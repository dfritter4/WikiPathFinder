package com.fritz.philsofinder.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fritz.philsofinder.controller.WikiPathFinderController;
import com.fritz.philsofinder.domain.PathResponse;
import com.fritz.philsofinder.service.WikiPathFindingService;

@CrossOrigin("*")
@RequestMapping("/api")
@RestController
public class WikiPathFinderControllerImpl implements WikiPathFinderController {

	@Autowired
	private WikiPathFindingService service;
	
	@Override
	@GetMapping("/findpath")
	public ResponseEntity<PathResponse> findPath(
			@RequestParam("startUrl") String startUrl,
			@RequestParam(value = "destinationPage", defaultValue = "Philosophy") String destinationPage) {
		
		PathResponse pathResponse =  service.getPathToPage(startUrl, destinationPage);
		
		//will return HTTP.200 status even if the path isn't found
		//the front-end can handle how to display a PathResponse
		//where the destinationToPath is empty and hops = -1
		return ResponseEntity.ok().body(pathResponse);
	}

}
