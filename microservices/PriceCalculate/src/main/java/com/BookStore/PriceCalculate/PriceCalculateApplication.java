package com.BookStore.PriceCalculate;

import java.util.function.Function;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
@SpringBootApplication
public class PriceCalculateApplication {

	public static void main(String[] args) {
		SpringApplication.run(PriceCalculateApplication.class, args);
	}
	@Bean
	public Function<Flux<Integer[]> , Flux<Integer>> getPrice(){
		return (Flux<Integer[]> flux) -> 
		{
			System.out.println("Receive Flux Success");
			return flux.map(value -> value[0] * value[1]) ; 
		} ; 
	}
}
