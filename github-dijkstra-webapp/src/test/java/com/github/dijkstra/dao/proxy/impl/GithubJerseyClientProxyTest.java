package com.github.dijkstra.dao.proxy.impl;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;

public class GithubJerseyClientProxyTest {

    GithubJerseyClientProxy jerseyClient;

    @Before
    public void setUp() throws Exception {
        jerseyClient = new GithubJerseyClientProxy();
    }

    @Ignore
    public void testGetUserRepos() {
        List<String> userRepos = jerseyClient.getUserRepos("GheeyomThor");
        assertTrue(userRepos.contains("dijkstraatrest"));
    }

    @Ignore
    public void testGetRepoContributors() {
        List<String> userRepos = jerseyClient.getRepoContributors("GheeyomThor", "dijkstraatrest");
        assertTrue(userRepos.contains("GheeyomThor") && userRepos.size() == 1);
    }

}
