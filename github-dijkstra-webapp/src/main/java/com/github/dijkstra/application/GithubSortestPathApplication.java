package com.github.dijkstra.application;

import java.util.Set;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import com.github.dijkstra.service.ShortestPathSolver;
import com.github.dijkstra.service.impl.GithubShortestPathSolver;

/*
 * IOC registration
 */
public class GithubSortestPathApplication extends ResourceConfig {

    public GithubSortestPathApplication() {
        registerBinder();
    }

    public GithubSortestPathApplication(Class<?>... classes) {
        super(classes);
        registerBinder();
    }

    public GithubSortestPathApplication(ResourceConfig original) {
        super(original);
        registerBinder();
    }

    public GithubSortestPathApplication(Set<Class<?>> classes) {
        super(classes);
        registerBinder();
    }

    private void registerBinder() {
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(GithubShortestPathSolver.class).to(ShortestPathSolver.class);
            }
        });
    }

}
