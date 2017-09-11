package com.github.dijkstra.dao.proxy.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.dijkstra.dao.proxy.impl.GithubJerseyClientProxy;

public class GithubJerseyClientProxyTest {

    GithubJerseyClientProxy jerseyClient;

    @Before
    public void setUp() throws Exception {
        jerseyClient = new GithubJerseyClientProxy();
    }

    @Test
    public void testGetUserRepos() {
        List<String> userRepos = jerseyClient.getUserRepos("GeehomThor");
        assertTrue(userRepos.contains("dijkstraatrest"));
    }

    @Test
    public void testGetRepoContributors() {
        List<String> userRepos = jerseyClient.getRepoContributors("GeehomThor", "dijkstraatrest");
        assertTrue(userRepos.contains("GeehomThor") && userRepos.size() == 1);
    }

}
