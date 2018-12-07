package edu.gatech.curator.repository;

import edu.gatech.curator.client.BulkFhirApiClient;
import edu.gatech.curator.factory.RetrofitClientFactory;
import edu.gatech.curator.manager.DataManagerTestConfiguration;
import edu.gatech.curator.provider.ProviderTestConfiguration;
import edu.gatech.curator.service.ServiceTestConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.mockito.Mockito.mock;

@TestConfiguration
@EnableTransactionManagement
@EnableJpaRepositories
@Import({DataManagerTestConfiguration.class, ServiceTestConfiguration.class, ProviderTestConfiguration.class})
@ComponentScan(basePackages = {"edu.gatech.curator.entity", "edu.gatech.curator.repository"})
public class RepositoryTestConfiguration {
    @Bean
    BulkFhirApiClient bulkFhirApiClient() {
        return mock(BulkFhirApiClient.class);
    }

    @Bean
    RetrofitClientFactory retrofitClientFactory() { return mock(RetrofitClientFactory.class); }
}
