package com.ckay.muddle.Muddle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MuddleApplication {

	public static void main(String[] args) {
		SpringApplication.run(MuddleApplication.class, args);
	}

}
