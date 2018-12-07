package edu.gatech.curator.repository;

import edu.gatech.curator.entity.PatientEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends CrudRepository<PatientEntity, PatientEntity.PatientPrimaryKey> {
}
