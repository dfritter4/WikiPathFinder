package com.fritz.philsofinder.service;

import com.fritz.philsofinder.domain.PathResponse;

public interface WikiPathFindingService {
	
	public PathResponse getPathToPage(String startPageUrl, String destinationPageName);

}
