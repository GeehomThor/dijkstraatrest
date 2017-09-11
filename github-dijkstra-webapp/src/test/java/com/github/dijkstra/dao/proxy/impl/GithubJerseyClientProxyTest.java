package com.github.dijkstra.dao.proxy.impl;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.Before;
import org.junit.Test;

public class GithubJerseyClientProxyTest {

    GithubJerseyClientProxy jerseyClient;

    @Before
    public void setUp() throws Exception {
        jerseyClient = new GithubJerseyClientProxy(JerseyClientBuilder.newBuilder().build());
    }

    @Test
    public void testGetUserRepos() {
        List<String> userRepos = jerseyClient.getUserRepos("GheeyomThor");
        assertTrue(userRepos.contains("dijkstraatrest"));
    }

    @Test
    public void testGetRepoContributors() {
        List<String> userRepos = jerseyClient.getRepoContributors("GheeyomThor", "dijkstraatrest");
        assertTrue(userRepos.contains("GheeyomThor") && userRepos.size() == 1);
    }

}
