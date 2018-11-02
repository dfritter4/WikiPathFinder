package com.fritz.philsofinder.controller;

import org.springframework.http.ResponseEntity;

import com.fritz.philsofinder.domain.PathResponse;

public interface WikiPathFinderController {

	public ResponseEntity<PathResponse> findPath(String startWikiPageUrl, String destinationPage);
	
}
