package edu.gatech.curator.provider;

import edu.gatech.curator.service.CuratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

public abstract class BaseProviderTest {
    @MockBean
    CuratorService curatorService;

    @Autowired
    ApplicationContext context;
}
