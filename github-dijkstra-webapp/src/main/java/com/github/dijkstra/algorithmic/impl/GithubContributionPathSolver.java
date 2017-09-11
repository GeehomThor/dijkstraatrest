package com.github.dijkstra.algorithmic.impl;

import javax.inject.Inject;

import com.github.dijkstra.algorithmic.ContributionPathSolver;
import com.github.dijkstra.algorithmic.impl.builders.GraphStrollerBuilder;
import com.github.dijkstra.algorithmic.pojos.ContributionPath;
import com.github.dijkstra.dao.proxy.JerseyClientProxy;

public class GithubContributionPathSolver implements ContributionPathSolver {

    @Inject
    JerseyClientProxy jerseyClientProxy;

    public ContributionPath shortestContributionsPath(String sourceUser, String destinationUser) {

        GraphStroller graphStroller = GraphStrollerBuilder.getInstance().with(jerseyClientProxy).build();

        return new ContributionPath(graphStroller.stroll(sourceUser, destinationUser));

    }

}
