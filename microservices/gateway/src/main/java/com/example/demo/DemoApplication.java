package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(r -> r.path("/author/**")
				.filters(f->f.rewritePath("/author" , ""))
				.uri("lb://Author-Service")
				)
				.route(r -> r.path("/price/**")
				.filters(f->f.rewritePath("/price" , ""))
				.uri("lb://Price-Function")
				)
				.route(r -> r.path("/main/**")
				.filters(f->f.rewritePath("/main" , ""))
				.uri("lb://Main-Process")
				)
				.build();
	}
}
