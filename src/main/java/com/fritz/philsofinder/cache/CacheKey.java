package com.fritz.philsofinder.cache;

public class CacheKey {

	private final String startPage;
	private final String endPage;
	
	public CacheKey(String startPage, String endPage) {
		if(startPage == null || endPage == null) {
			throw new IllegalArgumentException("Start and end page must not be null");
		}
		this.startPage = startPage;
		this.endPage = endPage;
	}
	
	public String getStartPage() {
		return startPage;
	}

	public String getEndPage() {
		return endPage;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endPage == null) ? 0 : endPage.hashCode());
		result = prime * result + ((startPage == null) ? 0 : startPage.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CacheKey other = (CacheKey) obj;
		if(!endPage.equals(other.endPage))
			return false;
		if (!startPage.equals(other.startPage))
			return false;
		return true;
	}
}
