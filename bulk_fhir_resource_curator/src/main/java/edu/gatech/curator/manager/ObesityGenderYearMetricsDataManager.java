package edu.gatech.curator.manager;

import edu.gatech.curator.entity.ObesityGenderYearMetricEntity;
import edu.gatech.curator.entity.ObesityMetricEntity;
import edu.gatech.curator.provider.DateProvider;
import edu.gatech.curator.repository.ObesityGenderYearMetricsRepository;
import edu.gatech.curator.repository.ObesityMetricsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class ObesityGenderYearMetricsDataManager {
    @Autowired
    ObesityGenderYearMetricsRepository obesityGenderYearMetricsRepository;

    @Autowired
    private DateProvider dateProvider;

    public void save(Object[] result) {
        try {
            ObesityGenderYearMetricEntity e = new ObesityGenderYearMetricEntity();
            e.setGender(result[0].toString());
            e.setYear((int) result[1]);
            e.setAge(result[2].toString());
            e.setUnderweight(((BigInteger) result[3]).intValue());
            e.setHealthy(((BigInteger) result[4]).intValue());
            e.setOverweight(((BigInteger) result[5]).intValue());
            e.setObese(((BigInteger) result[6]).intValue());
            e.setLastUpdated(dateProvider.now());
            obesityGenderYearMetricsRepository.save(e);
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
    }
}
