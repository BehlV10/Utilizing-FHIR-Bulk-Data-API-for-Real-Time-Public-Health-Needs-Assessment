package edu.gatech.curator.model;

import edu.gatech.curator.model.ExportOutputResponse;
import edu.gatech.curator.model.ModelSerializationTest;
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
public class ExportOutputTest extends ModelSerializationTest {

    @Value("classpath:test_fixtures/ExportOutput.json")
    private Resource exportOutputJson;

    @Test
    public void objectSerialization() throws IOException {
        ExportOutputResponse.ExportOutput serialized = objectMapper.readValue(JsonFromResource.getBytes(exportOutputJson), ExportOutputResponse.ExportOutput.class);

        assertThat(serialized.getType()).isEqualTo("AllergyIntolerance");
        assertThat(serialized.getCount()).isEqualTo(64);
        assertThat(serialized.getUrl()).isEqualTo("https://enigmatic-waters-34317.herokuapp.com/eyJpZCI6ImQxZjM0ZGU5NzRhNWJmYWE4MWFmODIwZDMxYmU4MDI4NDZjMzM0YTM3MmRiZDU1Y2EyZDEyZDRlNjgzNTBmN2YiLCJyZXF1ZXN0U3RhcnQiOjE1NDE4MTYwMTIwMDksInNlY3VyZSI6dHJ1ZSwib2Zmc2V0IjowLCJsaW1pdCI6MTAwMDB9/fhir/bulkfiles/1.AllergyIntolerance.ndjson");
    }
}

