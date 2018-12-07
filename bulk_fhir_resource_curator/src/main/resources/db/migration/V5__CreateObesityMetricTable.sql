CREATE TABLE obesity_metrics(
	  id VARCHAR(255),
    gender VARCHAR(255) NOT NULL,
    year INT NOT NULL,
    underweight INT NOT NULL,
    healthy INT NOT NULL,
    overweight INT NOT NULL,
    obese INT NOT NULL,
    last_updated DATETIME NOT NULL DEFAULT '2000-01-01',

    PRIMARY KEY (id)
);
