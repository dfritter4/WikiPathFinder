package com.fritz.philsofinder.domain;

import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "paths")
public class PathResponse {
	
	@Id
	private String uniqueId;
	
	@Indexed
	private String startingPage;
	
	@Indexed
	private String destinationPage;
	
	private List<String> hopsToDestination;
	private Date foundOnDate;
	
	public PathResponse(String startingPage, String destinationPage, List<String> hopsAlongDestination) {
		if(null == startingPage || null == destinationPage) {
			throw new IllegalArgumentException("startingPage or destinationPage cannot be null");
		}
		this.startingPage = startingPage;
		this.destinationPage = destinationPage;
		this.hopsToDestination = hopsAlongDestination;
		this.foundOnDate = new Date();
	}
	
	//for some reason Mongo/Spring needs this to
	//deserialize the object when retrieving from the mongo repo
	//making it private though because i believe Spring will change
	//it to public under the hood when attempting to call it via reflection
	//but i don't want to expose it via traditional methods
	@SuppressWarnings("unused")
	private PathResponse() {
		
	}
	
	// this constructs a "no path found" Object
	public PathResponse(String startingPage, String destinationPage) {
		if(null == startingPage || null == destinationPage) {
			throw new IllegalArgumentException("startingPage or destinationPage cannot be null");
		}
		this.startingPage = startingPage;
		this.destinationPage = destinationPage;
		this.hopsToDestination = Collections.emptyList();
		this.foundOnDate = new Date();
	}
	
	public String getStartingPage() { return this.startingPage; }
	public String getDestinationPage() { return this.destinationPage; }
	public Date getFoundOnDate() { return this.foundOnDate; }
	public Integer getHopsOnPath() { return this.hopsToDestination.size()-1; }
	public Boolean isPathExists() { return !this.hopsToDestination.isEmpty(); }
	public List<String> getHopsToDestination() { return this.hopsToDestination; }
	
	public String buildPathString() {
		StringBuilder path = new StringBuilder();
		for(Iterator<String> pathItr = this.hopsToDestination.iterator(); pathItr.hasNext();) {
			path.append(pathItr.next());
			if(pathItr.hasNext()) {
				path.append(" -> ");
			}
		}
		return path.toString();
	}

}
