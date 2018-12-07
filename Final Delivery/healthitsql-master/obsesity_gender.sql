select gender, 
COUNT(CASE WHEN quantity_value < 18 THEN 1 END) AS 'Underweight',
COUNT(CASE WHEN quantity_value >=  18 AND quantity_value < 24.9 THEN 1 END) AS 'Healthy',
COUNT(CASE WHEN quantity_value >=  25 AND quantity_value < 29.9 THEN 1 END) AS 'Overweight',
COUNT(CASE WHEN quantity_value >= 30 THEN 1 END) AS 'Obese'
from observation, patient
where observation.patient_id  = patient.id
and observation.code_text = 'Body Mass Index'
and year(observation.issued)  = 2018 
and observation.issued = (select issued
							from observation t2 where t2.patient_id = observation.patient_id
                            ORDER BY t2.issued DESC
								LIMIT 1
                            )
group by gender
						

