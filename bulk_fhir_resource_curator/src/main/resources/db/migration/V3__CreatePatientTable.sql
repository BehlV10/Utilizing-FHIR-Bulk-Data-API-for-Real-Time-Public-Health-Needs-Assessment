CREATE TABLE patient(
	  id VARCHAR(255),
	  gender VARCHAR(16) NOT NULL DEFAULT 'Unknown',
    birth_date DATE NOT NULL DEFAULT '2000-01-01',
    city VARCHAR(255) NOT NULL,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL,
    source_system_id BIGINT NOT NULL,
    source_system_name VARCHAR(255) NOT NULL,

    PRIMARY KEY (id, source_system_id),
    FOREIGN KEY (source_system_id) REFERENCES source_system(id)
);