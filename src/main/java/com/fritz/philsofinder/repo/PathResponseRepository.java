package com.fritz.philsofinder.repo;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fritz.philsofinder.domain.PathResponse;

@Repository
public interface PathResponseRepository extends MongoRepository<PathResponse, String>{
	
	@Cacheable("paths")
	PathResponse findByStartingPageAndDestinationPage(String startingPage, String destinationPage);

}
