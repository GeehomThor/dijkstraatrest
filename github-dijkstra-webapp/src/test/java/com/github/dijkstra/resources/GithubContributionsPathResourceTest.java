package com.github.dijkstra.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

import java.util.Optional;

import javax.ws.rs.core.Application;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.github.dijkstra.service.ShortestPathSolver;
import com.github.dijkstra.service.pojos.ContributionPath;

public class GithubContributionsPathResourceTest extends JerseyTest {

    private static class MockContributionPathSolver implements ShortestPathSolver {

        @Override
        public ContributionPath shortestContributionsPath(String user1, String user2) {
            if (user1.equals("u1") && user2.equals("u2")) {
                return new ContributionPath(Optional.of(12));
            } else {
                return new ContributionPath(Optional.empty());
            }
        }

    }

    @Override
    protected Application configure() {
        ResourceConfig resourceConfig = new ResourceConfig(GithubSortestPathResource.class);
        resourceConfig.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(MockContributionPathSolver.class).to(ShortestPathSolver.class);
            }
        });
        return resourceConfig;
    }

    @Test
    public void testValidGet() {
        ContributionPath responseMsg = target().path("shortest").queryParam("user1", "u1").queryParam("user2", "u2")
                .request().get(ContributionPath.class);
        assertTrue(responseMsg.getContributionPathLength().isPresent());
        assertEquals(12, responseMsg.getContributionPathLength().get().intValue());
    }

    @Test
    public void testInvalidGet() {
        ContributionPath responseMsg = target().path("shortest").queryParam("user1", "u3").queryParam("user2", "u4")
                .request().get(ContributionPath.class);
        assertFalse(responseMsg.getContributionPathLength().isPresent());

    }
}
