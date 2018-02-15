package com.benz.tutorial.springboot.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.benz.tutorial.springboot.vo.Movie;

@RepositoryRestResource
public interface MoviesRepo extends MongoRepository<Movie, String> {

	/**
	 * Method to search based on title
	 * 
	 * @param title
	 * @return
	 */
	public List<Movie> findByTitleRegex(@Param("title") String title);
}
