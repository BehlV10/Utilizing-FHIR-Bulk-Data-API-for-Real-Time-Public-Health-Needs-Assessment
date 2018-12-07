package edu.gatech.curator.factory;

import edu.gatech.curator.client.BulkFhirApiClient;
import edu.gatech.curator.entity.SourceSystemEntity;
import edu.gatech.curator.service.CuratorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RetrofitClientFactoryTest {

    @MockBean
    CuratorService service;

    @Autowired
    ApplicationContext context;

    @Autowired
    RetrofitClientFactory subject;

    @Mock
    private SourceSystemEntity sourceSystem;

    @Before
    public void setUp() {
        when(sourceSystem.getBaseUrl()).thenReturn("http://localhost.test");
    }

    @Test
    public void getAPIClient() throws MalformedURLException {
        BulkFhirApiClient client =  subject.getAPIClient(sourceSystem);

        assertThat(client).isNotNull();
    }
}