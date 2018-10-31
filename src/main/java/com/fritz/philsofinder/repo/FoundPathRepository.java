package com.fritz.philsofinder.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fritz.philsofinder.domain.FoundPath;

public interface FoundPathRepository extends MongoRepository<FoundPath, String>{
	
	

}
