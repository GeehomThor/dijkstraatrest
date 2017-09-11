package com.github.dijkstra.application;

import java.util.Set;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import com.github.dijkstra.algorithmic.ContributionPathSolver;
import com.github.dijkstra.algorithmic.impl.GithubContributionPathSolver;

public class CurveTestApplication extends ResourceConfig {

    public CurveTestApplication() {
        registerBinder();
    }

    public CurveTestApplication(Class<?>... classes) {
        super(classes);
        registerBinder();
    }

    public CurveTestApplication(ResourceConfig original) {
        super(original);
        registerBinder();
    }

    public CurveTestApplication(Set<Class<?>> classes) {
        super(classes);
        registerBinder();
    }

    private void registerBinder() {
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(GithubContributionPathSolver.class).to(ContributionPathSolver.class);
            }
        });
    }

}
