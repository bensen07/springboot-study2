package com.benz.tutorial.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */
@RestController
public class HelloWorld {

	private static final Logger LOG = LoggerFactory.getLogger(HelloWorld.class);

	@RequestMapping("/")
	public String hello() {
		LOG.info("Entering hello");
		return "Benson Playing around";
	}

}
