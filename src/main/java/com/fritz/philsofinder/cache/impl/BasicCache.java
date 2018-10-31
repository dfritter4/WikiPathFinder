package com.fritz.philsofinder.cache.impl;

import java.util.HashMap;
import java.util.Map;

import com.fritz.philsofinder.cache.CachingSystem;

/**
 * 
 * @author David Fritz
 * 
 * This class will serve as a first-level cache for
 * found paths to the Philosophy wiki page implemented
 * as a simple HashMap
 *
 */
public class BasicCache implements CachingSystem {

	private Map<String, String> cache;
	
	public BasicCache() {
		cache = new HashMap<String, String>();
	}
	
	@Override
	public void put(String key, String value) {
		this.cache.put(key, value);
	}

	@Override
	public String get(String key) {
		return this.cache.get(key);
	}

	@Override
	public Boolean contains(String key) {
		return this.cache.containsKey(key);
	}

}
