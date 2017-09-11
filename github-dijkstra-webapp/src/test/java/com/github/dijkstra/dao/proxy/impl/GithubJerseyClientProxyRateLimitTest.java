package com.github.dijkstra.dao.proxy.impl;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.json.JsonArray;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

public class GithubJerseyClientProxyRateLimitTest {

    private Client jerseyClient;
    private WebTarget webTarget;
    private Builder requestBuilder;
    private Response response;

    @Before
    public void setUp() throws Exception {
        jerseyClient = mock(Client.class);
        webTarget = mock(WebTarget.class);
        requestBuilder = mock(Builder.class);
        response = mock(Response.class);
    }

    @Test(expected = Exception.class)
    public void testRateLimitFails() {

        // Given
        GithubJerseyClientProxy jerseyClientProxy = new GithubJerseyClientProxy(jerseyClient);
        MultivaluedMap<String, Object> responseHeaders = new MultivaluedHashMap<>();
        responseHeaders.add("X-RateLimit-Limit", 60);
        responseHeaders.add("X-RateLimit-Remaining", 16);

        when(response.getHeaders()).thenReturn(responseHeaders);
        when(requestBuilder.get()).thenReturn(response);
        when(requestBuilder.get(JsonArray.class)).thenReturn(JsonArray.EMPTY_JSON_ARRAY);
        when(webTarget.request()).thenReturn(requestBuilder);
        when(jerseyClient.target(anyString())).thenReturn(webTarget);

        // When
        List<String> userRepos = jerseyClientProxy.getRepoContributors("GheeyomThor", "dijkstraatrest");

        // Then
        assertTrue(userRepos.isEmpty());

        // When
        jerseyClientProxy.getRepoContributors("GheeyomThor", "dijkstraatrest");

    }

    @Test
    public void testRateLimitSucceed() {

        // Given
        GithubJerseyClientProxy jerseyClientProxy = new GithubJerseyClientProxy(jerseyClient);
        MultivaluedMap<String, Object> responseHeaders = new MultivaluedHashMap<>();
        responseHeaders.add("X-RateLimit-Limit", 60);
        responseHeaders.add("X-RateLimit-Remaining", 17);

        when(response.getHeaders()).thenReturn(responseHeaders);
        when(requestBuilder.get()).thenReturn(response);
        when(requestBuilder.get(JsonArray.class)).thenReturn(JsonArray.EMPTY_JSON_ARRAY);
        when(webTarget.request()).thenReturn(requestBuilder);
        when(jerseyClient.target(anyString())).thenReturn(webTarget);

        // When
        List<String> userRepos = jerseyClientProxy.getRepoContributors("GheeyomThor", "dijkstraatrest");

        // Then
        assertTrue(userRepos.isEmpty());

        // When
        jerseyClientProxy.getRepoContributors("GheeyomThor", "dijkstraatrest");

        // Then
        assertTrue(userRepos.isEmpty());

    }

}
