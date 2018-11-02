package com.fritz.philsofinder.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fritz.philsofinder.cache.CacheKey;
import com.fritz.philsofinder.cache.CachingSystem;
import com.fritz.philsofinder.domain.PathResponse;
import com.fritz.philsofinder.repo.PathResponseRepository;
import com.fritz.philsofinder.service.impl.WikiPhilosophyPagePathFindingServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
public class WikiPhilosophyPagePathFindingServiceTests {
	
	@Mock
	private CachingSystem cache;
	
	@Mock
	private PathResponseRepository repo;
	
	@InjectMocks
	private WikiPhilosophyPagePathFindingServiceImpl service;
	
	private final String PATH_FROM_ROCK_MUSIC = "Rock music -> Popular music -> Music industry -> Musical composition -> Music -> Culture -> Social behavior -> Behavior -> Action (philosophy) -> Philosophy";
	private final String PATH_FROM_CAPTAIN_AMERICA = "Captain America -> Character (arts) -> Person -> Reason -> Consciousness -> Quality (philosophy) -> Philosophy";
	private final String DESTINATION_PAGE = "Philosophy";
	
	@Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void testKnownPathFromRockMusic() {
		when(cache.contains(Mockito.mock(CacheKey.class))).thenReturn(false);
		when(repo.findPath(anyString(), anyString())).thenReturn(null);
		PathResponse response = service.getPathToPage("https://en.wikipedia.org/wiki/Rock_music", DESTINATION_PAGE);
		
		assertEquals(new Integer(9), response.getHopsOnPath());
		assertEquals(PATH_FROM_ROCK_MUSIC, response.getPathToDestination());
		assertEquals("Rock music", response.getStartingPage());
		assertTrue(response.isPathExists());
	}
	
	@Test
	public void testKnownPathFromCaptainAmerica() {
		when(cache.contains(Mockito.mock(CacheKey.class))).thenReturn(false);
		when(repo.findPath(anyString(), anyString())).thenReturn(null);
		PathResponse response = service.getPathToPage("https://en.wikipedia.org/wiki/Captain_America", DESTINATION_PAGE);
		
		assertEquals(new Integer(6), response.getHopsOnPath());
		assertEquals(PATH_FROM_CAPTAIN_AMERICA, response.getPathToDestination());
		assertEquals("Captain America", response.getStartingPage());
		assertTrue(response.isPathExists());
	}
	
	@Test
	public void testPathDoesNotExistFromPaper() {
		when(cache.contains(Mockito.mock(CacheKey.class))).thenReturn(false);
		when(repo.findPath(anyString(), anyString())).thenReturn(null);
		PathResponse response = service.getPathToPage("https://en.wikipedia.org/wiki/Paper", DESTINATION_PAGE);
		
		assertEquals(new Integer(-1), response.getHopsOnPath());
		assertEquals("", response.getPathToDestination());
		assertEquals("Paper", response.getStartingPage());
		assertFalse(response.isPathExists());
	}
	
	@Test
	public void testPathDoesNotExistFromProton() {
		when(cache.contains(Mockito.mock(CacheKey.class))).thenReturn(false);
		when(repo.findPath(anyString(), anyString())).thenReturn(null);
		PathResponse response = service.getPathToPage("https://en.wikipedia.org/wiki/Proton", DESTINATION_PAGE);
		
		assertEquals(new Integer(-1), response.getHopsOnPath());
		assertEquals("", response.getPathToDestination());
		assertEquals("Proton", response.getStartingPage());
		assertFalse(response.isPathExists());
	}

}
