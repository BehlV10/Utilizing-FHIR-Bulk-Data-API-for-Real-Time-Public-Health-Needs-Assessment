CREATE TABLE source_system(
  id BIGINT AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  base_url VARCHAR(255) NOT NULL,
  token_path VARCHAR(255) NOT NULL,
  fhir_server_path VARCHAR(255) NOT NULL,
  client_id VARCHAR(2048) NOT NULL,
  kid VARCHAR(255) NOT NULL,
  jku VARCHAR(255) NOT NULL,
  access_token VARCHAR(2048),
  last_updated DATETIME NOT NULL DEFAULT '2000-01-01',
  blockgroup VARCHAR(15),

  PRIMARY KEY(id)
);
