package edu.gatech.curator.factory;

import edu.gatech.curator.client.BulkFhirApiClient;
import edu.gatech.curator.entity.SourceSystemEntity;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

@Component
public class RetrofitClientFactory {

    public BulkFhirApiClient getAPIClient(SourceSystemEntity sourceSystem) throws MalformedURLException {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(90, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(sourceSystem.getBaseUrl())
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(NdJsonConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        BulkFhirApiClient client = retrofit.create(BulkFhirApiClient.class);

        return client;
    }
}
