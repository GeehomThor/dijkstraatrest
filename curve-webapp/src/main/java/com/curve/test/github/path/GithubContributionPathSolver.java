package com.curve.test.github.path;

import javax.inject.Inject;

import com.curve.test.github.proxy.JerseyClientProxy;

public class GithubContributionPathSolver implements ContributionPathSolver {

    @Inject
    JerseyClientProxy jerseyClientProxy;

    public ContributionPath shortestContributionsPath(String sourceUser, String destinationUser) {

        GraphStroller graphStroller = GraphStrollerBuilder.getInstance().with(jerseyClientProxy).build();

        return new ContributionPath(graphStroller.stroll(sourceUser, destinationUser));

    }

}
