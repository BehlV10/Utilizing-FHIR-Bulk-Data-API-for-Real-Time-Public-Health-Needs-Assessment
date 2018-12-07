package edu.gatech.curator.provider;

import edu.gatech.curator.service.CuratorService;
import edu.gatech.curator.service.ServiceTestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

@Import(ServiceTestConfiguration.class)
public abstract class BaseProviderTest {
    @MockBean
    CuratorService curatorService;

    @Autowired
    ApplicationContext context;
}
