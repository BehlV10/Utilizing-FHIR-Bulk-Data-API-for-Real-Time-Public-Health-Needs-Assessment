package edu.gatech.curator.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.gatech.curator.service.CuratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

public abstract class ModelSerializationTest {

    @MockBean
    CuratorService curatorService;

    @Autowired
    ApplicationContext context;

    protected ObjectMapper objectMapper;

    protected ModelSerializationTest() {
        objectMapper = new ObjectMapper();
    }
}
