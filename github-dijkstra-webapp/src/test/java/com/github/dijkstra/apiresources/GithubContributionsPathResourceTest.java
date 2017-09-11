package com.github.dijkstra.apiresources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
            return new ContributionPath(Optional.of(12));
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

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testGetIt() {
        ContributionPath responseMsg = target().path("githubcontributionpath/shortest").request()
                .get(ContributionPath.class);
        assertTrue(responseMsg.getContributionPathLength().isPresent());
        assertEquals(12, responseMsg.getContributionPathLength().get().intValue());
    }
}
