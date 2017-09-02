package com.curve.test.github.path;

import java.util.Optional;

public class ContributionPath {

    private Optional<Integer> contributionPathLength;

    public ContributionPath() {
        super();
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
