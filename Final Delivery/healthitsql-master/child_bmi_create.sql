CREATE TABLE `child_bmi` (
  `patient_id` VARCHAR(255) NOT NULL,
  `gender` VARCHAR(16) NULL,
  `quantity_value` DOUBLE NULL,
  `bmi_perc` DOUBLE NULL,
  PRIMARY KEY (`patient_id`));
