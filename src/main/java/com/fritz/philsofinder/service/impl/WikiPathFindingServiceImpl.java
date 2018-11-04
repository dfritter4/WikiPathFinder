package com.fritz.philsofinder.service.impl;

import java.util.AbstractSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fritz.philsofinder.domain.PathResponse;
import com.fritz.philsofinder.repo.PathResponseRepository;
import com.fritz.philsofinder.service.WikiPathFindingService;
import com.fritz.philsofinder.util.JsoupUtilities;

@Component
public class WikiPathFindingServiceImpl implements WikiPathFindingService {
	
	@Autowired
	private PathResponseRepository repo;
			
	@Override
	public PathResponse getPathToPage(String startPageUrl, String destinationPageName) {

		// 1) check the cache if the path exists from the start page
		// 2) if its not in the cache, check the repo
		// 3) if it's not in the repo run the path finding algo
		Document startPageDoc = JsoupUtilities.connectAndGetPage(startPageUrl);
		String startPageName = JsoupUtilities.getPageName(startPageDoc);
		
		PathResponse foundPath = findPathToDestinationPage(startPageName, destinationPageName, startPageDoc);
		
		addToCacheAndRepo(foundPath);
		
		return foundPath;
		
	}
	
	private void addToCacheAndRepo(PathResponse foundPath) {
		//will add the path AND all sub-paths from start to destination
		//to the cache and repository
		for(int hopPos = 0; hopPos < foundPath.getHopsToDestination().size(); ++hopPos) {
			List<String> fromHopToDestination = foundPath.getHopsToDestination().subList(hopPos, foundPath.getHopsToDestination().size());
			PathResponse intermediatePathResponse  = new PathResponse(fromHopToDestination.get(0), foundPath.getDestinationPage(), fromHopToDestination);
			repo.save(intermediatePathResponse);
		}
	}
	//primary path finding algorithm
	private PathResponse findPathToDestinationPage(String startPageName, String destinationPageName, Document startPageDoc) {
		
		PathResponse path = null;
		path = repo.findByStartingPageAndDestinationPage(startPageName, destinationPageName);
		if(null != path) {
			return path;
		}
		
		String nextPageName = "";
		AbstractSet<String> pathSet = new LinkedHashSet<String>();
		pathSet.add(startPageName);
				
		Element href = JsoupUtilities.getFirstValidLink(startPageDoc);
		
		while(!destinationPageName.equals(nextPageName) && null != href) {
			
			Document nextPage = JsoupUtilities.connectAndGetPage(href.absUrl("href"));
			nextPageName = JsoupUtilities.getPageName(nextPage);
			
			path = repo.findByStartingPageAndDestinationPage(nextPageName, destinationPageName);
			if(null != path) {
				return mergePaths(new LinkedList<String>(pathSet), path);
			}
			
			if(pathSet.contains(nextPageName)) {
				//found a loop, break and return a "no path exists" object
				return new PathResponse(startPageName, destinationPageName);
			}
			
			pathSet.add(nextPageName);
			href = JsoupUtilities.getFirstValidLink(nextPage);
		}
		
		if("Philosophy".equals(nextPageName)) {
			return new PathResponse(startPageName, destinationPageName, new LinkedList<String>(pathSet));
		} else {
			//we might have reached a page that was deleted or has no valid links in the main body of the
			//article, so we'll return a "no path exists" object
			return new PathResponse(startPageName, destinationPageName);
		}
		
	}
	
	private PathResponse mergePaths(List<String> currentPath, PathResponse remainingPath) {
		currentPath.addAll(remainingPath.getHopsToDestination());
		return new PathResponse(currentPath.get(0), currentPath.get(currentPath.size()-1), currentPath);
	}

}
