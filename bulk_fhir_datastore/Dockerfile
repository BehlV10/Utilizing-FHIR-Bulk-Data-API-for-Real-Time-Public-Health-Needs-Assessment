FROM mysql/mysql-server:5.7

ENV MYSQL_RANDOM_ROOT_PASSWORD yes
ENV MYSQL_DATABASE bulk_fhir_datastore
ENV MYSQL_USER doctor*who
ENV MYSQL_PASSWORD cyber*men

COPY ./camerongis.sql /tmp/camerongis.sql
COPY ./camerongis-import.sh /docker-entrypoint-initdb.d/camerongis-import.sh