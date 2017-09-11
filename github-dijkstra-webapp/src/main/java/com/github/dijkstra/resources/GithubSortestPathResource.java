package com.github.dijkstra.resources;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.github.dijkstra.service.ShortestPathSolver;
import com.github.dijkstra.service.pojos.ContributionPath;

/**
 * We can run the application locally by simply running : mvn clean package
 * jetty:run mvn clean package jetty:run This end point can also be deployed in
 * heroku (see
 * https://jersey.github.io/documentation/latest/getting-started.html#deploy-it-on-heroku)
 * Bear in mind this service also needs some improvement and testing with a
 * larger set of data (inc. caching)
 *
 */
@Path("shortest")
public class GithubSortestPathResource {

    @Inject
    private ShortestPathSolver shortestPathSolver;

    @GET
    @Produces("application/json")
    public Response shortestPath(@NotNull @QueryParam("user1") String user1,
            @NotNull @QueryParam("user2") String user2) {

        ContributionPath shortestCoontributionPath = shortestPathSolver.shortestContributionsPath(user1, user2);

        return Response.status(200).entity(shortestCoontributionPath).build();
    }

}
