package com.benz.tutorial.springboot.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.benz.tutorial.springboot.repo.MoviesRepo;
import com.benz.tutorial.springboot.vo.Movie;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;

@RestController
@RequestMapping("/movies")
public class MoviesController {

	private static final Logger LOG = LoggerFactory.getLogger(MoviesController.class);

	@Autowired
	private MoviesRepo moviesRepo;

	/**
	 * Method to retrieve all the movies
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Movie>> getAllMovies(@RequestParam(name = "title", required = false) String title) {
		LOG.debug("Entering getAllMovies");
		List<Movie> movies = null;
		if (StringUtils.isEmpty(title)) {
			movies = moviesRepo.findAll();
		} else {
			movies = moviesRepo.findByTitleRegex( title + ".*");
		}
		LOG.debug("Movies Size {}", movies.size());
		if (!CollectionUtils.isEmpty(movies)) {
			return ResponseEntity.ok(movies);
		}
		return ResponseEntity.notFound().build();
	}

	/**
	 * Method to retrieve based on id
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Movie> findMovie(@PathVariable("id") String id) {
		LOG.debug("Entering findMovie");
		Movie movie = moviesRepo.findOne(id);
		if (null != movie) {
			return ResponseEntity.ok(movie);
		}
		return ResponseEntity.notFound().build();

	}

}
