# TBC Digital Advanced Test Automation

## Project Overview

This project is an automated testing framework for the TBC Bank digital website.

The framework combines UI, API, database-driven, localization, API-to-UI, and browser network validation scenarios.

### Technologies

- Java 21
- Playwright
- TestNG
- Rest Assured
- MyBatis
- H2 Database
- Maven
- Zephyr Scale

The automated scenarios cover Consumer Loan functionality, Money Transfers, Installment Terms, POS Terminals, localization, database-driven testing, API validation, API-to-UI consistency, and browser network validation.

---

## Project Structure

```text
src/
├── main/
│   ├── java/
│   │   └── ge/tbc/testautomation/
│   │       ├── api/
│   │       │   └── models/
│   │       ├── components/
│   │       ├── constants/
│   │       ├── database/
│   │       │   ├── mappers/
│   │       │   └── models/
│   │       ├── pages/
│   │       └── steps/
│   │
│   └── resources/
│       ├── mybatis/
│       └── database.sql
│
└── test/
    └── java/
        └── ge/tbc/testautomation/tests/
            ├── Base/
            ├── api/
            ├── data/
            └── ui/
                ├── ConsumerLoanTest
                ├── InstallmentTest
                ├── LocalizationTest
                ├── MoneyTransferFeeTest
                ├── MoneyTransferNetworkTest
                └── PosTerminalTest

pom.xml
testing.xml
README.md
```

---

# 1. Framework Architecture

The framework follows a Page Object and Steps-based architecture that separates locators, reusable components, business actions, test data, and test scenarios.

### Pages

The `pages` package contains Page Object classes responsible for page-specific UI elements.

Main Page Objects include:

- `ConsumerLoanPage`
- `MoneyTransfersPage`
- `InstallmentPage`
- `PosTerminalPage`
- `HomePage`

Page Objects contain locators used by the corresponding Steps classes.

XPath locators are used for UI element identification.

### Components

The `components` package contains reusable UI elements shared between different scenarios.

`NavigationComponent` represents common website navigation and is reused by multiple flows.

It contains navigation elements such as:

- Personal
- For Business
- localized navigation buttons

This prevents duplication of shared navigation locators across Page Objects.

### Steps

The `steps` package contains reusable business actions built on top of Page Objects and Components.

Examples include:

- opening the Personal menu
- navigating to Consumer Loan
- entering calculator data
- selecting money transfer currency and country
- opening Installment Terms
- opening POS Terminal forms
- validating UI results

This keeps TestNG test methods focused on business scenarios instead of low-level Playwright interactions.

### Constants

Reusable test values are stored in `Constants.java`.

These include:

- URLs
- API endpoints
- HTTP methods and status codes
- UI text
- calculator values
- money transfer data
- installment values
- POS Terminal values
- navigation values

This reduces duplicated hardcoded values throughout the project.

---

# 2. Localization Strategy

The project validates both English and Georgian versions of the TBC Bank website.

Localization data is stored in a TestNG DataProvider.

The current data contains two locale variations:

```text
English:
https://tbcbank.ge/en
Personal
For Business
TBC

Georgian:
https://tbcbank.ge/ka
ჩემთვის
ჩემი ბიზნესისთვის
თიბისი
```

The same `LocalizationTest` is executed for both data sets.

`NavigationComponent.getNavigationButton()` receives the expected localized navigation text dynamically.

The flow is:

```text
LocalizationDataProvider
        ↓
Locale-specific URL and labels
        ↓
LocalizationTest
        ↓
NavigationComponent
        ↓
Playwright validation
```

This avoids creating separate test methods for each language.

---

# 3. SQL and Test Data Strategy

A local H2 database and MyBatis are used to provide Consumer Loan calculator test data.

The database contains multiple loan variations:

```text
Amount = 3000   Period = 48
Amount = 5000   Period = 36
Amount = 10000  Period = 24
Amount = 15000  Period = 12
```

The data flow is:

```text
H2 Database
     ↓
database.sql
     ↓
MyBatis
     ↓
LoanDataMapper
     ↓
LoanData
     ↓
LoanDataProvider
     ↓
TestNG DataProvider
     ↓
ConsumerLoanTest
```

`DatabaseConfig` initializes the H2 database and executes `database.sql`.

`LoanDataMapper` retrieves the rows from the `loan_data` table.

Each database row is mapped to a `LoanData` Java object.

`LoanDataProvider` supplies these objects to the TestNG test.

This allows the same Consumer Loan calculator test to execute with multiple data sets without duplicating test methods.

---

# 4. API Testing Strategy

Rest Assured is used for API validation.

The Money Transfer Fees endpoint is tested with both positive and negative scenarios.

### Positive scenario

The request contains:

```text
amount=200
currencyCode=EUR
receiveCountryCode=GEO
```

The response is deserialized into `MoneyTransferFee` POJOs.

The test validates the returned money transfer systems and their corresponding fees.

### Negative scenario

The request is sent without the required `currencyCode`.

The test validates:

- HTTP 400 status
- validation error title
- `currencyCode` validation message

This provides both successful and negative API validation.

---

# 5. API-to-UI Validation

The Consumer Loan functionality is used for API-to-UI consistency validation.

The test calls:

```text
/api/v1/sites/pages/VL9d8DnAnqAGWv84sUJvZ?locale=en-US
```

The response is deserialized into Consumer Loan POJOs.

The test extracts:

```text
yearlyPercent
effectivePercent
```

Playwright then opens the Consumer Loan page and validates the corresponding UI values:

- Interest rate
- Effective interest rate

The flow is:

```text
Consumer Loan API
        ↓
POJO deserialization
        ↓
yearlyPercent + effectivePercent
        ↓
Consumer Loan UI
        ↓
Playwright validation
```

The expected UI values are obtained from the API response rather than duplicated as separate hardcoded test expectations.

---

# 6. Playwright Network Validation

The Money Transfer Fee Calculator is used for browser network validation.

The UI scenario enters:

```text
Amount: 200
Currency: EUR
Country: Georgia
```

Selecting the receiving country triggers:

```text
GET /api/v1/moneyTransfer/fees
```

Playwright captures the browser response using:

```java
page.waitForResponse(...)
```

The test validates:

- expected API endpoint
- GET request method
- HTTP 200 response
- `amount=200`
- `currencyCode=EUR`
- `receiveCountryCode=GEO`

After validating the network request, the test also verifies that a commission result is displayed in the UI.

The flow is:

```text
UI interaction
      ↓
Select country
      ↓
page.waitForResponse()
      ↓
Validate endpoint and method
      ↓
Validate HTTP status
      ↓
Validate request parameters
      ↓
Verify UI result
```

---

# 7. Test Stability

The framework uses Playwright synchronization mechanisms instead of fixed delays.

### Browser isolation

Each UI test receives a new `BrowserContext` and `Page`.

The context is closed after each test.

This prevents browser state, cookies, and selections from one test affecting another test.

### Playwright assertions

Playwright assertions automatically wait for the expected UI state.

### Network synchronization

Browser network validation uses:

```java
page.waitForResponse(...)
```

The listener is registered around the UI action that triggers the request.

No `Thread.sleep()` is used for synchronization.

---

# 8. Zephyr Scale and Automation Traceability

Test scenarios are documented in Zephyr Scale.

| Zephyr ID | Scenario |
|---|---|
| SCRUM-T42 | POS Terminal Types |
| SCRUM-T47 | Money Transfer Fee Calculation |
| SCRUM-T48 | Money Transfer Systems API |
| SCRUM-T51 | Online Installment Terms |
| SCRUM-T52 | Consumer Loan API → UI Validation |
| SCRUM-T55 | Consumer Loan Calculator |
| SCRUM-T56 | Header Localization |
| SCRUM-T57 | Consumer Loan Calculator with Database Test Data |
| SCRUM-T58 | Money Transfer Fee Network Request |

Automated tests use TestNG descriptions to provide traceability to the corresponding Zephyr scenarios.

Example:

```java
@Test(
    description = "SCRUM-T58 | Verify Money Transfer Fee Network Request"
)
```

---

# 9. Parallel Execution

The TestNG suite configuration is stored in `testing.xml`.

The suite uses class-level parallel execution:

```xml
<suite name="TBC Digital Automation Suite"
       parallel="classes"
       thread-count="3">
```

Class-level parallelism allows independent test classes to execute concurrently.

Each UI test uses an isolated browser context to reduce state interference between tests.

---

# 10. Test Execution Result

The current automated suite executes successfully:

```text
Total tests run: 12
Passes: 12
Failures: 0
Skips: 0
```

---

# 11. Running the Tests

## IntelliJ IDEA

Open `testing.xml` and run the TestNG suite.

## Maven

```bash
mvn clean test
```

The project requires Java 21 and the Maven dependencies defined in `pom.xml`.

---

# Automated Coverage

The project includes:

- Playwright UI automation
- Page Object Model
- reusable navigation component
- Steps layer
- XPath locators
- Georgian and English localization validation
- TestNG DataProviders
- SQL/H2 test data
- MyBatis database mapping
- Rest Assured positive API validation
- Rest Assured negative API validation
- POJO response deserialization
- API-to-UI consistency validation
- Playwright browser network validation
- isolated browser contexts
- parallel TestNG execution
- Zephyr Scale automation traceability