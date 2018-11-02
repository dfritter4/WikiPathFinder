package com.fritz.philsofinder.service.impl;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fritz.philsofinder.cache.CacheKey;
import com.fritz.philsofinder.cache.CachingSystem;
import com.fritz.philsofinder.domain.PathResponse;
import com.fritz.philsofinder.repo.PathResponseRepository;
import com.fritz.philsofinder.service.WikiPhilosophyPagePathFindingService;
import com.fritz.philsofinder.util.JsoupUtilities;

@Component
public class WikiPhilosophyPagePathFindingServiceImpl implements WikiPhilosophyPagePathFindingService {

	@Autowired
	private CachingSystem cache;
	
	@Autowired
	private PathResponseRepository repo;
			
	@Override
	public PathResponse getPathToPage(String startPageUrl, String destinationPageName) {

		// 1) check the cache if the path exists from the start page
		// 2) if its not in the cache, check the repo
		// 3) if it's not in the repo run the path finding algo
		Document startPageDoc = JsoupUtilities.connectAndGetPage(startPageUrl);
		String startPageName = JsoupUtilities.getPageName(startPageDoc);
		
		PathResponse foundPath = getFromCacheOrRepo(new CacheKey(startPageName, destinationPageName));
		
		if(null != foundPath) {
			return foundPath;
		}
		
		foundPath = findPathToPhiloPage(startPageName, destinationPageName, startPageDoc);
		
		addToCacheAndRepo(foundPath);
		
		return foundPath;
		
	}
	
	private void addToCacheAndRepo(PathResponse foundPath) {
		cache.put(new CacheKey(foundPath.getStartingPage(), foundPath.getDestinationPage()), foundPath);
		repo.save(foundPath);
	}
	
	private PathResponse getFromCacheOrRepo(CacheKey key) {
		if(cache.contains(key)) {
			return cache.get(key);
		}
		
		return repo.findPath(key.getStartPage(), key.getEndPage());
	}
	
	private PathResponse findPathToPhiloPage(String startPageName, String destinationPageName, Document startPageDoc) {
		
		//main path finding algorithm
		Integer hops = 0;
		String nextPageName = "";
		AbstractSet<String> pathSet = new LinkedHashSet<String>();
		pathSet.add(startPageName);
				
		Element href = JsoupUtilities.getFirstValidLink(startPageDoc);
		while(!destinationPageName.equals(nextPageName) && null != href) {
			Document nextPage = JsoupUtilities.connectAndGetPage(href.absUrl("href"));
			nextPageName = JsoupUtilities.getPageName(nextPage);
			
			if(pathSet.contains(nextPageName)) {
				//found a loop, break and return a "no path exists" object
				return new PathResponse(startPageName, destinationPageName);
			}
			
			pathSet.add(nextPageName);
			href = JsoupUtilities.getFirstValidLink(nextPage);
			hops++;
		}
		
		String path = buildPathString(pathSet);
		return new PathResponse(startPageName, destinationPageName, path, hops);
		
	}
	
	private String buildPathString(AbstractSet<String> pathSet) {
		StringBuilder path = new StringBuilder();
		Iterator<String> pathIter = pathSet.iterator();
		while(pathIter.hasNext()) {
			path.append(pathIter.next());
			if(pathIter.hasNext()) {
				path.append(" -> ");
			}
		}
		return path.toString();
	}

}
