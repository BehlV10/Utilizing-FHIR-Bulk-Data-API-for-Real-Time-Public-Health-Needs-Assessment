package edu.gatech.curator.repository;

import edu.gatech.curator.entity.ObesityGenderYearMetricEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObesityGenderYearMetricsRepository extends CrudRepository<ObesityGenderYearMetricEntity, String> {
}
