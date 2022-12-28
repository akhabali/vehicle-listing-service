# Vehicle Listing Service

![CI](https://github.com/akhabali/vehicle-listing-service/actions/workflows/ci-github-actions.yml/badge.svg)

A RESTful service for managing listings for online advertising service.

Check the API documentation here https://akhabali.github.io/vehicle-listing-service/

The service provides a RESTful API for the following resources:
* Dealers (CRUD)
* Vehicle Listings (CRUD)

To check how this was build, see the merged PR list:
https://github.com/akhabali/vehicle-listing-service/pulls?q=is%3Apr+is%3Aclosed


This repo uses:
- Java 17
- Spring Boot, Spring Data JPA
- TestRestTemplate and Junit5 for testing
- OpenAPIv3 and Swagger UI for API documentation
- Github Actions for CI
- Github Pages for API documentation hosting
- Github Packages as an artifact repository


# Getting Started

## How To Run

### Requirement

* Java 17: please download and setup `JAVA_HOME` https://adoptium.net/download/
* Download the released fat jar from this repository package https://github.com/akhabali/vehicle-listing-service/packages/1759566

Run the following command

``java -jar vehicle-listing-service-0.0.1.jar``

The console output should look like the following

![Startup console](/screenshots/jar_startup.png "Startup console")

From a web browser, check the API documentation and playground AT http://localhost:8080



## API Documentation
This Service provides an Open API documentation using Swagger UI.

The documentation is hosted using GitHub pages and can be browsed here https://akhabali.github.io/vehicle-listing-service/
![API documentation](/screenshots/openapi_swaggerui.png "API documentation")

The documentation is also available locally for testing at http://localhost:8080

## How to Build & Run Locally from Sources
Clone this project and move to the local folder where the project was cloned.

* Build: ``gradlew build``
* Run tests: ``gradlew test`` 
* Generate a Boot Jar ``gradlew assemble``
* Run: ``gradlew bootRun``

