
# Foreign Currency Trading API Service

## Articles

- [Spring — A Faster Way To Build Production-Ready API in a Well- Defined Structure](https://medium.com/dev-genius/spring-a-faster-way-to-build-production-ready-api-in-a-well-defined-structure-5b1730fa81dd)
- [Uplift Your Java Spring Boot To WebFlux Non-Blocking Application](https://blog.devgenius.io/uplift-your-java-spring-boot-to-webflux-non-blocking-application-7b207999e669)
- [Java Spring — The Best Practice of Modern Software Quality Assurance](https://blog.devgenius.io/java-spring-the-best-practice-of-modern-software-quality-assurance-e5aa66466627)
- - [Start Behavior-Driven Development (BDD) for Java SpringBoot REST API Using Cucumber](https://blog.devgenius.io/start-behavior-driven-development-bdd-for-java-springboot-rest-api-using-cucumber-f6b1754d93b1)
- [DevOps— Software Quality Control Using Jenkins CI/CD Pipeline](https://blog.devgenius.io/devops-software-quality-control-using-jenkins-ci-cd-pipeline-373ade18d738)
- [CircleCI — The Modern Cloud-Based Platform of Pipeline Automation](https://blog.devgenius.io/circleci-the-modern-cloud-based-platform-of-pipeline-automation-a5b293c9e171)
- [How To Fast Track Your Apps & Website Development Using React JS + Grommet](https://blog.devgenius.io/how-to-fast-track-your-apps-website-development-using-react-js-grommet-b54b1353a3e1)
- [How to Build Performance Tests To Validate Your APIs Using Gatling](https://medium.com/@gavinklfong/how-to-build-performance-tests-to-validate-your-apis-using-gatling-b91adaf08222)
- [Is Spring WebFlux a Myth?](https://blog.devgenius.io/is-spring-webflux-a-myth-4526c2f92413)
- [How To Build A Marvelous Tech Stack Of Elasticsearch](https://blog.devgenius.io/how-to-build-a-marvelous-tech-stack-of-elasticsearch-798bae89b13c)
- [How to Build High Performance API Client Using Reactive Feign](https://blog.devgenius.io/how-to-build-high-performance-api-client-using-reactive-feign-bae4b5053d12)
- [How to Verify API Interfaces Using Automated Contract Test?](https://blog.devgenius.io/how-to-verify-api-interfaces-using-automated-contract-test-7002cce5a306)
- [Contract Test — Spring Cloud Contract vs PACT](https://medium.com/dev-genius/contract-test-spring-cloud-contract-vs-pact-420450f20429)

## Introduction

This repository was created with the aim of demonstrating the prevailing non-blocking technology of Spring framework WebFlux and the reactive programming style.

It is an API service which serves the use case of foreign currency trading. Here is list of API endpoints:

- [GET] /rates/latest/<currency>  : Fetch the latest foreign currency rates
- [GET] /rates/book : Reserve an exchange rate
- [GET] /deals : Fetch list of deals of specific customer
- [POST] /deals : Submit a new foreign exchange deal

## Dependency

### Libraries

Maven pom.xml was generated on Spring Initializr with the following dependent libraries:

- **Spring WebFlux** - Web Framework supports non-block operations
- **Spring Data R2DBC** - Persistent data access
- **Spring Validation** - Validation definition using annotations
- **H2 Database** - In-memory database for testing

### Forex Rate API

To simulate API provider for Forex Rate, the workspace comes with a json data for **json-server** so that this application can consume the API for forex rate retrieval.

You will need to install json-server on your environment:

`npm install -g json-server`

or 

`./mock-server/docker-compose.sh up -d`


## Build & Run
	
This is Maven project, you can clone this repository to your local machine and then use maven command to build and running this application.

You can follow the commands below:

**Start up the mock forex rate API:**

`json-server ./k8s/mock-server/db.json` 

or

`./mock-server/docker-compose.sh up -d`

**Build:**
`mvn install`

**Run:**
`mvn spring-boot:run`

## Package jar file and Build docker image
**Package jar file:**
`mvn package -DskipTests`

**Build docker image:**
` docker build -t zarinfam/reactive-spring-forex-trade .`


## Docker Image

Alternatively, you can run this application using the published docker image.

`docker run --rm -p 8080:8080 whalebig27/reactive-spring-forex-trade`
	
## Development

Run `docker-compose up` to run json-server and then run or debug code inside the ide

## Use Case


The Use Case is a foreign currency trade service. Due to a constant fluctuation of currency prices, customers are only informed of the exact exchange rate upon completion of transaction posting. Some people are not comfortable with making a deal without knowing the cost before making a deal. 

In order to offer a better customer experience, some financial institutions allow customers to reserve an exchange rate so that customers are well informed of the rate prior to transaction submission. Certainly, such rate booking is only valid within a short period of time for sake of risk management. In addition, different preferential rates would be offered to customers with different account tiers.

The use case diagram below shows the simplified version of forex trade service with 4 functions:

- Retrieve the latest forex rates

- Book forex rate

- Post forex trade deal. it includes rate booking as customers need to have a valid rate booking prior to transaction submission.

- Retrieve list of forex transaction of a customer


![Use Case Diagram](https://raw.githubusercontent.com/gavinklfong/reactive-spring-forex-trade/master/blob/Use_Case.jpg)


## Technical Design

I published an article in medium.com for about the high level technical design of this system. You can refer to this link here:

[Spring — A Faster Way To Build Production-Ready API in a Well- Defined Structure](https://medium.com/dev-genius/spring-a-faster-way-to-build-production-ready-api-in-a-well-defined-structure-5b1730fa81dd)

### Configuration

Application specific configuration resides in spring boot default configuration file - applicaton.yaml

- **[app.rate-booking-duration]** - Valid period of rate booking in second.
- **[app.default-base-currency]** - Default base currency.
- **[app.forex-rate-api-url]** - External API service for currency rate retrieval

### Data Model

There are 3 entities Customer, Rate Booking and Trade Deal with one-to-many relationship.

![Data Model](https://raw.githubusercontent.com/gavinklfong/reactive-spring-forex-trade/master/blob/Data_Model.jpg?raw=true)


### Components

The whole system consists of controllers, services and repositories. Each type component is assigned with a distinct role:

- **Controller**:  Expose business service to HTTP and handle all system logic related to HTTP protocol
- **Service**: Integrate various component to business logic
- **Repository**: The interface for access to persistent store


![Component Diagram](https://raw.githubusercontent.com/gavinklfong/reactive-spring-forex-trade/master/blob/Component.jpg?raw=true)


### Data Flow

Illustration of the interaction between components is shown in the data flow diagram below

![Component Diagram](https://raw.githubusercontent.com/gavinklfong/reactive-spring-forex-trade/master/blob/Activity-Forex_Deal.jpg?raw=true)


## Testing

### Unit Test

Unit test code is built for each components. To isolate the target component, all other components the target depends on are mocked. In order to speed test execution, sliced spring context is loaded, meaning that only necessary spring bean will be initialized and injected.

**Controller**

- Service component is mocked for testing on controller
- Use of @WebFluxTest annotation to loaded sliced spring context with the target controller class

![Unit Test for Controller](https://raw.githubusercontent.com/gavinklfong/reactive-spring-forex-trade/master/blob/Testing-Unit_Test-Controller.jpg?raw=true)

**Service**

- Repository component is mocked for testing on service.
- Use of @SpringJUnitConfig and @ContextConfiguration annotation to loaded sliced spring context with the target service class

![Unit Test for Service](https://raw.githubusercontent.com/gavinklfong/reactive-spring-forex-trade/master/blob/Testing-Unit_Test-Service.jpg?raw=true)

**Repository**

- In-memory database will be used for testing on repository. 
- Use of @DataJpaTest annotation to load sliced spring context

![Unit Test for Repository](https://raw.githubusercontent.com/gavinklfong/reactive-spring-forex-trade/master/blob/Testing-Unit_Test-Repository.jpg?raw=true)


### Integration Test

Next, end-to-end test is executed as if a real application. Thus, spring context is fully loaded using @SpringBootTest annotation

WebTestClient is used as API client which triggers HTTP requests to API endpoints and verify the actual result.
