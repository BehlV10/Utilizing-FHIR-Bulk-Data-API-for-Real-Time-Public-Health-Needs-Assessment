package edu.gatech.curator.manager;

import edu.gatech.curator.entity.SourceSystemEntity;
import org.hl7.fhir.dstu3.model.AllergyIntolerance;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllergyIntoleranceDataManager {
    public void save(SourceSystemEntity sourceSystem, List<AllergyIntolerance> allergyIntolerances) {


    }
}
