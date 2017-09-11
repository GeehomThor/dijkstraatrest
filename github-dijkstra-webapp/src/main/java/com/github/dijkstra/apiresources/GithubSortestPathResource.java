package com.github.dijkstra.apiresources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.github.dijkstra.service.ShortestPathSolver;
import com.github.dijkstra.service.pojos.ContributionPath;

/**
 * 
 * This end point can be deployed in heroku.
 * Also bear in mind this service also needs some improvement and testing with a larger set of data (inc. caching)
 *
 */
@Path("githubcontributionpath")
public class GithubSortestPathResource {

    @Inject
    private ShortestPathSolver shortestPathSolver;

    @GET
    @Path("shortest")
    @Produces("application/json")
    public Response shortestPath(@PathParam("user1") String user1, @PathParam("user2") String user2) {

        ContributionPath shortestCoontributionPath = shortestPathSolver.shortestContributionsPath(user1, user2);

        return Response.status(200).entity(shortestCoontributionPath).build();
    }

}
