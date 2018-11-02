package com.fritz.philsofinder.cache.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fritz.philsofinder.cache.CachingSystem;
import com.fritz.philsofinder.domain.PathResponse;

/**
 * 
 * This class will serve as a first-level cache for
 * found paths to the Philosophy wiki page, implemented
 * as a simple HashMap
 *
 */
@Component
public class BasicCache implements CachingSystem {

	private Map<String, PathResponse> cache;
	
	public BasicCache() {
		cache = new HashMap<String, PathResponse>();
	}
	
	@Override
	public void put(String key, PathResponse value) {
		this.cache.put(key, value);
	}

	@Override
	public PathResponse get(String key) {
		return this.cache.get(key);
	}

	@Override
	public Boolean contains(String key) {
		return this.cache.containsKey(key);
	}

}
