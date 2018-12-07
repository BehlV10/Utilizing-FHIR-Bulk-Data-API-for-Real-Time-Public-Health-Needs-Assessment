package edu.gatech.curator.model;

import edu.gatech.curator.testhelper.JsonFromResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccessTokenResponseTest extends ModelSerializationTest {

    @Value("classpath:test_fixtures/AccessTokenResponse.json")
    private Resource accessTokenJson;

    @Test
    public void accessTokenSerialization() throws IOException {
        AccessTokenResponse serialized = objectMapper.readValue(JsonFromResource.getBytes(accessTokenJson), AccessTokenResponse.class);

        assertThat(serialized.getAccessToken()).isEqualTo("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYmVhcmVyIiwiZXhwaXJlc19pbiI6OTAwLCJpYXQiOjE1NDE5NzEyNTYsImV4cCI6MTU0MTk3MjE1Nn0.ybqbhoSUgAM8hevBbVE92k_gftWPVINKtt0B6T38_oE");
        assertThat(serialized.getExpiresIn()).isEqualTo(900);
        assertThat(serialized.getTokenType()).isEqualTo("bearer");
    }
}