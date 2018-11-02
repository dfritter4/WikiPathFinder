package com.fritz.philsofinder.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

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
import com.fritz.philsofinder.service.impl.WikiPathFindingServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
public class WikiPhilosophyPagePathFindingServiceTests {
	
	@Mock
	private CachingSystem cache;
	
	@Mock
	private PathResponseRepository repo;
	
	@InjectMocks
	private WikiPathFindingServiceImpl service;
	
	private final String PATH_FROM_ROCK_MUSIC = "Rock music -> Popular music -> Music industry -> Musical composition -> Music -> Culture -> Social behavior -> Behavior -> Action (philosophy) -> Philosophy";
	private final String PATH_FROM_CAPTAIN_AMERICA = "Captain America -> Character (arts) -> Person -> Reason -> Consciousness -> Quality (philosophy) -> Philosophy";
	private final String DESTINATION_PAGE = "Philosophy";
	private List<String> rockPathList;
	
	@Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        rockPathList = new LinkedList<>();
        rockPathList.add("Rock music");
        rockPathList.add("Popular music");
        rockPathList.add("Music industry");
        rockPathList.add("Musical composition");
        rockPathList.add("Music");
        rockPathList.add("Culture");
        rockPathList.add("Social behavior");
        rockPathList.add("Behavior");
        rockPathList.add("Action (philosophy)");
        rockPathList.add("Philosophy");
    }
	
	@Test
	public void testKnownPathFromRockMusic() {
		when(cache.contains(Mockito.mock(CacheKey.class))).thenReturn(false);
		when(repo.findByStartingPageAndDestinationPage(anyString(), anyString())).thenReturn(null);
		PathResponse response = service.getPathToPage("https://en.wikipedia.org/wiki/Rock_music", DESTINATION_PAGE);
		
		assertEquals(new Integer(9), response.getHopsOnPath());
		assertEquals(PATH_FROM_ROCK_MUSIC, response.buildPathString());
		assertEquals("Rock music", response.getStartingPage());
		assertTrue(response.isPathExists());
	}
	
	@Test
	public void testPathIsRetrievedFromCache() {
		
		//get path a second time to verify it's retrieved from cache
		CacheKey key = new CacheKey("Rock music", "Philosophy");
		when(cache.contains(key)).thenReturn(true);
		when(cache.get(key)).thenReturn(new PathResponse("Rock music", "Philosophy", rockPathList));
		PathResponse response = service.getPathToPage("https://en.wikipedia.org/wiki/Rock_music", "Philosophy");
		verify(repo, never()).findByStartingPageAndDestinationPage("Rock music", "Philosophy");

		assertEquals(new Integer(9), response.getHopsOnPath());
		assertEquals(PATH_FROM_ROCK_MUSIC, response.buildPathString());
		assertEquals("Rock music", response.getStartingPage());
		assertTrue(response.isPathExists());
	}
	
	@Test
	public void testPathIsRetrievedFromRepo() {
		
		//get path a second time to verify it's retrieved from cache
		CacheKey key = new CacheKey("Rock music", "Philosophy");
		when(cache.contains(key)).thenReturn(false);
		when(repo.findByStartingPageAndDestinationPage("Rock music", "Philosophy")).thenReturn(new PathResponse("Rock music", "Philosophy", rockPathList));
		PathResponse response = service.getPathToPage("https://en.wikipedia.org/wiki/Rock_music", "Philosophy");
		verify(repo, times(1)).findByStartingPageAndDestinationPage("Rock music", "Philosophy");

		assertEquals(new Integer(9), response.getHopsOnPath());
		assertEquals(PATH_FROM_ROCK_MUSIC, response.buildPathString());
		assertEquals("Rock music", response.getStartingPage());
		assertTrue(response.isPathExists());
	}
	
	@Test
	public void testKnownPathFromCaptainAmerica() {
		when(cache.contains(Mockito.mock(CacheKey.class))).thenReturn(false);
		when(repo.findByStartingPageAndDestinationPage(anyString(), anyString())).thenReturn(null);
		PathResponse response = service.getPathToPage("https://en.wikipedia.org/wiki/Captain_America", DESTINATION_PAGE);
		
		assertEquals(new Integer(6), response.getHopsOnPath());
		assertEquals(PATH_FROM_CAPTAIN_AMERICA, response.buildPathString());
		assertEquals("Captain America", response.getStartingPage());
		assertTrue(response.isPathExists());
	}
	
	@Test
	public void testPathDoesNotExistFromPaper() {
		when(cache.contains(Mockito.mock(CacheKey.class))).thenReturn(false);
		when(repo.findByStartingPageAndDestinationPage(anyString(), anyString())).thenReturn(null);
		PathResponse response = service.getPathToPage("https://en.wikipedia.org/wiki/Paper", DESTINATION_PAGE);
		
		assertEquals(new Integer(-1), response.getHopsOnPath());
		assertEquals("", response.buildPathString());
		assertEquals("Paper", response.getStartingPage());
		assertFalse(response.isPathExists());
	}
	
	@Test
	public void testPathDoesNotExistFromProton() {
		when(cache.contains(Mockito.mock(CacheKey.class))).thenReturn(false);
		when(repo.findByStartingPageAndDestinationPage(anyString(), anyString())).thenReturn(null);
		PathResponse response = service.getPathToPage("https://en.wikipedia.org/wiki/Proton", DESTINATION_PAGE);
		
		assertEquals(new Integer(-1), response.getHopsOnPath());
		assertEquals("", response.buildPathString());
		assertEquals("Proton", response.getStartingPage());
		assertFalse(response.isPathExists());
	}

}
