package com.github.dijkstra.dao.proxy.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.json.JsonArray;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.io.RuntimeIOException;

import com.github.dijkstra.dao.proxy.JerseyClientProxy;

//TODO Add some caching capabilities
public class GithubJerseyClientProxy implements JerseyClientProxy {

    private Client client;

    private RateLimit applicationRateLimit;

    @Inject
    GithubJerseyClientProxy(Client client) {
        super();
        this.client = client;
    }

    private RateLimit getRateLimitPercentRemaining(String user) {

        Response response = client.target(String.format("https://api.github.com/users/%s", user)).request().get();

        MultivaluedMap<String, Object> headers = response.getHeaders();
        int limit = Integer.valueOf(headers.getFirst("X-RateLimit-Limit").toString());
        int remaining = Integer.valueOf(headers.getFirst("X-RateLimit-Remaining").toString());

        return new RateLimit(remaining, limit);

    }

    private boolean hasReachedQueryLimit(String user) {

        if (applicationRateLimit == null) {
            applicationRateLimit = getRateLimitPercentRemaining(user);
        }

        applicationRateLimit.decrementRemaining();
        return applicationRateLimit.isToBeBlocked();

    }

    @Override
    public List<String> getUserRepos(String user) {

        if (hasReachedQueryLimit(user))
            throw new RuntimeException("Github query limit exceeded");

        JsonArray response = client.target(String.format("https://api.github.com/users/%s/repos", user)).request()
                .get(JsonArray.class);

        return response.stream().map(jsonValue -> jsonValue.asJsonObject().getString("name"))
                .collect(Collectors.toList());

    }

    @Override
    public List<String> getRepoContributors(String user, String repo) {

        if (hasReachedQueryLimit(user))
            throw new RuntimeException("Github query limit exceeded");

        JsonArray response = client.target(String.format("https://api.github.com/repos/%s/%s/contributors", user, repo))
                .request().get(JsonArray.class);

        return response.stream().map(jsonValue -> jsonValue.asJsonObject().getString("login"))
                .collect(Collectors.toList());

    }

}
