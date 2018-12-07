package edu.gatech.curator.manager;

import edu.gatech.curator.entity.ObesityMetricEntity;
import edu.gatech.curator.entity.ObservationEntity;
import edu.gatech.curator.entity.SourceSystemEntity;
import edu.gatech.curator.repository.ObesityMetricsRepository;
import edu.gatech.curator.repository.ObservationRepository;
import org.hl7.fhir.dstu3.model.Observation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ObesityMetricsDataManager {
	@Autowired
	ObesityMetricsRepository obesityMetricsRepository;

	public void save(String gender, int year, String age, int underweight, int healthy, int overweight, int obese) {
		try {
			ObesityMetricEntity e = new ObesityMetricEntity();
			e.setGender(gender);
			e.setYear(year);
			e.setAge(age);
			e.setUnderweight(underweight);
			e.setHealthy(healthy);
			e.setHealthy(overweight);
			e.setHealthy(obese);
			obesityMetricsRepository.save(e);

		} catch (Exception e) {
			// should log exception
		}
	}
}
