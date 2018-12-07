package edu.gatech.curator.service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import static org.mockito.Mockito.mock;

@TestConfiguration
@ComponentScan(basePackages = {"edu.gatech.curator.service"})
public class ServiceTestConfiguration {
    @Bean
    CuratorService curatorService() {
        return mock(CuratorService.class);
    }

    @Bean
    SourceSystemService sourceSystemService() {
        return mock(SourceSystemService.class);
    }

    @Bean
    FhirResourceProcessorService fhirResourceProcessorService() {
        return mock(FhirResourceProcessorService.class);
    }
}

