package edu.gatech.curator.repository;

import edu.gatech.curator.entity.SourceSystemEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = {RepositoryTestConfiguration.class})
public class SourceSystemsRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SourceSystemsRepository subject;

    private SourceSystemEntity entity1;
    private SourceSystemEntity entity2;
    private SourceSystemEntity entity3;
    private Date demarcationDate;
    private Date date;

    @Before
    public void setUp() throws ParseException {
        demarcationDate = new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-01");
        date = new SimpleDateFormat("yyyy-MM-dd").parse("1999-12-31");

        entity1 = new SourceSystemEntity();
        entity1.setName("name-1");
        entity1.setBaseUrl("base-url-1");
        entity1.setFhirServerPath("fhir");
        entity1.setTokenPath("token");
        entity1.setClientId("clientId");
        entity1.setLastUpdated(date);
        entity1.setKid("kid");
        entity1.setJku("jku");
        entity1.setAccessToken("accessToken");

        entity2 = new SourceSystemEntity();
        entity2.setName("name-2");
        entity2.setBaseUrl("base-url-2");
        entity2.setFhirServerPath("fhir");
        entity2.setTokenPath("token");
        entity2.setClientId("clientId");
        entity2.setLastUpdated(date);
        entity2.setKid("kid-2");
        entity2.setJku("jku-2");
        entity2.setAccessToken("accessToken-2");

        entity3 = new SourceSystemEntity();
        entity3.setName("name-3");
        entity3.setBaseUrl("base-url-3");
        entity3.setFhirServerPath("fhir");
        entity3.setTokenPath("token");
        entity3.setClientId("clientId");
        entity3.setLastUpdated(demarcationDate);
        entity3.setKid("kid");
        entity3.setJku("jku");
        entity3.setAccessToken("accessToken-3");
    }

    @Test
    public void shouldReturnSourceSystemsOlderThanGivenDate() {
        entityManager.persist(entity1);
        entityManager.persist(entity2);
        entityManager.persist(entity3);

        List<SourceSystemEntity> actual = subject.findAllByLastUpdatedBefore(demarcationDate);

        assertThat(actual).hasSize(2);
        assertThat(actual).containsExactlyInAnyOrder(entity1, entity2);
    }
}

