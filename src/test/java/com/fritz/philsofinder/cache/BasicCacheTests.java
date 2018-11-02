package com.fritz.philsofinder.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.fritz.philsofinder.cache.impl.BasicCache;
import com.fritz.philsofinder.domain.PathResponse;

public class BasicCacheTests {
	
	private BasicCache cache;
	
	@Before
	public void setUp() {
		cache = new BasicCache();
	}
	
	@Test
	public void testRetreiveCacheWithEntry() {
		CacheKey key = new CacheKey("start","end"); 
		PathResponse path = new PathResponse("start","end", Arrays.asList("start", ""));
		cache.put(key, path);
		
		PathResponse cacheResponse = cache.get(key);
		assertEquals(path, cacheResponse);
	}
	
	@Test
	public void testRetrieveCacheWithoutEntry() {
		CacheKey key = new CacheKey("start","end"); 
		PathResponse cacheResponse = cache.get(key);
		
		assertNull(cacheResponse);
	}
	
	@Test
	public void testCacheContainsEntry() {
		CacheKey key = new CacheKey("start","end"); 
		PathResponse path = new PathResponse("start","end", Arrays.asList("start", ""));
		cache.put(key, path);
		assertTrue(cache.contains(key));
	}

}
