package edu.gatech.curator.repository;

import edu.gatech.curator.entity.ObservationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObservationRepository extends CrudRepository<ObservationEntity, ObservationEntity.ObservationPrimaryKey> {
}
