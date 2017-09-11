package com.github.dijkstra.algorithmic;

import com.github.dijkstra.algorithmic.pojos.ContributionPath;

public interface ContributionPathSolver {

    ContributionPath shortestContributionsPath(String user1, String user2);

}
