package com.fritz.philsofinder.cache;

import com.fritz.philsofinder.domain.PathResponse;

public interface CachingSystem {
	
	public void put(CacheKey key, PathResponse value);
	public PathResponse get(CacheKey key);
	public Boolean contains(CacheKey key);
	
}
