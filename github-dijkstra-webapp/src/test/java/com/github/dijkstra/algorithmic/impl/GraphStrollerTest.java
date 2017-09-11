package com.github.dijkstra.algorithmic.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.github.dijkstra.algorithmic.impl.builders.GraphStrollerBuilder;
import com.github.dijkstra.dao.proxy.JerseyClientProxy;

//Additional test are need : to the limit and also with larger set of data
public class GraphStrollerTest {

    private JerseyClientProxy jerseyClientProxy;

    @Before
    public void setUp() throws Exception {

        jerseyClientProxy = mock(JerseyClientProxy.class);

    }

    @Test
    public void exampleTest() {

        // Given
        GraphStrollerBuilder builder = GraphStrollerBuilder.getInstance().with(jerseyClientProxy);
        GraphStroller stroller = builder.build();

        // When
        when(jerseyClientProxy.getUserRepos("user1")).thenReturn(Arrays.asList(new String[] { "repository1" }));
        when(jerseyClientProxy.getUserRepos("user2"))
                .thenReturn(Arrays.asList(new String[] { "repository1", "repository2" }));
        when(jerseyClientProxy.getUserRepos("user3")).thenReturn(Arrays.asList(new String[] { "repository2" }));

        when(jerseyClientProxy.getRepoContributors("user1", "repository1"))
                .thenReturn(Arrays.asList(new String[] { "user1", "user2" }));
        when(jerseyClientProxy.getRepoContributors("user2", "repository1"))
                .thenReturn(Arrays.asList(new String[] { "user1", "user2" }));
        when(jerseyClientProxy.getRepoContributors("user2", "repository2"))
                .thenReturn(Arrays.asList(new String[] { "user2", "user3" }));
        when(jerseyClientProxy.getRepoContributors("user3", "repository2"))
                .thenReturn(Arrays.asList(new String[] { "user2", "user3" }));

        Optional<Integer> pathLength = stroller.stroll("user1", "user3");

        // Then
        assertTrue(pathLength.isPresent());
        assertEquals(2, pathLength.get().intValue());

    }

    @Test
    public void aThreeLengthTest() {

        // Given
        GraphStrollerBuilder builder = GraphStrollerBuilder.getInstance().with(jerseyClientProxy);
        GraphStroller stroller = builder.build();

        // When
        when(jerseyClientProxy.getUserRepos("user1")).thenReturn(Arrays.asList(new String[] { "repository1" }));
        when(jerseyClientProxy.getUserRepos("user2"))
                .thenReturn(Arrays.asList(new String[] { "repository1", "repository2" }));
        when(jerseyClientProxy.getUserRepos("user3"))
                .thenReturn(Arrays.asList(new String[] { "repository2", "repository3" }));
        when(jerseyClientProxy.getUserRepos("user4")).thenReturn(Arrays.asList(new String[] { "repository3" }));

        when(jerseyClientProxy.getRepoContributors("user1", "repository1"))
                .thenReturn(Arrays.asList(new String[] { "user1", "user2" }));
        when(jerseyClientProxy.getRepoContributors("user2", "repository1"))
                .thenReturn(Arrays.asList(new String[] { "user1", "user2" }));
        when(jerseyClientProxy.getRepoContributors("user2", "repository2"))
                .thenReturn(Arrays.asList(new String[] { "user2", "user3" }));
        when(jerseyClientProxy.getRepoContributors("user3", "repository2"))
                .thenReturn(Arrays.asList(new String[] { "user2", "user3" }));
        when(jerseyClientProxy.getRepoContributors("user3", "repository3"))
                .thenReturn(Arrays.asList(new String[] { "user3", "user4" }));
        when(jerseyClientProxy.getRepoContributors("user4", "repository3"))
                .thenReturn(Arrays.asList(new String[] { "user3", "user4" }));

        Optional<Integer> pathLength = stroller.stroll("user1", "user4");

        // Then
        assertTrue(pathLength.isPresent());
        assertEquals(3, pathLength.get().intValue());

    }

    @Test
    public void isTheShortestTest() {

        // Given
        GraphStrollerBuilder builder = GraphStrollerBuilder.getInstance().with(jerseyClientProxy);
        GraphStroller stroller = builder.build();

        // When
        when(jerseyClientProxy.getUserRepos("user1")).thenReturn(Arrays.asList(new String[] { "repository1" }));
        when(jerseyClientProxy.getUserRepos("user2"))
                .thenReturn(Arrays.asList(new String[] { "repository1", "repository2" }));
        when(jerseyClientProxy.getUserRepos("user3"))
                .thenReturn(Arrays.asList(new String[] { "repository2", "repository3" }));
        when(jerseyClientProxy.getUserRepos("user4"))
                .thenReturn(Arrays.asList(new String[] { "repository2", "repository3" }));

        when(jerseyClientProxy.getRepoContributors("user1", "repository1"))
                .thenReturn(Arrays.asList(new String[] { "user1", "user2" }));
        when(jerseyClientProxy.getRepoContributors("user2", "repository1"))
                .thenReturn(Arrays.asList(new String[] { "user1", "user2" }));
        when(jerseyClientProxy.getRepoContributors("user2", "repository2"))
                .thenReturn(Arrays.asList(new String[] { "user2", "user3", "user4" }));
        when(jerseyClientProxy.getRepoContributors("user3", "repository2"))
                .thenReturn(Arrays.asList(new String[] { "user2", "user3", "user4" }));
        when(jerseyClientProxy.getRepoContributors("user4", "repository2"))
                .thenReturn(Arrays.asList(new String[] { "user2", "user3", "user4" }));
        when(jerseyClientProxy.getRepoContributors("user3", "repository3"))
                .thenReturn(Arrays.asList(new String[] { "user3", "user4" }));
        when(jerseyClientProxy.getRepoContributors("user4", "repository3"))
                .thenReturn(Arrays.asList(new String[] { "user3", "user4" }));

        Optional<Integer> pathLength = stroller.stroll("user1", "user4");

        // Then
        assertTrue(pathLength.isPresent());
        assertEquals(2, pathLength.get().intValue());

    }

    @Test
    public void limitShortestTest() {

        // Given
        GraphStrollerBuilder builder = GraphStrollerBuilder.getInstance().with(jerseyClientProxy);
        GraphStroller stroller = builder.build();

        // When
        when(jerseyClientProxy.getUserRepos("user1")).thenReturn(Arrays.asList(new String[] { "repository1" }));
        when(jerseyClientProxy.getUserRepos("user2"))
                .thenReturn(Arrays.asList(new String[] { "repository1", "repository2" }));
        when(jerseyClientProxy.getUserRepos("user3"))
                .thenReturn(Arrays.asList(new String[] { "repository2", "repository3" }));
        when(jerseyClientProxy.getUserRepos("user4"))
                .thenReturn(Arrays.asList(new String[] { "repository2", "repository3" }));

        when(jerseyClientProxy.getRepoContributors("user1", "repository1"))
                .thenReturn(Arrays.asList(new String[] { "user1", "user2" }));
        when(jerseyClientProxy.getRepoContributors("user2", "repository1"))
                .thenReturn(Arrays.asList(new String[] { "user1", "user2" }));
        when(jerseyClientProxy.getRepoContributors("user2", "repository2"))
                .thenReturn(Arrays.asList(new String[] { "user2", "user3", "user4" }));
        when(jerseyClientProxy.getRepoContributors("user3", "repository2"))
                .thenReturn(Arrays.asList(new String[] { "user2", "user3", "user4" }));
        when(jerseyClientProxy.getRepoContributors("user4", "repository2"))
                .thenReturn(Arrays.asList(new String[] { "user2", "user3", "user4" }));
        when(jerseyClientProxy.getRepoContributors("user3", "repository3"))
                .thenReturn(Arrays.asList(new String[] { "user3", "user4" }));
        when(jerseyClientProxy.getRepoContributors("user4", "repository3"))
                .thenReturn(Arrays.asList(new String[] { "user3", "user4" }));

        Optional<Integer> pathLength = stroller.stroll("user1", "user1");

        // Then
        assertTrue(pathLength.isPresent());
        assertEquals(0, pathLength.get().intValue());

    }

    @Test
    public void limitNoPathTest() {

        // Given
        GraphStrollerBuilder builder = GraphStrollerBuilder.getInstance().with(jerseyClientProxy);
        GraphStroller stroller = builder.build();

        // When
        when(jerseyClientProxy.getUserRepos("user1")).thenReturn(Arrays.asList(new String[] { "repository1" }));
        when(jerseyClientProxy.getUserRepos("user2"))
                .thenReturn(Arrays.asList(new String[] { "repository1", "repository2" }));
        when(jerseyClientProxy.getUserRepos("user3")).thenReturn(Arrays.asList(new String[] { "repository2" }));
        when(jerseyClientProxy.getUserRepos("user4")).thenReturn(Arrays.asList(new String[] { "repository3" }));

        when(jerseyClientProxy.getRepoContributors("user1", "repository1"))
                .thenReturn(Arrays.asList(new String[] { "user1", "user2" }));
        when(jerseyClientProxy.getRepoContributors("user2", "repository1"))
                .thenReturn(Arrays.asList(new String[] { "user1", "user2" }));
        when(jerseyClientProxy.getRepoContributors("user2", "repository2"))
                .thenReturn(Arrays.asList(new String[] { "user2", "user3" }));
        when(jerseyClientProxy.getRepoContributors("user3", "repository2"))
                .thenReturn(Arrays.asList(new String[] { "user2", "user3" }));
        when(jerseyClientProxy.getRepoContributors("user3", "repository3"))
                .thenReturn(Arrays.asList(new String[] { "user3" }));
        when(jerseyClientProxy.getRepoContributors("user4", "repository4"))
                .thenReturn(Arrays.asList(new String[] { "user4" }));

        Optional<Integer> pathLength = stroller.stroll("user1", "user4");

        // Then
        assertFalse(pathLength.isPresent());

    }

}
