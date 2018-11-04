package com.fritz.philsofinder.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import org.junit.Test;

public class PathResponseTests {
	
	@Test
	public void testPathResponse() {
		PathResponse response = new PathResponse("start", "end", Arrays.asList("start", "end"));
		assertEquals("start", response.getStartingPage());
		assertEquals("end", response.getDestinationPage());
		assertEquals(Integer.valueOf(1), response.getHopsCount());
		assertEquals(new Date().toString(), response.getFoundOnDate().toString());
		assertTrue(response.isPathExists());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalStartPage() {
		new PathResponse(null, "end", Collections.emptyList());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalEndPage() {
		new PathResponse("start", null, Collections.emptyList());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalStartAndEndPage() {
		new PathResponse(null, null, Collections.emptyList());
	}
	
	@Test
	public void testPathFoundConstructorWithEmptyHopsList() {
		PathResponse response = new PathResponse("start", "end", Collections.emptyList());
		assertEquals("start", response.getStartingPage());
		assertEquals("end", response.getDestinationPage());
		assertEquals(Integer.valueOf(-1), response.getHopsCount());
		assertEquals("", response.getPathString());
		assertFalse(response.isPathExists());
	}

}
