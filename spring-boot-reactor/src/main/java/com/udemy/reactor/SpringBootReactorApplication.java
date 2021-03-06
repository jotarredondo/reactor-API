package com.udemy.reactor;

import java.util.ArrayList;
import java.util.List;

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
		
		List<String> usuariosList =  new ArrayList<String>();
		usuariosList.add("Jose Arredondo");
		usuariosList.add("Norman Reedus");
		usuariosList.add("Sasha Grey");
		usuariosList.add("Quentin Tarantino");
		usuariosList.add("Craig Ferguson");
		usuariosList.add("Sasha Blue");
		
		Flux<String> nombres = Flux.fromIterable(usuariosList);/*Flux.just("Jose Arredondo","Norman Reedus","Sasha Grey", "Quentin Tarantino","Craig Ferguson", "Sasha Blue");*/
				Flux<Usuario> usuarios = nombres.map(nombre -> new Usuario(nombre.split(" ")[0].toUpperCase(), nombre.split(" ")[1].toUpperCase()))
				.filter(usuario -> usuario.getNombre().toLowerCase().equals("sasha"))
				.doOnNext(usuario -> {
					if(usuario == null) {
						throw new RuntimeException("Nombre no pueden ser vacíos");
					} 
					System.out.println(usuario.getNombre().concat(" ").concat(usuario.getApellido()));
					
					})
				.map(usuario -> {String nombre = usuario.getNombre().toLowerCase();
				usuario.setNombre(nombre);
				return usuario;
				});
		
		usuarios.subscribe(e -> log.info(e.toString()), error -> log.error(error.getMessage()),
				new Runnable() {

					@Override
					public void run() {
						log.info("Ha finalizado la ejecución del observable con éxito!");
						
					}
		});
		
	}

}
