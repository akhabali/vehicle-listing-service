# Vehicle Listing Service

![CI](https://github.com/akhabali/vehicle-listing-service/actions/workflows/ci-github-actions.yml/badge.svg)

A RESTful service for managing listings for online advertising service.

Check the API documentation here https://akhabali.github.io/vehicle-listing-service/

The service provides a RESTful API for the following resources:
* Dealers (CRUD)
* Vehicle Listings (CRUD)

# Getting Started

## How To Run
Download the released jar and run the following command

// TBD

## API Documentation
This Service provides an Open API documentation using Swagger UI.

The documentation is hosted using GitHub pages and can be browsed here https://akhabali.github.io/vehicle-listing-service/

The documentation is also available locally for testing at http://localhost:8080

## How to Build Locally
Clone this project and move the local folder where the project was cloned.

* Build: ``gradlew build``
* Run tests: ``gradlew test`` 
* Generate a Boot Jar ``gradlew bootJar``
* Run: ``gradlew bootRun``

