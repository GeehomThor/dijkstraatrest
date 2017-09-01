package com.curve.test.github.path;

import java.util.List;

import javax.inject.Inject;

import com.curve.test.github.proxy.JerseyClientProxy;

public class GithubContributionPathSolver implements ContributionPathSolver {
	
	@Inject
	JerseyClientProxy jerseyClientProxy;

	public ContributionPath shortestContributionsPath(String sourceUser, String destinationUser) {
		
		List<String> userRepos = jerseyClientProxy.getUserRepos(sourceUser);
		
		GraphStroller graphStroller = GraphStrollerBuilder.getInstance()
				.with(jerseyClientProxy)
				.withFirstStepRepos(userRepos)
				.build();
		
		return new ContributionPath(graphStroller.stroll());
	
	}

	

}
