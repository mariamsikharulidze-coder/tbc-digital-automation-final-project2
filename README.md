# TBC Digital Advanced Test Automation

## Project Overview

This project is a Java-based test automation framework for the TBC Bank website.

It combines UI automation, REST API testing, database-driven testing, localization, API-to-UI validation, and browser network validation.

## Technologies

- Java 21
- Playwright
- TestNG
- Rest Assured
- MyBatis
- H2 Database
- Lombok
- Maven
- Zephyr Scale

## Project Architecture

The framework separates test scenarios from reusable actions, page locators, API requests, test data, and database configuration.

### Page Object Model

The `pages` package contains page-specific UI locators.

Main Page Objects:

- `ConsumerLoanPage`
- `MoneyTransfersPage`
- `InstallmentPage`
- `PosTerminalPage`
- `HomePage`

### Reusable Components

The `components` package contains shared UI elements.

`NavigationComponent` provides reusable navigation functionality for different test scenarios.

### Steps Layer

The `steps` package contains reusable business actions and UI validations.

This separation keeps TestNG test methods focused on test scenarios rather than low-level Playwright interactions.

### API Clients

REST API requests are separated from test classes.

- `ConsumerLoanApiClient` handles Consumer Loan API requests and response processing.
- `MoneyTransferApiClient` handles positive and negative Money Transfer API requests.

### API Models

API responses are deserialized into Java POJOs.

The project uses Lombok to reduce boilerplate code in model classes.

### Constants

Shared URLs, API endpoints, HTTP status codes, test values, and expected UI text are maintained in `Constants.java`.

## Automated Test Coverage

### UI Automation

Playwright is used to automate the following website functionality:

- Consumer Loan calculator
- Online Installment Terms
- Header localization
- Money Transfer Fee Calculator
- POS Terminal functionality

### Localization

TestNG DataProvider supplies Georgian and English test data.

The same localization scenario validates navigation elements for both languages.

### Database-Driven Testing

An H2 database stores Consumer Loan calculator test data.

MyBatis maps database records to Java objects, which are supplied to TestNG tests through `LoanDataProvider`.

Database sessions are managed using Java's `try-with-resources` mechanism.

### REST API Testing

Rest Assured is used for positive and negative API validation.

Money Transfer API testing covers successful fee retrieval and validation errors caused by missing required parameters.

API responses are mapped to POJO models, and their contents are validated.

### API-to-UI Validation

Consumer Loan API data is used to validate corresponding interest rate information displayed in the website's calculator.

### Browser Network Validation

Playwright captures and validates the network request triggered by the Money Transfer Fee Calculator.

Validation includes the API endpoint, HTTP method, response status, request parameters, and the resulting UI information.

## Test Stability

The framework uses Playwright synchronization and assertions instead of fixed delays.

UI tests use isolated browser contexts to prevent test state from affecting other scenarios.

Database sessions are closed automatically after use.

## Zephyr Scale Traceability

The following scenarios are documented in Zephyr Scale:

| Zephyr ID | Scenario |
|---|---|
| SCRUM-T42 | POS Terminal Types |
| SCRUM-T47 | Money Transfer Fee Calculation |
| SCRUM-T48 | Money Transfer Systems API |
| SCRUM-T51 | Online Installment Terms |
| SCRUM-T52 | Consumer Loan API-to-UI Validation |
| SCRUM-T55 | Consumer Loan Calculator |
| SCRUM-T56 | Header Localization |
| SCRUM-T57 | Database-Driven Consumer Loan Calculator |
| SCRUM-T58 | Money Transfer Network Validation |

## Parallel Execution

The `testing.xml` file configures TestNG to execute test classes in parallel using three threads.

## Running the Tests

### IntelliJ IDEA

Open `testing.xml` and execute the TestNG suite.

### Maven

Run the following command:

```bash
mvn clean test
```

To run the explicitly configured TestNG suite:

```bash
mvn clean test -Dsurefire.suiteXmlFiles=testing.xml
```

## Test Results

The complete suite must be rerun after the latest framework refactoring. Final execution results will be documented after verification.
