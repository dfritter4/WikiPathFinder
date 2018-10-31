package com.fritz.philsofinder.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class FoundPath {
	
	@Id
	private String uniqueId;
	
	private final String startingPage;
	private final String pathToPhilosophy;
	private Date foundOnDate;
	
	public FoundPath(String startingPage, String pathToPhilosophy, Date foundOnDate) {
		this.startingPage = startingPage;
		this.pathToPhilosophy = pathToPhilosophy;
		this.foundOnDate = foundOnDate;
	}
	
	public String getStartingPage() { return this.startingPage; }
	public String getPathToPhilosophy() { return this.pathToPhilosophy; }
	public Date getFoundOnDate() { return this.foundOnDate; }

	@Override
	public String toString() {
		return "FoundPath [startingPage=" + startingPage 
				+ ", pathToPhilosophy=" + pathToPhilosophy 
				+ ", foundOnDate=" + foundOnDate
				+ "]";
	}

}
