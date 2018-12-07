library(RMySQL)
library(childsds)


mydb = dbConnect(MySQL(), user='root', password='MyNewPassword', 
                 dbname='new_data', host='10.240.162.123')


rs = dbSendQuery(mydb, "SELECT patient_id, 
    gender,
    (DATEDIFF(observation.issued, patient.birth_date) / 365.25) AS age,
    observation.quantity_value
FROM
    observation,
    patient
WHERE
    observation.patient_id = patient.id
        AND observation.code_text = 'Body Mass Index'
        AND observation.issued = (SELECT 
            issued
        FROM
            observation t2
        WHERE
            t2.patient_id = observation.patient_id
        ORDER BY t2.issued DESC
        LIMIT 1)
HAVING age < 15"
                 )

data = fetch(rs, n=-1)

data$bmi_perc <- sds(data$quantity_value,
                       age = data$age,
                       sex = data$gender, male = "Male", female = "Female",
                       ref = cdc.ref,
                       item = "bmi",
                       type = "perc")

