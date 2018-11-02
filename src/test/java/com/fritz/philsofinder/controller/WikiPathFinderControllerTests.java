package com.fritz.philsofinder.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fritz.philsofinder.controller.impl.WikiPathFinderControllerImpl;
import com.fritz.philsofinder.domain.PathResponse;
import com.fritz.philsofinder.service.WikiPathFindingService;


@RunWith(SpringJUnit4ClassRunner.class)
public class WikiPathFinderControllerTests {
	
	@Mock
	private WikiPathFindingService service;
	
	@InjectMocks
	private WikiPathFinderControllerImpl controller;
	
	@Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void testControllerPathExists() {
		when(service.getPathToPage(anyString(), anyString())).thenReturn(new PathResponse("start","end", Arrays.asList("start", "")));
		ResponseEntity<PathResponse> response = controller.findPath("some_url", "destination");
		
		assertTrue(response.getBody().isPathExists());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void testControllerNoPathExists() {
		when(service.getPathToPage(anyString(), anyString())).thenReturn(new PathResponse("start", "end"));
		ResponseEntity<PathResponse> response = controller.findPath("some_url", "destination");
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertFalse(response.getBody().isPathExists());
	}

}
