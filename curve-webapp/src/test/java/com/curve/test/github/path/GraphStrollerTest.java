package com.curve.test.github.path;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.curve.test.github.proxy.JerseyClientProxy;


//Additional test are need : to the limit and also some stress tests
public class GraphStrollerTest {
	
	private JerseyClientProxy jerseyClientProxy;
	

	@Before
	public void setUp() throws Exception {
		
		jerseyClientProxy = mock(JerseyClientProxy.class);
		
	}

	@Test
	public void test() {
		
		//Given
		GraphStrollerBuilder builder = GraphStrollerBuilder.getInstance().with(jerseyClientProxy);
		GraphStroller stroller = builder.build();
		
	
		//When
		when(jerseyClientProxy.getUserRepos("user1")).thenReturn(Arrays.asList(new String[] {"repository1"}));
		when(jerseyClientProxy.getUserRepos("user2")).thenReturn(Arrays.asList(new String[] {"repository1","repository2"}));
		when(jerseyClientProxy.getUserRepos("user3")).thenReturn(Arrays.asList(new String[] {"repository2"}));
		
		when(jerseyClientProxy.getRepoContributors("user1", "repository1")).thenReturn(Arrays.asList(new String[] {"user1","user2"}));
		when(jerseyClientProxy.getRepoContributors("user2", "repository1")).thenReturn(Arrays.asList(new String[] {"user1","user2"}));
		when(jerseyClientProxy.getRepoContributors("user2", "repository2")).thenReturn(Arrays.asList(new String[] {"user2","user3"}));
		when(jerseyClientProxy.getRepoContributors("user3", "repository2")).thenReturn(Arrays.asList(new String[] {"user2","user3"}));
		
		int pathLength = stroller.stroll("user1", "user3");
		
		//Then
		assertEquals(2, pathLength);
			
	}

}
