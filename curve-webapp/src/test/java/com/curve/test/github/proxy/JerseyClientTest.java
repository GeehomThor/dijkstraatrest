package com.curve.test.github.proxy;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.curve.test.github.proxy.GithubJerseyClientProxy;

public class JerseyClientTest {
	
	GithubJerseyClientProxy jerseyClient;

	@Before
	public void setUp() throws Exception {
		jerseyClient = new GithubJerseyClientProxy();
	}

	@Test
	public void testGetUserRepos() {
		List<String> userRepos = jerseyClient.getUserRepos("GeehomThor");
		assertTrue(userRepos.contains("curve"));
	}
	
	@Test
	public void testGetRepoContributors() {
		List<String> userRepos = jerseyClient.getRepoContributors("GeehomThor", "curve");
		assertTrue(userRepos.contains("GeehomThor") && userRepos.size() == 1);
	}

}
