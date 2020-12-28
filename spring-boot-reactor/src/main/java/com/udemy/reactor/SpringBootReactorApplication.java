package com.udemy.reactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(SpringBootReactorApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		
		Flux<String> nombres = Flux.just("Jose","Norman","", "Quentin","Craig")
				.doOnNext(e -> {
					if(e.isEmpty()) {
						throw new RuntimeException("Nombre no pueden ser vacíos");
					} {
					System.out.println(e);
					}
					});
		
		nombres.subscribe(e -> log.info(e), error -> log.error(error.getMessage()));
		
	}

}
