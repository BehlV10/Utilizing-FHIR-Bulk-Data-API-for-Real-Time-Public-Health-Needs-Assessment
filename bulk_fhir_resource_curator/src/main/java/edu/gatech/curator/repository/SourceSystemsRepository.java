package edu.gatech.curator.repository;

import edu.gatech.curator.entity.SourceSystemEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SourceSystemsRepository extends CrudRepository<SourceSystemEntity, Long> {
    List<SourceSystemEntity> findAllByLastUpdatedBefore(Date demarcationDate);
}
