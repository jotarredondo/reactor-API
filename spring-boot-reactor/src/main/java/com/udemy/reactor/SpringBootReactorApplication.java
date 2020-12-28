package com.udemy.reactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.udemy.reactor.entity.Usuario;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(SpringBootReactorApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		
		Flux<Usuario> nombres = Flux.just("Jose","Norman","Sasha", "Quentin","Craig")
				.map(nombre -> new Usuario(nombre.toUpperCase(), null))
				.doOnNext(usuario -> {
					if(usuario == null) {
						throw new RuntimeException("Nombre no pueden ser vacíos");
					} 
					System.out.println(usuario.getNombre());
					
					})
				.map(usuario -> {String nombre = usuario.toString();
				usuario.setNombre(nombre);
				return usuario;
				});
		
		nombres.subscribe(e -> log.info(e.getNombre()), error -> log.error(error.getMessage()),
				new Runnable() {

					@Override
					public void run() {
						log.info("Ha finalizado la ejecución del observable con éxito!");
						
					}
		});
		
	}

}
