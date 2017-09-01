package com.curve.test.github.path;

import java.util.List;

import com.curve.test.github.proxy.JerseyClientProxy;

public class GraphStrollerBuilder {
	
	JerseyClientProxy jerseyClientProxy;
	
	List<String> sourceUserRepos;

	private String sourceUser;

	private String destinationUser;
	
	private GraphStrollerBuilder() {
		super();
	}

	public static GraphStrollerBuilder getInstance() {
		return new GraphStrollerBuilder();
	}
	
	public GraphStrollerBuilder with(JerseyClientProxy jerseyClientProxy) {
		this.jerseyClientProxy = jerseyClientProxy;
		return this;
	}
	
	public GraphStrollerBuilder withFirstStepRepos(List<String> sourceUserRepos) {
		this.sourceUserRepos = sourceUserRepos;
		return this;
	}
	
	public GraphStrollerBuilder withSourceUser(String sourceUser) {
		this.sourceUser = sourceUser;
		return this;
	}
	
	public GraphStrollerBuilder withDestinationUser(String destinationUser) {
		this.destinationUser = destinationUser;
		return this;
	}
	
	public GraphStroller build() {
		return new GraphStroller(jerseyClientProxy, sourceUser, destinationUser, sourceUserRepos);
	}

}
