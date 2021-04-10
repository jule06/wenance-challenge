package com.challenge.wenance;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WenanceChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(WenanceChallengeApplication.class, args);
	}

	@Bean
	@Primary
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
				.setFieldMatchingEnabled(true)
				.setAmbiguityIgnored(true)
				.setFieldAccessLevel(Configuration.AccessLevel.PUBLIC)
				.setMatchingStrategy(MatchingStrategies.LOOSE);

		//TODO: agregar el typeMap de licencias.
		return modelMapper;
	}

}
