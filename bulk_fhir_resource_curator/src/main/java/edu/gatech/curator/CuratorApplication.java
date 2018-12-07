package edu.gatech.curator;

import edu.gatech.curator.service.CuratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CuratorApplication {

	private static Logger LOG = LoggerFactory
			.getLogger(CuratorApplication.class);

	public static void main(String[] args) {
        LOG.info("STARTING THE CURATION PROCESS");
		SpringApplication.run(CuratorApplication.class, args);
        LOG.info("CURATION PROCESS FINISHED");
	}

	@Bean
	public CommandLineRunner runner(CuratorService curatorService) {
		return (args -> {
			curatorService.start();
		});
	}
}
