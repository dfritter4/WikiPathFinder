package com.fritz.philsofinder.cache;

public interface CachingSystem {
	
	public void put(String key, String value);
	public String get(String key);
	public Boolean contains(String key);
	
}
