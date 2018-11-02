package com.fritz.philsofinder.cache.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fritz.philsofinder.cache.CacheKey;
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

	private Map<CacheKey, PathResponse> cache;
	
	public BasicCache() {
		cache = new HashMap<CacheKey, PathResponse>();
	}
	
	@Override
	public void put(CacheKey key, PathResponse value) {
		this.cache.put(key, value);
	}

	@Override
	public PathResponse get(CacheKey key) {
		return this.cache.get(key);
	}

	@Override
	public Boolean contains(CacheKey key) {
		return this.cache.containsKey(key);
	}

}
