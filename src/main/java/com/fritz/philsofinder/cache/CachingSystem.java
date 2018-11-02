package com.fritz.philsofinder.cache;

import com.fritz.philsofinder.domain.PathResponse;

public interface CachingSystem {
	
	public void put(String key, PathResponse value);
	public PathResponse get(String key);
	public Boolean contains(String key);
	
}
