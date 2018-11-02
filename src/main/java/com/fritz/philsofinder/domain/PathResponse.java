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
	
	@Indexed
	private final String destinationPage;
	
	private final String pathToDestination;
	private final Integer hopsOnPath;
	private Date foundOnDate;
	
	public PathResponse(String startingPage, String destinationPage, String pathToDestination, Integer hopsOnPath) {
		this.startingPage = startingPage;
		this.destinationPage = destinationPage;
		this.pathToDestination = pathToDestination;
		this.hopsOnPath = hopsOnPath;
		this.foundOnDate = new Date();
	}
	
	// this constructs a "no path found" Object
	public PathResponse(String startingPage, String destinationPage) {
		this.startingPage = startingPage;
		this.destinationPage = destinationPage;
		this.pathToDestination = "";
		this.hopsOnPath = -1;
		this.foundOnDate = new Date();
	}
	
	public String getStartingPage() { return this.startingPage; }
	public String getDestinationPage() { return this.destinationPage; }
	public String getPathToDestination() { return this.pathToDestination; }
	public Date getFoundOnDate() { return this.foundOnDate; }
	public Integer getHopsOnPath() { return this.hopsOnPath; }
	public Boolean isPathExists() { return this.hopsOnPath != -1; }

	@Override
	public String toString() {
		return "FoundPath [startingPage=" + startingPage 
				+ ", destinationPage=" + destinationPage 
				+ ", pathToDestination=" + pathToDestination 
				+ ", foundOnDate=" + foundOnDate
				+ ", hopsOnPath=" + hopsOnPath
				+ "]";
	}

}
