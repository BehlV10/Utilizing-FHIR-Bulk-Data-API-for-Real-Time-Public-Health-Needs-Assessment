# Special Instructions
## CS6440 Fall 2018 – FHIR Buffer Overflow
### Utilizing FHIR Bulk Data API for Real-Time Public Health Needs Assessments

#### Project 34
#### Team Name: FHIR Buffer Overflow
#### TA Mentor: Taylor Startin
#### External Mentors: Johnny Bender
#### [Github Repository Link:] https://github.gatech.edu/gt-cs6440-hit-fall2018/Utilizing-FHIR-Bulk-Data-API-for-Real-Time-Public-Health-Needs-Assessments

![Image](./imgs/github.png)



|Team Members | GT Username | Email |
| ------------| ----------- | ----- |
| Varun Behl  | vbehl3      | vbehl@gatech.edu|
| Pauline Sho | psho3       | psho3@gatech.edu |
| Chulmin “Simon” Lee | clee702 | clee702@gatech.edu |
| Cody Hutchens | chutchens3 | chutchens3@gatech.edu |
| Van Mang | vmang3 | vmang3@gatech.edu |
| Vijay Pothona | vpothana3 | vpothana3@gatech.edu |

#### Technical Requirements
1. Install docker-compose
2. Install maven
3. Install gradle

#### How to run the Application
##### Step 1: Clone the repository 
`git clone https://github.gatech.edu/gt-cs6440-hit-fall2018/Utilizing-FHIR-Bulk-Data-API-for-Real-Time-Public-Health-Needs-Assessments.git`
##### Step 2: Change into the server directory
`cd bulk_fhir_server`
##### Step 3: Run mvnw
`./mvnw package`
##### Step 4: Change into the curator directory
`cd ../bulk_fhir_resource_curator/`
##### Step 5: Build gradle
`./gradlew build`
##### Step 6: Go back to root directory
`cd ..`
##### Step 7: Start/build docker
`docker-compose up -d --build`
 
