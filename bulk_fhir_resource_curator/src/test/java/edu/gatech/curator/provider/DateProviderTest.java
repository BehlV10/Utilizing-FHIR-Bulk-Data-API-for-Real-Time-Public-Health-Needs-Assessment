package edu.gatech.curator.provider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DateProviderTest extends BaseProviderTest {

    @Autowired
    DateProvider subject;

    @Test
    public void providesDateOneWeekAgo() {
        long dayInMs = 1000 * 60 * 60 * 24;

        assertThat(subject.oneWeekAgo()).isInSameSecondWindowAs(new Date(System.currentTimeMillis() - (7 * dayInMs)));
    }

    @Test
    public void providesDateFifteenMinutesFromNow() {
        long fifteenMinutesInMs = 1000 * 60 * 15;

        assertThat(subject.fifteenMinutesFromNow()).isInSameSecondWindowAs(new Date(System.currentTimeMillis() + fifteenMinutesInMs));
    }
}