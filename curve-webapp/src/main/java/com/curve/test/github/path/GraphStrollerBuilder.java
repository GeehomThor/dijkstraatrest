package com.curve.test.github.path;

import com.curve.test.github.proxy.JerseyClientProxy;

public class GraphStrollerBuilder {
	
	JerseyClientProxy jerseyClientProxy;

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
	
	public GraphStroller build() {
		return new GraphStroller(jerseyClientProxy);
	}

}
