package com.fritz.philsofinder.controller;

import org.springframework.http.ResponseEntity;

import com.fritz.philsofinder.domain.PathResponse;

public interface PhilosoFinderController {

	public ResponseEntity<PathResponse> findPath(String startWikiPageUrl, String destinationPage);
	
}
