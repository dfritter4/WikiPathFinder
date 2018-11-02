package com.fritz.philsofinder.service.impl;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
	public PathResponse getPathToPhilosophy(String startPageUrl) {

		// 1) check the cache if the path exists from the start page
		// 2) if its not in the cache, check the repo
		// 3) if it's not in the repo run the path finding algo
		Document startPageDoc = JsoupUtilities.connectAndGetPage(startPageUrl);
		String startPageName = JsoupUtilities.getPageName(startPageDoc);
		
		PathResponse foundPath = getFromCacheOrRepo(startPageName);
		
		if(null != foundPath) {
			return foundPath;
		}
		
		foundPath = findPathToPhiloPage(startPageName, startPageDoc);
		
		addToCacheAndRepo(foundPath);
		
		return foundPath;
		
	}
	
	private void addToCacheAndRepo(PathResponse foundPath) {
		cache.put(foundPath.getStartingPage(), foundPath);
		repo.save(foundPath);
	}
	
	private PathResponse getFromCacheOrRepo(String pageName) {
		if(cache.contains(pageName)) {
			return cache.get(pageName);
		}
		
		return repo.findByStartingPage(pageName);
	}
	
	private PathResponse findPathToPhiloPage(String startPageName, Document startPageDoc) {
		
		//main path finding algorithm
		Integer hops = 0;
		String nextPageName = "";
		AbstractSet<String> pathSet = new LinkedHashSet<String>();
		pathSet.add(startPageName);
				
		Element href = JsoupUtilities.getFirstValidLink(startPageDoc);
		while(!"Philosophy".equals(nextPageName) && null != href) {
			Document nextPage = JsoupUtilities.connectAndGetPage(href.absUrl("href"));
			nextPageName = JsoupUtilities.getPageName(nextPage);
			
			if(pathSet.contains(nextPageName)) {
				//found a loop, break and return a "no path exists" object
				return new PathResponse(startPageName);
			}
			
			pathSet.add(nextPageName);
			href = JsoupUtilities.getFirstValidLink(nextPage);
			hops++;
		}
		
		String path = buildPathString(pathSet);
		return new PathResponse(startPageName, path, hops);
		
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
