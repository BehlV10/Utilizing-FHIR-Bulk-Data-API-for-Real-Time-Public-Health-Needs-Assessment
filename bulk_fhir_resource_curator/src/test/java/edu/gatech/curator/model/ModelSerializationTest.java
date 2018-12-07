package edu.gatech.curator.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.gatech.curator.service.ServiceTestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

@Import(ServiceTestConfiguration.class)
public abstract class ModelSerializationTest {

    @Autowired
    ApplicationContext context;

    protected ObjectMapper objectMapper;

    protected ModelSerializationTest() {
        objectMapper = new ObjectMapper();
    }
}
