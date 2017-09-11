package com.github.dijkstra.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.dijkstra.dao.proxy.JerseyClientProxy;

public class GraphStroller {

    private JerseyClientProxy jerseyClientProxy;

    private Set<User> exploredUsers;
    private Set<User> toBeExploredUsers;
    private Map<User, Integer> distanceToSource;

    public GraphStroller(JerseyClientProxy jerseyClientProxy) {

        this.jerseyClientProxy = jerseyClientProxy;

        exploredUsers = new HashSet<User>();
        toBeExploredUsers = new HashSet<User>();
        distanceToSource = new HashMap<User, Integer>();

    }

    public Optional<Integer> stroll(String sourceUserName, String targetUserName) {
        User targetUser = new User(targetUserName);
        User sourceUser = new User(sourceUserName);
        stroll(sourceUser, targetUser);
        Integer integer = distanceToSource.get(targetUser);
        return Optional.ofNullable(integer);
    }

    private void stroll(User source, User target) {

        distanceToSource.put(source, 0); // The source user is at 0 distance
                                         // from himself
        toBeExploredUsers.add(source); // toBeExploredUsers are the neighbouring
                                       // users in our walk

        while (toBeExploredUsers.size() > 0) {

            // We check the closest user to the source
            // among users that needs exploration i.e. not explored yet from
            // where we stand
            User closestUser = getClosestUserAmoung(toBeExploredUsers);
            exploredUsers.add(closestUser);
            toBeExploredUsers.remove(closestUser);

            // We stop if we have reached our target user
            if (target.equals(closestUser)) {
                return;
            }

            // Otherwise we explore paths around that closest user
            solveAdjacentUsersDistanceWhenThrough(closestUser);

        }

    }

    private User getClosestUserAmoung(Set<User> users) {
        User closestUser = null;
        for (User user : users) {
            // initialisation
            if (closestUser == null) {
                closestUser = user;
                // then
            } else {
                if (getKnownShortestDistanceTo(user) < getKnownShortestDistanceTo(closestUser)) {
                    closestUser = user;
                }
            }
        }
        return closestUser;
    }

    // We update the distance to every adjacent users through this user
    // and add these adjacent users into our users to be explored.
    // But only if this distance is shorter than previously found through an
    // other path
    private void solveAdjacentUsersDistanceWhenThrough(User user) {
        List<User> adjacentUsers = unExploredNeighboursOf(user);
        for (User adjacentUser : adjacentUsers) {
            if (getKnownShortestDistanceTo(adjacentUser) > getKnownShortestDistanceTo(user)
                    + getDistanceWeightBetween(user, adjacentUser)) {
                int distance = getKnownShortestDistanceTo(user) + getDistanceWeightBetween(user, adjacentUser);
                distanceToSource.put(adjacentUser, distance);
                toBeExploredUsers.add(adjacentUser);
            }
        }
    }

    // In this particular example, the edges having all the same weight,
    // we return either one when the two users are neighbours or infinity when
    // they are not
    private int getDistanceWeightBetween(User sourceNode, User targetNode) {

        List<String> userRepos = jerseyClientProxy.getUserRepos(sourceNode.getUserName());
        boolean isNeighbour = userRepos.stream().anyMatch(repo -> jerseyClientProxy
                .getRepoContributors(sourceNode.getUserName(), repo).contains(targetNode.getUserName()));
        return isNeighbour ? 1 : Integer.MAX_VALUE;

    }

    // We return infinity when the distance is unknown or not yet known
    private int getKnownShortestDistanceTo(User destination) {
        Integer distance = distanceToSource.get(destination);
        if (distance == null) {
            return Integer.MAX_VALUE;
        } else {
            return distance;
        }
    }

    private List<User> unExploredNeighboursOf(User user) {

        List<String> userRepos = jerseyClientProxy.getUserRepos(user.getUserName());

        // In comparison with the Iterator pattern,
        // it is always interesting to experiment with streams as it seems to
        // just flow nicely
        List<User> unExploredContributors = userRepos.stream().flatMap(repo -> {
            List<String> repoContributors = jerseyClientProxy.getRepoContributors(user.getUserName(), repo);
            return repoContributors.stream()
                    // We only retain users that we have not explored yet
                    .filter(contrib -> !exploredUsers.contains(new User(contrib))).map(User::new);
        }).collect(Collectors.toList());

        return unExploredContributors;

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
