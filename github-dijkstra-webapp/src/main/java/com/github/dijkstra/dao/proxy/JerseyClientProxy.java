package com.github.dijkstra.dao.proxy;

import java.util.List;

public interface JerseyClientProxy {

    List<String> getRepoContributors(String user, String repo);

    List<String> getUserRepos(String user);

}
