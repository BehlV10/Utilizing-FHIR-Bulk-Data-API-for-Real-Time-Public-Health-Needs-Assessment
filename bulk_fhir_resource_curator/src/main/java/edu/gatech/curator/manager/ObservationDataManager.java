package edu.gatech.curator.manager;

import edu.gatech.curator.entity.ObservationEntity;
import edu.gatech.curator.entity.SourceSystemEntity;
import edu.gatech.curator.repository.ObservationRepository;
import org.hl7.fhir.dstu3.model.Observation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ObservationDataManager {
    @Autowired
    ObservationRepository observationRepository;

    public void save(SourceSystemEntity sourceSystem, List<Observation> observations) {
        observations.forEach(o -> {
            try {
                ObservationEntity e = new ObservationEntity();
                e.setId(o.getId());
                e.setPatientId(o.getSubject().getReference());
                e.setIssued(o.getIssued());
                e.setCodeText(o.getCode().getText());
                e.setQuantityValue(o.getValueQuantity().getValue().doubleValue());
                e.setQuantityUnit(o.getValueQuantity().getUnit());
                e.setSourceSystemId(sourceSystem.getId());
                e.setSourceSystemName(sourceSystem.getName());
                observationRepository.save(e);
            } catch (Exception e) {
                // should log exception
            }
        });

    }
}
