package edu.gatech.curator.manager;

import edu.gatech.curator.service.ServiceTestConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(ServiceTestConfiguration.class)
@ComponentScan(basePackages = {"edu.gatech.curator.manager"})
public class DataManagerTestConfiguration { }
