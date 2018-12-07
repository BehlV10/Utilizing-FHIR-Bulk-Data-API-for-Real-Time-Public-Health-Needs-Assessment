FROM openjdk:8-jdk-alpine

VOLUME /tmp
COPY ./target/bulk_fhir_server*.jar app.jar

ENV SPRING_DATASOURCE_URL "jdbc:mysql://bulk-fhir-datastore:3306/bulk_fhir_datastore?useSSL=false&&serverTimezone=UTC"
ENV SPRING_DATASOURCE_USERNAME "doctor*who"
ENV SPRING_DATASOURCE_PASSWORD "cyber*men"
ENV CORS_ORIGIN "https://cs6440-f18-prj20.apps.hdap.gatech.edu"

EXPOSE 8080

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]