package edu.gatech.curator.service;

import edu.gatech.curator.manager.ObesityGenderYearMetricsDataManager;
import edu.gatech.curator.manager.ObesityMetricsDataManager;
import edu.gatech.curator.provider.QueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.List;

@Service
public class PostCurationProcessorService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

	@Autowired
	private QueryProvider queryProvider;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private ObesityMetricsDataManager obesityMetricsDataManager;

    @Autowired
    private ObesityGenderYearMetricsDataManager obesityGenderYearMetricsDataManager;


    public void start() {
		try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withProcedureName("UpdatePatientBlockgroup");
            jdbcCall.execute();

			String query = queryProvider.getObesityMetricsQuery();
			List<Object[]> results = entityManager.createNativeQuery(query).getResultList();
			results.forEach((result) -> obesityMetricsDataManager.save(result));

            query = queryProvider.getObesityGenderYearMetricsQuery();
            results = entityManager.createNativeQuery(query).getResultList();
            results.forEach((result) -> obesityGenderYearMetricsDataManager.save(result));

		} catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
		}
	}
}