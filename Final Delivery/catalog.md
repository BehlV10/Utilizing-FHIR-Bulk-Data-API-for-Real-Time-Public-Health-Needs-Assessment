# Catalog
## CS6440 Fall 2018 – FHIR Buffer Overflow
### Utilizing FHIR Bulk Data API for Real-Time Public Health Needs Assessments

#### Project 34
#### Team Name: FHIR Buffer Overflow
#### TA Mentor: Taylor Startin
#### External Mentors: Johnny Bender
#### [Github Link:] https://github.gatech.edu/gt-cs6440-hit-fall2018/Utilizing-FHIR-Bulk-Data-API-for-Real-Time-Public-Health-Needs-Assessments

|Team Members | GT Username | Email |
| ------------| ----------- | ----- |
| Varun Behl  | vbehl3      | vbehl@gatech.edu|
| Pauline Sho | psho3       | psho3@gatech.edu |
| Chulmin “Simon” Lee | clee702 | clee702@gatech.edu |
| Cody Hutchens | chutchens3 | chutchens3@gatech.edu |
| Van Mang | vmang3 | vmang3@gatech.edu |
| Vijay Pothona | vpothana3 | vpothana3@gatech.edu |

## CONTENTS:

- MANUAL
[FHIR Buffer Overflow Manual](https://github.gatech.edu/gt-cs6440-hit-fall2018/Utilizing-FHIR-Bulk-Data-API-for-Real-Time-Public-Health-Needs-Assessments/blob/master/Final%20Delivery/Manual%20-%20FHIR%20Buffer%20Overflow.md)
- SPECIAL INSTRUCTIONS
[Special Instructions](https://github.gatech.edu/gt-cs6440-hit-fall2018/Utilizing-FHIR-Bulk-Data-API-for-Real-Time-Public-Health-Needs-Assessments/blob/master/Final%20Delivery/Special%20Instructions%20-%20FHIR%20Buffer%20Overflow.md)
- FINAL GANTT CHART
[Final Gantt](https://github.gatech.edu/gt-cs6440-hit-fall2018/Utilizing-FHIR-Bulk-Data-API-for-Real-Time-Public-Health-Needs-Assessments/blob/master/Final%20Delivery/Final%20Gantt%20Chart%20-%20FHIR%20Buffer%20Overflow.pdf)
- PROJECT OVERVIEW VIDEO - [Video Link](https://www.youtube.com/watch?v=UsWlc61KqsA&feature=youtu.be)
- PROJECT DEMONSTRATION VIDEO - [Demo Link](https://www.youtube.com/watch?v=mE1y3c2Cm4A&feature=youtu.be)

## CODE STRUCTURE
Docker-Compose.yml - Cordinate activation of the docker lifecycle 
```bash
version: '3.0'
services:
  bulk-fhir-datastore:
    image: bulk-fhir-datastore:latest
    build:
      context: bulk_fhir_datastore
    restart: always
  jwks-server:
    image: jwks-server:latest
    build:
      context: jwks_server
    restart: always
    ports:
      - 8180:80
  bulk-fhir-client:
    image: bulk-fhir-client:latest
    build:
      context: bulk_fhir_client
    restart: always
    ports:
      - 5000:80
  bulk-fhir-server:
    image: bulk-fhir-server:latest
    build:
      context: bulk_fhir_server
    restart: always
    ports:
      - 5001:8080
    depends_on:
      - "bulk-fhir-datastore"
  bulk-fhir-curator:
    image: bulk-fhir-curator:latest
    build:
      context: bulk_fhir_resource_curator
    restart: always
    depends_on:
    - "bulk-fhir-datastore"
```

## PROJECT SPECIFICATIONS
- FHIR SPECIFICATION:  Dstu2 
- FRONT-END FRAMEWORK: HTML and CSS
- BACK-END FRAMEWORK: Java Spring 
- DATABASE: MySQL

### ORIGINAL PROJECT DESCRIPTION
[Full-text link:](http://cs6440.gatech.edu/wp-content/uploads/sites/634/2018/09/Spring2018CatalogTeam34.pdf)

### UTILIZING FHIR BULK DATA API FOR REAL-TIME PUBLIC HEALTH NEEDS ASSESSMENTS
The scope of public health includes Assessment, Policy Development, and Assurance (Institute of Medicine, 1988). It is critical for public health agencies to understand the scope of the problem they are addressing to appropriately target the problem and measure the impact of interventions. Population assessment data can be used to identify problem scope, advocate for policy changes, justify future projects, and obtain external funding (Eschenfelder, 2010).
It is a burdensome process to conduct community public health needs assessments, typically relying on the manual collection of secondary data from clinics and hospitals, surveys, and collaborations with academic health centers to document the public health needs of a jurisdiction (Bruckner & Barr, 2014). Several tools have been developed to assist public health agencies with case reporting, disease surveillance, and population health reporting, which may help inform needs assessments. But efforts to collect data for public health are often disjointed, and results from state- or nationally-submitted data often are delayed by days, months, or years.
The advent of electronic health records (EHRs) has the potential to enable public health agencies to understand the health needs in a region, but due largely to non-uniform, complex data formatting, and expensive interface costs, small health agencies often fail to leverage these valuable resources. A new HL7 standard, Fast Healthcare Interoperability Resources, aims to standardize EHR interfaces, while allowing for discrete data queries using RESTful interfaces. In January 2018, Dan Gottleib and Josh Mandel proposed to expand the features of FHIR to enable the export of massive standardized clinical data from FHIR- compatible databases. The project, FHIR Bulk Data API, has broad implications for health analytics and population health. We seek to leverage the FHIR Bulk Data API in a public health setting, to demonstrate how it can be used to enable real-time needs assessments using massive clinical data from communities.
This project will be conducted with an intended implementation in Cameron County, Texas. Cameron County neighbors the Mexico City of Matamoros with a population of 93.2% Hispanic or Latino in 2010. It is burdened with poverty, and contained some of the most impoverished cities in the U.S. based on the 2009-2013 U.S. Census (United States Census Bureau, 2015). Cameron County has a high prevalence of chronic disease, including 31% diabetes, 49% obesity, and 32% hypertension. The population is also 70% uninsured, highlighting the critical need for effective preventative health practices (UTHealth School of Public Health, 2012). Additionally, Cameron County is located in Texas Region 11, which had the second highest prevalence of diabetes and highest prevalence of overweight and obesity in Texas in 2008 (Texas Department of State Health Services, 2010).
There may also be a need to supplement clinical data obtained via the FHIR Bulk Data API with non-clinical research data from the community. As an illustrative example, local needs assessments for the Rio Grande Valley from Mission Regional Medical Center (MRMC) and Valley Baptist Health System reported an uninsured rate of approximately 40%, while results from a local representative cohort study reported an uninsured rate of approximately 70% (MRMC, 2013; UTHealth, 2012; Professional Research Consultants, 2011).
We believe that simplifying the needs assessment development process will enable public health agencies to spend precious resources on public health interventions, and will broadly benefit the public health community by providing a reference implementation of a public health needs assessment using the FHIR Bulk Data API, which may encourage adoption of the standard.

### PROJECT OBJECTIVES
We seek to leverage the new FHIR Bulk Data API specification to demonstrate how the tool can be used to enable real-time needs assessments using massive clinical data from communities. We will implement the findings from our extensive literature review to build a dashboard of valuable information for public health practitioners, and will work with at least one local public health authority to receive iterative feedback on the solution. Artifacts in the solution will include prevalence and incidence calculations for various diseases of importance to the area, and other epidemiological metrics. We seek to create a tool that can be used by any public health jurisdiction to collect data from clinics and hospitals in the community using a common FHIR-based API. Our overall objective is to reduce the burden for conducting community needs assessments, both for public health agencies and healthcare organizations, so these organizations can focus on implementing solutions to address the identified needs.
