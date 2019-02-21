# TheTURK

This repository has 3 project

    ├── client - Angular project
    ├── server - Spring Boot project
    └── turkjob - project which generates uploadable jar file


1) Client :
    Client project is developed using Angular and has server URL setting as below, this nees to be changed according to deployment
    serverUrl: 'http://localhost:8080'

2) Server :
Server project is developed using SpringBoot.
Below properties (from a through g) needs to be done in application.properties file

# ===============================
# = TURK Properties
# ===============================
a) com.theturk.fileUploadLocation=/home/vijay/fileupload - this is the location where jar files uploaded from client

b) com.theturk.maxWorkersAllowedToRegister=5 - Maximum workers allowed to register in this application

c) com.theturk.maxJobsPerWorker=2 - Maximum jobs allowd to execute per user

d) com.theturk.maxTimeToExecuteJobInSec=5 - Maximum time allowd per job to execute

# ===============================
# = DATA SOURCE
# ===============================
e) spring.datasource.url=jdbc:postgresql://localhost:5432/turk - Postgresql server url and database name

f) spring.datasource.username=turk - Postgresql user name

g) spring.datasource.password=turk - Postgresql password



