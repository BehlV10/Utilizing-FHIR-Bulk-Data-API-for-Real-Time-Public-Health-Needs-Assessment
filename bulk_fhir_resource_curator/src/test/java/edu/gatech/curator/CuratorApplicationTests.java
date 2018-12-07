package edu.gatech.curator;

import edu.gatech.curator.service.CuratorService;
import edu.gatech.curator.service.ServiceTestConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ServiceTestConfiguration.class)
public class CuratorApplicationTests {

    @MockBean
    CuratorService curatorService;

    @Autowired
    ApplicationContext context;

    @Test
	public void contextLoads_startsCuratorService() {
        verify(curatorService).start();
	}
}
