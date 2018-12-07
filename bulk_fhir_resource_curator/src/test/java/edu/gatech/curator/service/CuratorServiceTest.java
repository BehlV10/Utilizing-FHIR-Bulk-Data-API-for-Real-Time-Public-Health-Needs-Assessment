package edu.gatech.curator.service;

import edu.gatech.curator.entity.SourceSystemEntity;
import edu.gatech.curator.model.ExportOutputResponse;
import edu.gatech.curator.repository.SourceSystemsRepository;
import okhttp3.HttpUrl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CuratorServiceTest {

    @MockBean
    private SourceSystemService sourceSystemService;

    @MockBean
    private SourceSystemsRepository sourceSystemRepository;

    @MockBean
    private FhirResourceProcessorService resourceProcessor;

    @MockBean
    PostCurationProcessorService postCurationProcessorService;

    @Autowired
    private CuratorService subject;

    private SourceSystemEntity expiredSourceSystem1;
    private SourceSystemEntity expiredSourceSystem2;
    private String accessToken1;
    private String accessToken2;
    private HttpUrl sourceSystemExportStatusUrl1;
    private HttpUrl sourceSystemExportStatusUrl2;
    private List<ExportOutputResponse.ExportOutput> exportedResources1;
    private List<ExportOutputResponse.ExportOutput> exportedResources2;

    @Before
    public void setUp() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {

        expiredSourceSystem1 = mock(SourceSystemEntity.class);
        expiredSourceSystem2 = mock(SourceSystemEntity.class);

        List<SourceSystemEntity> sourceSystems = new ArrayList<SourceSystemEntity>() {{
            add(expiredSourceSystem1);
            add(expiredSourceSystem2);
        }};
        when(sourceSystemService.retrieveSourceSystemPastDemarcationDate()).thenReturn(sourceSystems);

        accessToken1 = "access_token_1_in_jwt_format";
        accessToken2 = "access_token_2_in_jwt_format";
        when(sourceSystemService.getAccessToken(expiredSourceSystem1)).thenReturn(accessToken1);
        when(sourceSystemService.getAccessToken(expiredSourceSystem2)).thenReturn(accessToken2);

        sourceSystemExportStatusUrl1 = HttpUrl.parse("http://test1.local");
        sourceSystemExportStatusUrl2 = HttpUrl.parse("http://test2.local");
        when(sourceSystemService.startPatientExportOperation(expiredSourceSystem1)).thenReturn(sourceSystemExportStatusUrl1);
        when(sourceSystemService.startPatientExportOperation(expiredSourceSystem2)).thenReturn(sourceSystemExportStatusUrl2);

        exportedResources1 = mock(List.class);
        exportedResources2 = mock(List.class);
        when(sourceSystemService.getExportOutputs(sourceSystemExportStatusUrl1, expiredSourceSystem1)).thenReturn(exportedResources1);
        when(sourceSystemService.getExportOutputs(sourceSystemExportStatusUrl2, expiredSourceSystem2)).thenReturn(exportedResources2);
    }

    @Test
    public void start_retrievesSourceSystemsToCurate() {
        subject.start();

        verify(sourceSystemService).retrieveSourceSystemPastDemarcationDate();
    }

    @Test
    public void start_retrieveAccessTokensFromExpiredSourceSystems() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        subject.start();

        verify(sourceSystemService).getAccessToken(expiredSourceSystem1);
        verify(sourceSystemService).getAccessToken(expiredSourceSystem2);
    }

    @Test
    public void start_savesAccessTokensForEachTokenFetchedForSourceSystem() {
        subject.start();

        verify(expiredSourceSystem1).setAccessToken(accessToken1);
        verify(expiredSourceSystem2).setAccessToken(accessToken2);

        verify(sourceSystemRepository).save(expiredSourceSystem1);
        verify(sourceSystemRepository).save(expiredSourceSystem2);
    }

    @Test
    public void start_shouldInvokeSourceSystemClient_startPatientExportOperation() throws IOException {
        subject.start();

        verify(sourceSystemService).startPatientExportOperation(expiredSourceSystem1);
        verify(sourceSystemService).startPatientExportOperation(expiredSourceSystem1);
    }

    @Test
    public void start_shouldPollStatusOfPatientExportOperation_returnsOperationOutputs() throws IOException {
        subject.start();

        verify(sourceSystemService).getExportOutputs(sourceSystemExportStatusUrl1, expiredSourceSystem1);
        verify(sourceSystemService).getExportOutputs(sourceSystemExportStatusUrl2, expiredSourceSystem2);
    }

    @Test
    public void start_processOutputResourceTypesWhenExportOperationIsComplete() throws IOException {
        subject.start();

        verify(resourceProcessor).process(exportedResources1, expiredSourceSystem1);
        verify(resourceProcessor).process(exportedResources2, expiredSourceSystem2);
    }

}