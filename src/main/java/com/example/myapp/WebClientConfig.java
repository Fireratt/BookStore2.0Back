package com.example.myapp;

import java.net.http.HttpHeaders;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    	// create a Dependency Injection function , to initialize the WebClient to call other services online . 
	@Bean
	public WebClient webClient(){
        return WebClient.builder()
        .baseUrl("http://localhost:8080")
        .defaultCookie("cookieKey", "cookieValue")
        .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE) 
        .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8080"))
        .build();
	}
}
