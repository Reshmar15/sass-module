package com.sass.sass_module;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Component
@PropertySource("classpath:application.properties")
@RestController
@RequestMapping(value = "/sassmodule")
public class SassController {

	@Autowired
	private Environment env;
	
	RestTemplate restTemplate = new RestTemplate();
	Logger logger = LoggerFactory.getLogger(SassController.class);
		
	@RequestMapping(value = "/createaccount", method = RequestMethod.POST)
	public ResponseEntity<String> accountCreation(@RequestBody String body) {
		
		logger.debug("Inside accountCreation method");
		ResponseEntity<String> response = restTemplate.exchange(
				getUrl() + "AccountCreation/", HttpMethod.POST, getAuth(body), String.class);
		logger.debug("accountCreation method execution completed");
		
		return (ResponseEntity<String>) response;
	}


	@RequestMapping(value = "/billing", method = RequestMethod.GET)
	public ResponseEntity<String> getBillingInfo() {
		
		logger.debug("Inside getBillingInfo method");
		ResponseEntity<String> response = restTemplate.exchange(
				getUrl() + "BillingInformation/", HttpMethod.GET,  getAuth(null), String.class);
		logger.debug("getBillingInfo method execution completed");
		
		return (ResponseEntity<String>) response;
	}
	
	@RequestMapping(value = "/suspendaccount", method = RequestMethod.POST)
	public ResponseEntity<String> accountSuspension(@RequestBody String body) {
		
		logger.debug("Inside accountSuspension method");
		ResponseEntity<String> response = restTemplate.exchange(
				getUrl() + "AccountSuspension/", HttpMethod.POST, getAuth(body), String.class);
		logger.debug("accountSuspension method executed");
		
		return (ResponseEntity<String>) response;
	}
	
	@RequestMapping(value = "/createproduct", method = RequestMethod.POST)
	public ResponseEntity<String> productCreation(@RequestBody String body) {
		
		logger.debug("Inside productCreation method");
		ResponseEntity<String> response = restTemplate.exchange(
				getUrl() + "ProductCreation/", HttpMethod.POST, getAuth(body), String.class);
		logger.debug("productCreation method execution completed");
		
		return (ResponseEntity<String>) response;
	}
	
	@RequestMapping(value = "/suspendproduct", method = RequestMethod.POST)
	public ResponseEntity<String> productSuspension(@RequestBody String body) {
		
		logger.debug("Inside productSuspension method");
		ResponseEntity<String> response = restTemplate.exchange(
				getUrl() + "ProductSuspension/", HttpMethod.POST, getAuth(body), String.class);
		logger.debug("productSuspension method executed");
		
		return (ResponseEntity<String>) response;
	}
	
	/*
	 * getUrl
	 * Method to get the API URL from Application Property
	 */
	private String getUrl() {
		return env.getProperty("api.url");
	}
	
	/*
	 * getAuth
	 * Method for HTTP basic AUTH with credentials 
	 */
	private HttpEntity<String> getAuth(String body) {
		
		logger.debug("Inside getAuth method");
		String username = env.getProperty("api.username");
		String password = env.getProperty("api.password");
		String plainCreds = username+":"+password;
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);
		HttpEntity<String> request = new HttpEntity<String>(body,headers);
		
		logger.debug("getAuth method executed");
		return request;
	}

}
