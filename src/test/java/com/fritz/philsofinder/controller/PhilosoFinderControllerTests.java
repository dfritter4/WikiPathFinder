package com.fritz.philsofinder.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fritz.philsofinder.controller.impl.PhilosoFinderControllerImpl;
import com.fritz.philsofinder.domain.PathResponse;
import com.fritz.philsofinder.service.WikiPhilosophyPagePathFindingService;


@RunWith(SpringJUnit4ClassRunner.class)
public class PhilosoFinderControllerTests {
	
	@Mock
	private WikiPhilosophyPagePathFindingService service;
	
	@InjectMocks
	private PhilosoFinderControllerImpl controller;
	
	@Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void testControllerPathExists() {
		when(service.getPathToPhilosophy(anyString())).thenReturn(new PathResponse("test", "path -> Philsophy", 1));
		ResponseEntity<PathResponse> response = controller.findPath((anyString()));
		
		assertTrue(response.getBody().isPathExists());
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void testControllerNoPathExists() {
		when(service.getPathToPhilosophy(anyString())).thenReturn(new PathResponse(""));
		ResponseEntity<PathResponse> response = controller.findPath((anyString()));
		
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}

}
