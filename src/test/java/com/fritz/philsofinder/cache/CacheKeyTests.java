package com.fritz.philsofinder.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class CacheKeyTests {
	
	@Test
	public void testGetStartAndEndPages() {
		CacheKey key = new CacheKey("start", "end");
		assertEquals("start", key.getStartPage());
		assertEquals("end", key.getEndPage());
	}
	
	@Test
	public void testEqualKeys() {
		CacheKey key1 = new CacheKey("start", "end");
		CacheKey key2 = new CacheKey("start", "end");
		assertEquals(key1, key2);
	}
	
	@Test
	public void testSameKeys() {
		CacheKey key1 = new CacheKey("start", "end");
		CacheKey key2 = key1;
		assertEquals(key1, key2);
	}
	
	@Test
	public void testUnequalKeys() {
		CacheKey key1 = new CacheKey("start", "end");
		CacheKey key2 = new CacheKey("start2", "end2");
		assertNotEquals(key1, key2);
		assertNotEquals(key1, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalStartPage() {
		new CacheKey(null, "end");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalEndPage() {
		new CacheKey("start", null);
	}
	
	@Test
	public void testHashCode() {
		CacheKey key1 = new CacheKey("start", "end");
		CacheKey key2 = new CacheKey("start", "end");
		assertEquals(key1.hashCode(), key2.hashCode());
	}
}
