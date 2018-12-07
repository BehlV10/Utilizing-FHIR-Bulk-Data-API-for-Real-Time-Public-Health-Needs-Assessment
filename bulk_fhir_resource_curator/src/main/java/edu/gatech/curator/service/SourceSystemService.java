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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@Service
public class SourceSystemService {

    @Autowired
    private SourceSystemsRepository sourceSystemsRepository;

    @Autowired
    private DateProvider dateProvider;

    @Autowired
    ClientAssertionProvider clientAssertionProvider;

    @Autowired
    private RetrofitClientFactory retrofitClientFactory;

    @Autowired
    private OperationOutcomeTextUrlProvider operationOutcomeTextUrlProvider;

    public List<SourceSystemEntity> retrieveSourceSystemPastDemarcationDate() {
        return sourceSystemsRepository.findAllByLastUpdatedBefore(dateProvider.oneWeekAgo());
    }

    public String getAccessToken(SourceSystemEntity sourceSystem) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        String clientAssertion = clientAssertionProvider.create(sourceSystem);
        BulkFhirApiClient apiClient = retrofitClientFactory.getAPIClient(sourceSystem);

        Call<AccessTokenResponse> call = apiClient.createAccessToken(
                sourceSystem.getTokenPath(),
                "system/*.read",
                "client_credentials",
                "urn:ietf:params:oauth:client-assertion-type:jwt-bearer", clientAssertion);

        Response<AccessTokenResponse> response = call.execute();
        AccessTokenResponse body = response.body();
        return body.getAccessToken();
    }

    public HttpUrl startPatientExportOperation(SourceSystemEntity sourceSystem) throws IOException {
        BulkFhirApiClient apiClient = retrofitClientFactory.getAPIClient(sourceSystem);
        String authorization = "bearer " + sourceSystem.getAccessToken();
        Call<OperationOutcomeResponse> call = apiClient.startPatientExportOperation(
                sourceSystem.getFhirServerPath(),
                authorization, "application/fhir+ndjson");
        Response<OperationOutcomeResponse> response = call.execute();
        OperationOutcomeResponse outcome = response.body();
        return operationOutcomeTextUrlProvider.parse(outcome.getText().getDiv());
    }

    public List<ExportOutputResponse.ExportOutput> getExportOutputs(HttpUrl url, SourceSystemEntity sourceSystem) throws IOException {
        int responseCode = 202;

        BulkFhirApiClient apiClient = retrofitClientFactory.getAPIClient(sourceSystem);
        String authorization = "bearer " + sourceSystem.getAccessToken();

        while (responseCode == 202) {
            Call<ExportOutputResponse> call = apiClient.getExportStatus(
                    url,
                    authorization);
            Response<ExportOutputResponse> response = call.execute();
            responseCode = response.code();

             if (responseCode == 200) {
                 return response.body().getOutput();
            }

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        throw new IOException("Received response status from server that was neither ok or accepted.");
    }
}

