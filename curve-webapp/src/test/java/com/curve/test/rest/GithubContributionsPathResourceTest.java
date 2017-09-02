package com.curve.test.rest;

import static org.junit.Assert.*;

import java.util.Optional;

import javax.ws.rs.core.Application;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.curve.test.github.path.ContributionPath;
import com.curve.test.github.path.ContributionPathSolver;

public class GithubContributionsPathResourceTest extends JerseyTest {

    private static class MockContributionPathSolver implements ContributionPathSolver {

        @Override
        public ContributionPath shortestContributionsPath(String user1, String user2) {
            return new ContributionPath(Optional.of(12));
        }

    }

    @Override
    protected Application configure() {
        ResourceConfig resourceConfig = new ResourceConfig(GithubContributionsPathResource.class);
        resourceConfig.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(MockContributionPathSolver.class).to(ContributionPathSolver.class);
            }
        });
        return resourceConfig;
    }

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testGetIt() {
        ContributionPath responseMsg = target().path("contributionspath/shortest").request()
                .get(ContributionPath.class);
        assertTrue(responseMsg.getContributionPathLength().isPresent());
        assertEquals(12, responseMsg.getContributionPathLength().get().intValue());
    }
}
