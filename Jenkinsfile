#!/usr/bin/env groovy
pipeline{
    agent any

    stages {
        stage('Build'){
            // //Define the docker image to use for the test stage
            // agent {
            //     docker{ image 'openjdk:8-jdk-alpine' }
            // }
            //Since this is a groovy file we use the '''string''' syntax to define multi-line formatting.
            //Groovy will use the string EXACTLY as written in between the ''' characters. In this instance each
            //line between the ''' characters will be treated as separate lines of a shell script.
            steps {
                sh '''cd ./bulk_fhir_server && ./mvnw package'''
                sh '''cd ./bulk_fhir_resource_curator && ./gradlew build'''
            }
        }

        stage('Deploy') {
            steps {
                //The Jenkins Declarative Pipeline does not provide functionality to deploy to a private
                //Docker registry. In order to deploy to the HDAP Docker registry we must write a custom Groovy
                //script using the Jenkins Scripting Pipeline. This is done by placing Groovy code with in a "script"
                //element. The script below registers the HDAP Docker registry with the Docker instance used by
                //the Jenkins Pipeline, builds a Docker image of the project, and pushes it to the registry.
                script{
                    docker.withRegistry('https://build.hdap.gatech.edu'){
                        def jwks_server = docker.build("jwks-server:1.0", "-f ./jwks_server/Dockerfile ./jwks_server")
                        jwks_server.push('latest')

                        def bulk_fhir_datastore = docker.build("bulk-fhir-datastore:1.0", "-f ./bulk_fhir_datastore/Dockerfile ./bulk_fhir_datastore")
                        bulk_fhir_datastore.push('latest')

                        def bulk_fhir_client = docker.build("bulk-fhir-client:1.0", "-f ./bulk_fhir_client/Dockerfile ./bulk_fhir_client")
                        bulk_fhir_client.push('latest')

                        def bulk_fhir_server = docker.build("bulk-fhir-server:1.0", "-f ./bulk_fhir_server/Dockerfile ./bulk_fhir_server")
                        bulk_fhir_server.push('latest')

                        def bulk_fhir_curator = docker.build("bulk-fhir-curator:1.0", "-f ./bulk_fhir_resource_curator/Dockerfile ./bulk_fhir_resource_curator")
                        bulk_fhir_curator.push('latest')
                    }
                }
            }
        }

        stage('Notify') {
            steps {
                script{
                    rancher confirm: true, credentialId: 'rancher-server', endpoint: 'https://rancher.hdap.gatech.edu/v2-beta', environmentId: '1a7', environments: '', image: 'build.hdap.gatech.edu/jwks-server:latest', ports: '', service: 'fbo/jwks-server', timeout: 50
                    rancher confirm: true, credentialId: 'rancher-server', endpoint: 'https://rancher.hdap.gatech.edu/v2-beta', environmentId: '1a7', environments: '', image: 'build.hdap.gatech.edu/bulk-fhir-datastore:latest', ports: '', service: 'fbo/bulk-fhir-datastore', timeout: 50
                    rancher confirm: true, credentialId: 'rancher-server', endpoint: 'https://rancher.hdap.gatech.edu/v2-beta', environmentId: '1a7', environments: '', image: 'build.hdap.gatech.edu/bulk-fhir-client:latest', ports: '', service: 'fbo/bulk-fhir-client', timeout: 50
                    rancher confirm: true, credentialId: 'rancher-server', endpoint: 'https://rancher.hdap.gatech.edu/v2-beta', environmentId: '1a7', environments: '', image: 'build.hdap.gatech.edu/bulk-fhir-server:latest', ports: '', service: 'fbo/bulk-fhir-server', timeout: 50
                    rancher confirm: true, credentialId: 'rancher-server', endpoint: 'https://rancher.hdap.gatech.edu/v2-beta', environmentId: '1a7', environments: '', image: 'build.hdap.gatech.edu/bulk-fhir-curator:latest', ports: '', service: 'fbo/bulk-fhir-curator', timeout: 50
                }
            }
        }
    }
}
