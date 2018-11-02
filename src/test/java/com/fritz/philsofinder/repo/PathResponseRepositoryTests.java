package com.fritz.philsofinder.repo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.fritz.philsofinder.domain.PathResponse;

@RunWith(SpringRunner.class)
@DataMongoTest
public class PathResponseRepositoryTests {
	
	@Autowired
	private PathResponseRepository repo;
	
	@Before
	public void setUp() {
		repo.deleteAll();
		repo.save(new PathResponse("start1", "end1"));
		repo.save(new PathResponse("start2", "end2"));
		repo.save(new PathResponse("start3", "end3", "start3 -> hop1 -> hop2 -> end3", 3));
	}
	
	@After
	public void tearDown() {
		repo.deleteAll();
	}
	
	@Test
	public void testFindPathStart1End1() {
		PathResponse response = repo.findByStartingPageAndDestinationPage("start1", "end1");
		
		assertEquals("start1", response.getStartingPage());
		assertEquals("end1", response.getDestinationPage());
		assertFalse(response.isPathExists());
	}
	
	@Test
	public void testFindPathStart2End2() {
		PathResponse response = repo.findByStartingPageAndDestinationPage("start2", "end2");
		
		assertEquals("start2", response.getStartingPage());
		assertEquals("end2", response.getDestinationPage());
		assertFalse(response.isPathExists());
	}
	
	@Test
	public void testFindPathStart3End3() {
		PathResponse response = repo.findByStartingPageAndDestinationPage("start3", "end3");
		
		assertEquals("start3", response.getStartingPage());
		assertEquals("end3", response.getDestinationPage());
		assertTrue(response.isPathExists());
		assertEquals("start3 -> hop1 -> hop2 -> end3", response.getPathToDestination());
		assertEquals(new Integer(3), response.getHopsOnPath());
	}
	
	@Test
	public void testFindPathNotInRepo() {
		PathResponse response = repo.findByStartingPageAndDestinationPage("start4", "end4");
		assertNull(response);
	}

}
