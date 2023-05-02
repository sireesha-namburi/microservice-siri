package com.shiva.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class RetryController {

	Logger logger = LoggerFactory.getLogger(RetryController.class);

	RestTemplate res=new RestTemplate();
	
	@GetMapping("/welcome")
	//@Retry(name="getWelcomeRetry",fallbackMethod = "getThink")
	@CircuitBreaker (name="getWelcomeRetry",fallbackMethod = "getThink")
	public String getWelcomeMsg()
	{
		logger.info("getWelcomeMsg is start here");
		ResponseEntity<String> entity = res.getForEntity("http://localhost:9898/payment/getMessage", String.class);
		logger.info("Response : "+entity.getStatusCode());
	return entity.getBody();
	
	
	}
	
	public String getThink(Exception e)
	{
		logger.info("Response from fallback method");
		return "Service is Down,Please Try after Some Time";
	}
	
}
