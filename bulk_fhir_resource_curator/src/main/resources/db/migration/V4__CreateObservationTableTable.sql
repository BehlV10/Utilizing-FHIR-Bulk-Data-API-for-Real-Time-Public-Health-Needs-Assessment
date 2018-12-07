CREATE TABLE observation(
	  id VARCHAR(255),
    patient_id VARCHAR(255) NOT NULL,
    issued DATE NOT NULL,
    code_text VARCHAR(255) NOT NULL,
    quantity_value DOUBLE NOT NULL,
    quantity_unit VARCHAR(32) NOT NULL,
    source_system_id BIGINT NOT NULL,
    source_system_name VARCHAR(255) NOT NULL,

    PRIMARY KEY (id, source_system_id),
    FOREIGN KEY (source_system_id) REFERENCES source_system(id)
);