package com.fritz.philsofinder.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fritz.philsofinder.controller.PhilosoFinderController;
import com.fritz.philsofinder.domain.PathResponse;
import com.fritz.philsofinder.service.WikiPhilosophyPagePathFindingService;

@CrossOrigin("*")
@RequestMapping("/api")
@RestController
public class PhilosoFinderControllerImpl implements PhilosoFinderController {

	@Autowired
	private WikiPhilosophyPagePathFindingService service;
	
	//hard-coded for now, but could easily switch the findPath end-point
	//to accept any destination page
	private final String destinationPage = "Philosophy"; 
	
	@Override
	@GetMapping("/findpath")
	public ResponseEntity<PathResponse> findPath(@RequestParam("startUrl") String startUrl) {
		PathResponse pathResponse =  service.getPathToPage(startUrl, destinationPage);
		
		ResponseEntity<PathResponse> serviceResponse = null;
		if(pathResponse.isPathExists()) {
			serviceResponse = ResponseEntity.ok().body(pathResponse);
		} else {
			serviceResponse = ResponseEntity.notFound().build();
		}
		
		return serviceResponse;
	}

}
