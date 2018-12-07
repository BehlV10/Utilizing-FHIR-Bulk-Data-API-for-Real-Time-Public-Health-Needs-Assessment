package edu.gatech.curator.service;

import edu.gatech.curator.client.BulkFhirApiClient;
import edu.gatech.curator.entity.SourceSystemEntity;
import edu.gatech.curator.factory.RetrofitClientFactory;
import edu.gatech.curator.model.AccessTokenResponse;
import edu.gatech.curator.model.ExportOutputResponse;
import edu.gatech.curator.model.OperationOutcomeResponse;
import edu.gatech.curator.provider.ClientAssertionProvider;
import edu.gatech.curator.provider.DateProvider;
import edu.gatech.curator.provider.OperationOutcomeTextUrlProvider;
import edu.gatech.curator.repository.SourceSystemsRepository;
import okhttp3.HttpUrl;
import okhttp3.Protocol;
import okhttp3.Request;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SourceSystemServiceTest {
    @MockBean
    PostCurationProcessorService postCurationProcessorService;

    @MockBean
    private DateProvider mockDateProvider;

    @MockBean
    private SourceSystemsRepository mockSourceSystemRepository;

    @MockBean
    private ClientAssertionProvider clientAssertionProvider;

    @MockBean
    private BulkFhirApiClient bulkFhirApiClient;

    @MockBean
    private RetrofitClientFactory retrofitClientFactory;

    @MockBean
    private OperationOutcomeTextUrlProvider operationOutcomeTextUrlProvider;

    @Autowired
    private SourceSystemService subject;

    @Mock
    private Call mockCall;

    private SourceSystemEntity sourceSystem;
    private Date mockedDemarcationDate;
    private String mockClientAssertion;
    private String accessToken;

    @Before
    public void setUp() throws Exception {
        accessToken = "expected-access-token";

        mockedDemarcationDate = mock(Date.class);
        when(mockDateProvider.oneWeekAgo()).thenReturn(mockedDemarcationDate);

        Date lastUpdated = new SimpleDateFormat("YYYY-MM-dd").parse("2000-02-02");
        sourceSystem = new SourceSystemEntity() {{
            setName("name");
            setTokenPath("auth");
            setFhirServerPath("fhir");
            setBaseUrl("http://i-need-a.token/");
            setClientId("a-token-of-some-kind");
            setKid("key-id-of-jwk");
            setJku("http://where-jwks-is.at");
            setLastUpdated(lastUpdated);
            setAccessToken(accessToken);
        }};

        mockClientAssertion = "signed-jwt-to-use-as-client-assertion";
        when(clientAssertionProvider.create(sourceSystem)).thenReturn(mockClientAssertion);

        when(retrofitClientFactory.getAPIClient(sourceSystem)).thenReturn(bulkFhirApiClient);

    }

    @Test
    public void callsRepositoryToRetrieveSourceSystemsToCurate() {
        List<SourceSystemEntity> sourceSystemsToCurate = mock(List.class);
        when(mockSourceSystemRepository.findAllByLastUpdatedBefore(mockedDemarcationDate)).thenReturn(sourceSystemsToCurate);

        assertThat(subject.retrieveSourceSystemPastDemarcationDate()).isSameAs(sourceSystemsToCurate);
    }

    @Test
    public void getAccessToken() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        when(mockCall.execute()).thenAnswer((Answer<Response<AccessTokenResponse>>) invocation ->
                Response.success(new AccessTokenResponse("bearer", 900, accessToken)));
        when(bulkFhirApiClient.createAccessToken(
                "auth",
                "system/*.read",
                "client_credentials",
                "urn:ietf:params:oauth:client-assertion-type:jwt-bearer", mockClientAssertion)).thenReturn(mockCall);

        String actual = subject.getAccessToken(sourceSystem);

        verify(clientAssertionProvider).create(sourceSystem);
        verify(retrofitClientFactory).getAPIClient(sourceSystem);

        assertThat(actual).isEqualTo("expected-access-token");
    }

    @Test
    public void startPatientExportOperation() throws IOException {
        OperationOutcomeResponse operationOutcomeResponse = mock(OperationOutcomeResponse.class);
        OperationOutcomeResponse.Text mockOperationOutcomeText = mock(OperationOutcomeResponse.Text.class);
        when(operationOutcomeResponse.getText()).thenReturn(mockOperationOutcomeText);
        when(mockOperationOutcomeText.getDiv()).thenReturn("div-string");

        HttpUrl expectedUrl = HttpUrl.parse("http://test.pass");
        when(operationOutcomeTextUrlProvider.parse("div-string")).thenReturn(expectedUrl);

        when(mockCall.execute()).thenAnswer((Answer<Response<OperationOutcomeResponse>>) invocation -> Response.success(operationOutcomeResponse));

        String authorization = "bearer " + accessToken;
        when(bulkFhirApiClient.startPatientExportOperation("fhir", authorization, "application/fhir+ndjson")).thenReturn(mockCall);

        HttpUrl actual = subject.startPatientExportOperation(sourceSystem);

        verify(retrofitClientFactory).getAPIClient(sourceSystem);
        assertThat(actual).isSameAs(expectedUrl);
    }

    @Test
    public void getExportOutputs() throws IOException {
        HttpUrl url = HttpUrl.parse("http://example.net");
        ExportOutputResponse response = mock(ExportOutputResponse.class);
        ArrayList<ExportOutputResponse.ExportOutput> expectedList = new ArrayList<>();
        when(response.getOutput()).thenReturn(expectedList);

        when(bulkFhirApiClient.getExportStatus(url, "bearer " + accessToken)).thenReturn(mockCall);

        okhttp3.Response okHttpResponse = new okhttp3.Response.Builder()
                .code(202)
                .protocol(Protocol.HTTP_1_1)
                .message("Accepted")
                .request(new Request.Builder().url(url).build())
                .build();
        Response acceptedResponse = Response.success(response, okHttpResponse);
        when(mockCall.execute())
                .thenAnswer((Answer<Response>) i -> acceptedResponse)
                .thenAnswer((Answer<Response<ExportOutputResponse>>) i -> Response.success(response));

        assertThat(subject.getExportOutputs(url, sourceSystem)).isSameAs(expectedList);
        verify(mockCall, times(2)).execute();
    }
}