package edu.gatech.curator.manager;

import edu.gatech.curator.entity.PatientEntity;
import edu.gatech.curator.entity.SourceSystemEntity;
import edu.gatech.curator.repository.PatientRepository;
import org.hl7.fhir.dstu3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PatientDataManager {

    @Autowired
    private PatientRepository patientRepository;

    public void save(SourceSystemEntity sourceSystem, List<Patient> patients) {
        try {
            Long srcId = sourceSystem.getId();
            String srcName = sourceSystem.getName();
            patients.forEach(p -> {
                Address address = p.getAddressFirstRep();
                DecimalType latType = (DecimalType) address
                        .getExtensionsByUrl("http://hl7.org/fhir/StructureDefinition/geolocation")
                        .get(0)
                        .getExtensionsByUrl("latitude")
                        .get(0).getValue();
                DecimalType longType = (DecimalType) address
                        .getExtensionsByUrl("http://hl7.org/fhir/StructureDefinition/geolocation")
                        .get(0)
                        .getExtensionsByUrl("longitude")
                        .get(0).getValue();


                PatientEntity patient = new PatientEntity();
                patient.setId(p.getId());
                patient.setSourceSystemId(srcId);
                patient.setSourceSystemName(srcName);
                patient.setBirthDate(p.getBirthDate());
                patient.setGender(p.getGender().getDisplay());
                patient.setCity(address.getCity());
                patient.setLatitude(latType.getValueAsNumber().doubleValue());
                patient.setLongitude(longType.getValueAsNumber().doubleValue());
                patientRepository.save(patient);
            });
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
    }
}
