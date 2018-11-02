package com.fritz.philsofinder.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "paths")
public class PathResponse {
	
	@Id
	private String uniqueId;
	
	@Indexed
	private final String startingPage;
	
	private final String pathToPhilosophy;
	private final Integer hopsOnPath;
	private Date foundOnDate;
	
	public PathResponse(String startingPage, String pathToPhilosophy, Integer hopsOnPath) {
		this.startingPage = startingPage;
		this.pathToPhilosophy = pathToPhilosophy;
		this.hopsOnPath = hopsOnPath;
		this.foundOnDate = new Date();
	}
	
	public PathResponse(String startingPage) {
		this.startingPage = startingPage;
		this.pathToPhilosophy = "";
		this.hopsOnPath = -1;
		this.foundOnDate = new Date();
	}
	
	public String getStartingPage() { return this.startingPage; }
	public String getPathToPhilosophy() { return this.pathToPhilosophy; }
	public Date getFoundOnDate() { return this.foundOnDate; }
	public Integer getHopsOnPath() { return this.hopsOnPath; }
	public Boolean isPathExists() { return this.hopsOnPath != -1; }

	@Override
	public String toString() {
		return "FoundPath [startingPage=" + startingPage 
				+ ", pathToPhilosophy=" + pathToPhilosophy 
				+ ", foundOnDate=" + foundOnDate
				+ ", hopsOnPath=" + hopsOnPath
				+ "]";
	}

}
