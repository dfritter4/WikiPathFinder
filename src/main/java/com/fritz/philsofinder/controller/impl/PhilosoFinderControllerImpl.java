package com.fritz.philsofinder.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fritz.philsofinder.cache.CachingSystem;
import com.fritz.philsofinder.controller.PhilosoFinderController;

@RestController
public class PhilosoFinderControllerImpl implements PhilosoFinderController {

	@Autowired
	private CachingSystem cache;
	
	@Autowired
	private 
	
	@Override
	@RequestMapping("/findpath")
	public String findPath(String startWikiPageUrl) {
		return null;
	}

}
