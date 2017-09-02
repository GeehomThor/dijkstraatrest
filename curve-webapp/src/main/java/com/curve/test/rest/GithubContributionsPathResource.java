package com.curve.test.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.curve.test.github.path.ContributionPath;
import com.curve.test.github.path.ContributionPathSolver;


/**
 * 
 * This end point is deployable to heroku (although I did not try it yet)
 * The algorithm would surely need some improvement testing with a larger set of data (inc. caching)
 *
 */
@Path("contributionspath")
public class GithubContributionsPathResource {

	@Inject
	private ContributionPathSolver contributionPathSolver;


	@GET
	@Path("shortest")
	@Produces("application/json")
	public Response shortestPath(@PathParam("user1") String user1, @PathParam("user2") String user2) {

		ContributionPath shortestCoontributionPath = contributionPathSolver.shortestContributionsPath(user1, user2);

		return Response.status(200).entity(shortestCoontributionPath).build();
	}

}
