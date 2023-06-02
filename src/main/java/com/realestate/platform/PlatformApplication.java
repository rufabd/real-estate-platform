package com.realestate.platform;

import com.realestate.platform.dbConfig.DatabaseInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PlatformApplication {

	public static void main(String[] args) {
		DatabaseInitializer.initialize("real_estate");
		SpringApplication.run(PlatformApplication.class, args);
	}

}
