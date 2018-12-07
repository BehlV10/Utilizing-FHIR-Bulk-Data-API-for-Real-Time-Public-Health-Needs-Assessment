select gender, 
COUNT(CASE WHEN bmi_perc < 5 THEN 1 END) AS 'Underweight',
COUNT(CASE WHEN bmi_perc >=  5 AND quantity_value < 85 THEN 1 END) AS 'Healthy',
COUNT(CASE WHEN quantity_value >=  85 AND quantity_value < 95 THEN 1 END) AS 'Overweight',
COUNT(CASE WHEN quantity_value >= 95 THEN 1 END) AS 'Obese' 
FROM new_data.child_bmi
group by gender;