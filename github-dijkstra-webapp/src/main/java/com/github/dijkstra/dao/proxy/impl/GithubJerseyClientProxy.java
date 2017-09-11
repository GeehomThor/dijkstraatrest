package com.github.dijkstra.dao.proxy.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.json.JsonArray;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import com.github.dijkstra.dao.proxy.JerseyClientProxy;

// TODO Add some caching capabilities
public class GithubJerseyClientProxy implements JerseyClientProxy {

    Client client;

    public GithubJerseyClientProxy() {
        client = ClientBuilder.newBuilder().build();
    }

    @Override
    public List<String> getUserRepos(String user) {

        JsonArray response = client.target(String.format("https://api.github.com/users/%s/repos", user))
                .queryParam("client_id", "geehomThor").queryParam("client_id", "geehomThor").request()
                .get(JsonArray.class);

        return response.stream().map(jsonValue -> jsonValue.asJsonObject().getString("name"))
                .collect(Collectors.toList());

    }

    @Override
    public List<String> getRepoContributors(String user, String repo) {

        JsonArray response = client.target(String.format("https://api.github.com/repos/%s/%s/contributors", user, repo))
                .request().get(JsonArray.class);

        return response.stream().map(jsonValue -> jsonValue.asJsonObject().getString("login"))
                .collect(Collectors.toList());

    }

}
