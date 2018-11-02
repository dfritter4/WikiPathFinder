package com.fritz.philsofinder.cache;

public class CacheKey {

	private final String startPage;
	private final String endPage;
	
	public CacheKey(String startPage, String endPage) {
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
		if (endPage == null) {
			if (other.endPage != null)
				return false;
		} else if (!endPage.equals(other.endPage))
			return false;
		if (startPage == null) {
			if (other.startPage != null)
				return false;
		} else if (!startPage.equals(other.startPage))
			return false;
		return true;
	}
}
