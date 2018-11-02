package com.fritz.philsofinder.service;

import com.fritz.philsofinder.domain.PathResponse;

public interface WikiPhilosophyPagePathFindingService {
	
	public PathResponse getPathToPhilosophy(String startPageUrl);

}
