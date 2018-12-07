select gender, year(observation.issued) as year_issued, 
COUNT(CASE WHEN quantity_value < 18 THEN 1 END) AS 'Underweight',
COUNT(CASE WHEN quantity_value >=  18 AND quantity_value < 24.9 THEN 1 END) AS 'Healthy',
COUNT(CASE WHEN quantity_value >=  25 AND quantity_value < 29.9 THEN 1 END) AS 'Overweight',
COUNT(CASE WHEN quantity_value >= 30 THEN 1 END) AS 'Obese'
from observation, patient
where observation.patient_id  = patient.id
and observation.code_text = 'Body Mass Index'
 
and observation.issued = (select issued
							from observation t2 where t2.patient_id = observation.patient_id
							and year(observation.issued)   =  year(t2.issued)  
                            ORDER BY t2.issued DESC
								LIMIT 1
                            )
group by gender,year_issued
						
