package edu.gatech.curator.service;

import edu.gatech.curator.entity.SourceSystemEntity;
import edu.gatech.curator.model.ExportOutputResponse;
import edu.gatech.curator.repository.SourceSystemsRepository;
import okhttp3.HttpUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.List;

@Service
public class CuratorService {

    @Autowired
    private SourceSystemService sourceSystemService;

    @Autowired
    private SourceSystemsRepository sourceSystemsRepository;

    @Autowired
    private FhirResourceProcessorService resourceProcessor;

    public void start() {
        List<SourceSystemEntity> sourceSystems = sourceSystemService.retrieveSourceSystemPastDemarcationDate();

        sourceSystems.forEach(ss -> {
            try {
                ss.setAccessToken(sourceSystemService.getAccessToken(ss));
            } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
                e.printStackTrace();
                return;
            }

            try {
                HttpUrl exportStatusUrl = sourceSystemService.startPatientExportOperation(ss);
                List<ExportOutputResponse.ExportOutput> exportOutputs = sourceSystemService.getExportOutputs(exportStatusUrl, ss);
                resourceProcessor.process(exportOutputs, ss);
                ss.setLastUpdated(new Date());
                sourceSystemsRepository.save(ss);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        });
    }
}
