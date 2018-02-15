package com.benz.tutorial.springboot.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.benz.tutorial.springboot.SpringbootStudy2Application;
import com.benz.tutorial.springboot.vo.Movie;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootStudy2Application.class)
@AutoConfigureMockMvc
public class MoviesControllerTest {

	private static final Logger LOG = LoggerFactory.getLogger(MoviesControllerTest.class);

	@Autowired
	private MockMvc mvc;

	@Test
	public void testGetAllMovies() throws Exception {
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/movies").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		List<Movie> movies = mapper.readValue(result.getResponse().getContentAsString(),
				new TypeReference<List<Movie>>() {
				});

		LOG.debug("movies size " + movies.size());
		Assert.assertEquals("MOVIES SIZE MATCHES", movies.size(), 2295);
	}

	@Test
	public void testFindMovie_Success() throws Exception {
		MvcResult result = mvc.perform(
				MockMvcRequestBuilders.get("/movies/5692a51f24de1e0ce2dfdc81").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		LOG.debug("Found Movie {}", mapper.writeValueAsString(result.getResponse()));
	}

	@Test
	public void testFindMovie_fail() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/movies/123").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testFindMovies() throws Exception {
		MvcResult result = mvc
				.perform(MockMvcRequestBuilders.get("/movies?title=Stars").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		LOG.debug("Found Movies {}", mapper.writeValueAsString(result.getResponse()));
	}

}
