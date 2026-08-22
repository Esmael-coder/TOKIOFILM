package com.tokio.filme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FilmeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilmeApplication.class, args);
	}

}
