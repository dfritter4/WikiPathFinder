package com.fritz.philsofinder.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fritz.philsofinder.domain.PathResponse;
import com.fritz.philsofinder.repo.PathResponseRepository;
import com.fritz.philsofinder.service.impl.WikiPathFindingServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
public class WikiPhilosophyPagePathFindingServiceTests {
	
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
		when(repo.findByStartingPageAndDestinationPage(anyString(), anyString())).thenReturn(null);
		PathResponse response = service.getPathToPage("https://en.wikipedia.org/wiki/Rock_music", DESTINATION_PAGE);
		
		assertEquals(new Integer(9), response.getHopsCount());
		assertEquals(PATH_FROM_ROCK_MUSIC, response.getPathString());
		assertEquals("Rock music", response.getStartingPage());
		assertTrue(response.isPathExists());
	}
	
	@Test
	public void testPathIsRetrievedFromCache() {
		
		PathResponse response = service.getPathToPage("https://en.wikipedia.org/wiki/Rock_music", "Philosophy");
		verify(repo, times(1)).findByStartingPageAndDestinationPage("Rock music", "Philosophy");

		assertEquals(new Integer(9), response.getHopsCount());
		assertEquals(PATH_FROM_ROCK_MUSIC, response.getPathString());
		assertEquals("Rock music", response.getStartingPage());
		assertTrue(response.isPathExists());
	}
	
	@Test
	public void testAlreadyFoundPathRetreivedFromCacheAndMerged() {
		when(repo.findByStartingPageAndDestinationPage("Rock music", "Philosophy")).thenReturn(null);
		when(repo.findByStartingPageAndDestinationPage("Music industry", "Philosophy")).thenReturn(new PathResponse("Rock music", "Philosophy", rockPathList));
		PathResponse response = service.getPathToPage("https://en.wikipedia.org/wiki/Rock_music", "Philosophy");
		verify(repo, times(1)).findByStartingPageAndDestinationPage("Rock music", "Philosophy");
		assertEquals("Rock music", response.getStartingPage());
	}
	
	@Test
	public void testPathIsRetrievedFromRepo() {
		when(repo.findByStartingPageAndDestinationPage("Rock music", "Philosophy")).thenReturn(new PathResponse("Rock music", "Philosophy", rockPathList));
		PathResponse response = service.getPathToPage("https://en.wikipedia.org/wiki/Rock_music", "Philosophy");
		verify(repo, times(1)).findByStartingPageAndDestinationPage("Rock music", "Philosophy");

		assertEquals(new Integer(9), response.getHopsCount());
		assertEquals(PATH_FROM_ROCK_MUSIC, response.getPathString());
		assertEquals("Rock music", response.getStartingPage());
		assertEquals(new Date().toString(), response.getFoundOnDate().toString());
		assertTrue(response.isPathExists());
	}
	
	@Test
	public void testKnownPathFromCaptainAmerica() {
		when(repo.findByStartingPageAndDestinationPage(anyString(), anyString())).thenReturn(null);
		PathResponse response = service.getPathToPage("https://en.wikipedia.org/wiki/Captain_America", DESTINATION_PAGE);
		
		assertEquals(new Integer(6), response.getHopsCount());
		assertEquals(PATH_FROM_CAPTAIN_AMERICA, response.getPathString());
		assertEquals("Captain America", response.getStartingPage());
		assertEquals(new Date().toString(), response.getFoundOnDate().toString());
		assertTrue(response.isPathExists());
	}
	
	@Test
	public void testPathDoesNotExistFromPaper() {
		when(repo.findByStartingPageAndDestinationPage(anyString(), anyString())).thenReturn(null);
		PathResponse response = service.getPathToPage("https://en.wikipedia.org/wiki/Paper", DESTINATION_PAGE);
		
		assertEquals(new Integer(-1), response.getHopsCount());
		assertEquals("", response.getPathString());
		assertEquals("Paper", response.getStartingPage());
		assertEquals(new Date().toString(), response.getFoundOnDate().toString());
		assertFalse(response.isPathExists());
	}
	
	@Test
	public void testPathDoesNotExistFromProton() {
		when(repo.findByStartingPageAndDestinationPage(anyString(), anyString())).thenReturn(null);
		PathResponse response = service.getPathToPage("https://en.wikipedia.org/wiki/Proton", DESTINATION_PAGE);
		
		assertEquals(new Integer(-1), response.getHopsCount());
		assertEquals("", response.getPathString());
		assertEquals("Proton", response.getStartingPage());
		assertEquals(new Date().toString(), response.getFoundOnDate().toString());
		assertFalse(response.isPathExists());
	}

}
