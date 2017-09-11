package com.github.dijkstra.service;

import com.github.dijkstra.service.pojos.ContributionPath;

public interface ShortestPathSolver {

    ContributionPath shortestContributionsPath(String user1, String user2);

}
