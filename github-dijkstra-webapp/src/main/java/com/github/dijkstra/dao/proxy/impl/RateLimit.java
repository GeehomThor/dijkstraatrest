package com.github.dijkstra.dao.proxy.impl;

public class RateLimit {

    private int remaining;
    private final int limit;

    public RateLimit(int remaining, int limit) {
        super();
        this.remaining = remaining;
        this.limit = limit;
    }

    public int decrementRemaining() {
        return --remaining;
    }

    public boolean isToBeBlocked() {
        return (remaining * 100) / limit < 25;
    }

}
