package edu.gatech.curator.provider;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import static org.mockito.Mockito.mock;

@TestConfiguration
@ComponentScan(basePackages = "edu.gatech.curator.provider")
public class ProviderTestConfiguration {
    @Bean
    DateProvider dateProvider() {
        return mock(DateProvider.class);
    }

    @Bean
    KeyProvider keyProvider() {
        return mock(KeyProvider.class);
    }

    @Bean
    ClientAssertionProvider clientAssertionProvider() {
        return mock(ClientAssertionProvider.class);
    }

    @Bean
    OperationOutcomeTextUrlProvider operationOutcomeTextUrlProvider() {
        return mock(OperationOutcomeTextUrlProvider.class);
    }
}
