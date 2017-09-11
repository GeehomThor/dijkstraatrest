package com.github.dijkstra.service.impl;

import javax.inject.Inject;

import com.github.dijkstra.dao.proxy.JerseyClientProxy;
import com.github.dijkstra.service.ShortestPathSolver;
import com.github.dijkstra.service.impl.builders.GraphStrollerBuilder;
import com.github.dijkstra.service.pojos.ContributionPath;

public class GithubShortestPathSolver implements ShortestPathSolver {

    @Inject
    JerseyClientProxy jerseyClientProxy;

    public ContributionPath shortestContributionsPath(String sourceUser, String destinationUser) {

        GraphStroller graphStroller = GraphStrollerBuilder.getInstance().with(jerseyClientProxy).build();

        return new ContributionPath(graphStroller.stroll(sourceUser, destinationUser));

    }

}
