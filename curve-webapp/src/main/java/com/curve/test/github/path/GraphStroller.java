package com.curve.test.github.path;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.curve.test.github.proxy.JerseyClientProxy;


class GraphStroller {

	private JerseyClientProxy jerseyClientProxy;

    private Set<User> exploredUsers;
    private Set<User> unExploredUsers;
    private Map<User, Integer> distanceToTarget;


    public GraphStroller(JerseyClientProxy jerseyClientProxy) {

    	this.jerseyClientProxy = jerseyClientProxy;

    	exploredUsers = new HashSet<User>();
    	unExploredUsers = new HashSet<User>();
    	distanceToTarget = new HashMap<User, Integer>();

    }

    public int stroll(String sourceUserName, String targetUserName) {
		User targetUser = new User(targetUserName);
		stroll(new User(sourceUserName), targetUser);
		return distanceToTarget.get(targetUser);
	}

	private void stroll(User source, User target) {

        distanceToTarget.put(source, 0);
        unExploredUsers.add(source);
        while (unExploredUsers.size() > 0) {
            User neighbourUser = getClosestUser(unExploredUsers);
            exploredUsers.add(neighbourUser);
            unExploredUsers.remove(neighbourUser);
            findShortestDistanceTo(neighbourUser);
            if (target.equals(neighbourUser)) return;
        }
        
    }

    private void findShortestDistanceTo(User user) {
        List<User> adjacentUsers = unVisitedNeighboursOf(user);
        for (User target : adjacentUsers) {
            if (getShortestDistance(target) > getShortestDistance(user) + getDistance(user, target)) {
                distanceToTarget.put(target, getShortestDistance(user) + getDistance(user, target));
                unExploredUsers.add(target);
            }
        }
    }

    private int getDistance(User sourceNode, User targetNode) { //Return one or infinity depending if the users are neighbours or not in github

    	List<String> userRepos = jerseyClientProxy.getUserRepos(sourceNode.getUserName());
    	boolean isNeighbour = userRepos.stream().anyMatch(repo -> jerseyClientProxy.getRepoContributors(sourceNode.getUserName(), repo).contains(targetNode.getUserName()));
    	return isNeighbour?1:Integer.MAX_VALUE;

    }

    private List<User> unVisitedNeighboursOf(User user) {

    	List<String> userRepos = jerseyClientProxy.getUserRepos(user.getUserName());
    	List<User> unvisitedContributors = 
    	userRepos.stream().flatMap(repo -> {
    		List<String> repoContributors = jerseyClientProxy.getRepoContributors(user.getUserName(), repo);
    		return repoContributors.stream()
    				.filter(contrib -> !exploredUsers.contains(new User(contrib)))
    				.map(u -> new User(u));
    	}).collect(Collectors.toList());

    	return unvisitedContributors;

    }

    private User getClosestUser(Set<User> users) {
        User closestUser = null;
        for (User user : users) {
            if (closestUser == null) {
                closestUser = user;
            } else {
                if (getShortestDistance(user) < getShortestDistance(closestUser)) {
                    closestUser = user;
                }
            }
        }
        return closestUser;
    }

    private int getShortestDistance(User destination) {
        Integer d = distanceToTarget.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }
    
    private class User {

    	private String userName;

    	public User(String userName) {
			this.userName = userName;
		}

		public String getUserName() {
    		return userName;
    	}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((userName == null) ? 0 : userName.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			User other = (User) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (userName == null) {
				if (other.userName != null)
					return false;
			} else if (!userName.equals(other.userName))
				return false;
			return true;
		}

		private GraphStroller getOuterType() {
			return GraphStroller.this;
		}

		@Override
		public String toString() {
			return "User [userName=" + userName + "]";
		}

    }

}
