# Guapodog

Guapodog is a RESTful web service designed to store information about developers in a backend repository. It is built on top of the [Micronaut](https://micronaut.io) framework and uses the [H2](https://www.h2database.com/html/main.html) database engine to persist data in an in-memory database.

The API specification of Guapodog is maintained in **api-definition.yaml**

## Getting Started

### Requirements

- Java 8 or higher JDK installed
- Maven 3.6.3 or higher
- Micronaut CLI 2.4

### Run Guapodog

Start the service by running 	`mvn mn:run` in the project directory. Guapodog is set to listen to port `8085` by default but this setting can be changed by configuring **src/main/resources/application.yml**

### Unit Tests

To execute the unit tests simply run `mvn test` in the project directory.

## Micronaut Reference Documentation

- [User Guide](https://docs.micronaut.io/2.4.0/guide/index.html)
- [API Reference](https://docs.micronaut.io/2.4.0/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/2.4.0/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
- [Micronaut HTTP Client documentation](https://docs.micronaut.io/latest/guide/index.html#httpClient)