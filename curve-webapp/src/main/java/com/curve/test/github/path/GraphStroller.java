package com.curve.test.github.path;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;

import com.curve.test.github.proxy.JerseyClientProxy;

//TODO
class GraphStroller {

	private JerseyClientProxy jerseyClientProxy;
	
	private Set<String> visitedUsers;

	private String sourceUser;
	private String destinationUser;
	private List<String> sourceUserRepos;


	public GraphStroller(
			@NotNull JerseyClientProxy jerseyClientProxy, 
			@NotNull String sourceUser, 
			@NotNull String destinationUser, 
			@NotNull List<String> sourceUserRepos) {
		
		this.jerseyClientProxy = jerseyClientProxy;
		
		this.visitedUsers = new HashSet<>();
		
		this.sourceUser = sourceUser;
		this.destinationUser = destinationUser;
		this.sourceUserRepos = sourceUserRepos;
		
	}
	
	public int stroll() {
		
		sourceUserRepos.stream().forEach( repo -> {
			exploreRepo(sourceUser, repo);
		});
		
		return 0;
		
	}

	private int exploreRepo(String sourceUser, String repo) {

		List<String> repoContributors = jerseyClientProxy.getRepoContributors(sourceUser, repo);
		if (repoContributors.contains(destinationUser)) {
			return 1;
		} else {
			
			//check already visited
			repoContributors.removeAll(visitedUsers);
			visitedUsers.addAll(repoContributors);
			repoContributors.stream().forEach( contributor -> {
				List<String> contributorRepos = jerseyClientProxy.getUserRepos(contributor);
				contributorRepos.forEach( contributorRepo -> {
					exploreRepo(contributor, contributorRepo);
				});
			});
			
		}
		
		return 0;
	}

}
