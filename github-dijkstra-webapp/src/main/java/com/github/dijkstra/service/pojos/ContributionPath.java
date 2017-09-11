package com.github.dijkstra.service.pojos;

import java.util.Optional;

public class ContributionPath {

    private Optional<Integer> contributionPathLength;

    public ContributionPath() {
        super();
        this.contributionPathLength = Optional.empty();
    }

    public ContributionPath(Optional<Integer> contributionPathLength) {
        super();
        this.contributionPathLength = contributionPathLength;
    }

    public Optional<Integer> getContributionPathLength() {
        return contributionPathLength;
    }

    public void setContributionPathLength(Optional<Integer> contributionPathLength) {
        this.contributionPathLength = contributionPathLength;
    }

}
