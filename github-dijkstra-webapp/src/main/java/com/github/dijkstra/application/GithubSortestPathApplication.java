package com.github.dijkstra.application;

import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.client.Client;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import com.github.dijkstra.dao.proxy.JerseyClientProxy;
import com.github.dijkstra.dao.proxy.impl.GithubJerseyClientProxy;
import com.github.dijkstra.dao.proxy.impl.JerseyClientFactory;
import com.github.dijkstra.service.ShortestPathSolver;
import com.github.dijkstra.service.impl.GithubShortestPathSolver;

/*
 * IOC registration
 */
//@ApplicationPath("githubshortestpath") //left commented out in the benefit of the web.xml setup
public class GithubSortestPathApplication extends ResourceConfig {

    public GithubSortestPathApplication() {
        packages("com.github.dijkstra.resources");
        registerBinder();
    }

    public GithubSortestPathApplication(Class<?>... classes) {
        super(classes);
        packages("com.github.dijkstra.resources");
        registerBinder();
    }

    public GithubSortestPathApplication(ResourceConfig original) {
        super(original);
        packages("com.github.dijkstra.resources");
        registerBinder();
    }

    public GithubSortestPathApplication(Set<Class<?>> classes) {
        super(classes);
        packages("com.github.dijkstra.resources");
        registerBinder();
    }

    private void registerBinder() {
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bindFactory(JerseyClientFactory.class).to(Client.class);
                bind(GithubJerseyClientProxy.class).to(JerseyClientProxy.class);
                bind(GithubShortestPathSolver.class).to(ShortestPathSolver.class);
            }
        });
    }

}
