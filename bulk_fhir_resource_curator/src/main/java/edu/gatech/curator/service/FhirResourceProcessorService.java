package edu.gatech.curator.service;

import edu.gatech.curator.client.BulkFhirApiClient;
import edu.gatech.curator.entity.SourceSystemEntity;
import edu.gatech.curator.factory.RetrofitClientFactory;
import edu.gatech.curator.manager.AllergyIntoleranceDataManager;
import edu.gatech.curator.manager.CarePlanDataManager;
import edu.gatech.curator.manager.ObservationDataManager;
import edu.gatech.curator.manager.PatientDataManager;
import edu.gatech.curator.model.ExportOutputResponse;
import edu.gatech.curator.model.NdJson;
import okhttp3.HttpUrl;
import org.hl7.fhir.dstu3.model.AllergyIntolerance;
import org.hl7.fhir.dstu3.model.CarePlan;
import org.hl7.fhir.dstu3.model.Observation;
import org.hl7.fhir.dstu3.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Service
public class FhirResourceProcessorService {

    @Autowired
    private RetrofitClientFactory clientFactory;

    @Autowired
    private AllergyIntoleranceDataManager allergyIntoleranceDataManager;

    @Autowired
    private PatientDataManager patientDataManager;

    @Autowired
    private ObservationDataManager observationDataManager;

    @Autowired
    private CarePlanDataManager carePlanDataManager;

    public void process(List<ExportOutputResponse.ExportOutput> exportOutputs, SourceSystemEntity sourceSystem) throws IOException {
        BulkFhirApiClient apiClient = clientFactory.getAPIClient(sourceSystem);

        for (ExportOutputResponse.ExportOutput exportOutput :
                exportOutputs) {
            HttpUrl url = HttpUrl.parse(exportOutput.getUrl());
            String authorization = "bearer " + sourceSystem.getAccessToken();
            try {
                switch (FHIR_RESOURCE_MAP.get(exportOutput.getType())) {
                    case CLAIM:
                    case CONDITION:
                    case DIAGNOSTIC_REPORT:
                    case ENCOUNTER:
                    case GOAL:
                    case IMAGING_STUDY:
                    case IMMUNIZATION:
                    case MEDICATION_REQUEST:
                    case BASIC:
                    case ORGANIZATION:
                    case PROCEDURE:
                        break;
                    case ALLERGY_INTOLERANCE:
                        Call<NdJson<AllergyIntolerance>> allergyIntoleranceCall = apiClient.getAllergyIntoleranceResource(url, authorization);
                        Response<NdJson<AllergyIntolerance>> allergyIntoleranceResponse = allergyIntoleranceCall.execute();
                        allergyIntoleranceDataManager.save(sourceSystem, allergyIntoleranceResponse.body().getResources());
                        break;
                    case CARE_PLAN:
                        Call<NdJson<CarePlan>> carePlanCall = apiClient.getCarePlanResources(url, authorization);
                        Response<NdJson<CarePlan>> carePlanResponse = carePlanCall.execute();
                        carePlanDataManager.save(sourceSystem, carePlanResponse.body().getResources());
                        break;
                    case OBSERVATION:
                        Call<NdJson<Observation>> observationCall= apiClient.getObseravationResources(url, authorization);
                        Response<NdJson<Observation>> observationResponse = observationCall.execute();
                        observationDataManager.save(sourceSystem, observationResponse.body().getResources());
                        break;
                    case PATIENT:
                        Call<NdJson<Patient>> patientCall = apiClient.getPatientResources(url, authorization);
                        Response<NdJson<Patient>> patientResponse = patientCall.execute();
                        patientDataManager.save(sourceSystem, patientResponse.body().getResources());
                        break;
                }
            } catch (Exception e) {
                System.err.println(e.getLocalizedMessage());
                return;
            }

        }
    }

    private enum FhirResourceType {
        ALLERGY_INTOLERANCE,
        CARE_PLAN,
        CLAIM,
        CONDITION,
        DIAGNOSTIC_REPORT,
        ENCOUNTER,
        GOAL,
        IMAGING_STUDY,
        IMMUNIZATION,
        MEDICATION_REQUEST,
        OBSERVATION,
        ORGANIZATION,
        PATIENT,
        PROCEDURE,
        BASIC,
        EXPLANATION_OF_BENEFIT,
        PRACTITIONER
    }

    private static HashMap<String, FhirResourceType> FHIR_RESOURCE_MAP = new HashMap<String, FhirResourceType>(){{
       put("AllergyIntolerance", FhirResourceType.ALLERGY_INTOLERANCE);
       put("CarePlan", FhirResourceType.CARE_PLAN);
       put("Claim", FhirResourceType.CLAIM);
       put("Condition", FhirResourceType.CONDITION);
       put("DiagnosticReport", FhirResourceType.DIAGNOSTIC_REPORT);
       put("Encounter", FhirResourceType.ENCOUNTER);
       put("Goal", FhirResourceType.GOAL);
       put("ImagingStudy", FhirResourceType.IMAGING_STUDY);
       put("Immunization", FhirResourceType.IMMUNIZATION);
       put("MedicationRequest", FhirResourceType.MEDICATION_REQUEST);
       put("Observation", FhirResourceType.OBSERVATION);
       put("Organization", FhirResourceType.ORGANIZATION);
       put("Patient", FhirResourceType.PATIENT);
       put("Procedure", FhirResourceType.PROCEDURE);
       put("Basic", FhirResourceType.BASIC);
       put("ExplanationOfBenefit", FhirResourceType.EXPLANATION_OF_BENEFIT);
       put("Practitioner", FhirResourceType.PRACTITIONER);
    }};
}
