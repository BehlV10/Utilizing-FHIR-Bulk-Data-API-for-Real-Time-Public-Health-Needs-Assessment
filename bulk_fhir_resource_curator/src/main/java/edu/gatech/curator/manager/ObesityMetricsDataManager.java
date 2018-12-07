package edu.gatech.curator.manager;

import edu.gatech.curator.entity.ObesityGenderYearMetricEntity;
import edu.gatech.curator.entity.ObesityMetricEntity;
import edu.gatech.curator.provider.DateProvider;
import edu.gatech.curator.repository.ObesityMetricsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class ObesityMetricsDataManager {
    @Autowired
    ObesityMetricsRepository obesityMetricsRepository;

    @Autowired
    private DateProvider dateProvider;

    public void save(Object[] result) {
        try {
            ObesityMetricEntity e = new ObesityMetricEntity();
            e.setGender(result[0].toString());
            e.setUnderweight(((BigInteger) result[1]).intValue());
            e.setHealthy(((BigInteger) result[2]).intValue());
            e.setOverweight(((BigInteger) result[3]).intValue());
            e.setObese(((BigInteger) result[4]).intValue());
            e.setLastUpdated(dateProvider.now());
            obesityMetricsRepository.save(e);
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
    }
}
