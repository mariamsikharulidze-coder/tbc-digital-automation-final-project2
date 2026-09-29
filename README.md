# TBC Digital Advanced Test Automation

## Project Overview

This project is an automated testing framework for the TBC Bank digital website.

The framework combines UI, API, database-driven, localization, API-to-UI, and browser network validation scenarios.

The project uses:

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
│   │       ├── pages/
│   │       ├── components/
│   │       ├── steps/
│   │       ├── api/
│   │       │   └── models/
│   │       ├── database/
│   │       │   ├── mappers/
│   │       │   └── models/
│   │       ├── constants/
│   │       └── utils/
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

pom.xml
testng.xml
README.md
```

---

# 1. Framework Architecture

The framework separates element definitions, reusable page components, business actions, API models, database access, test data, and test scenarios.

### pages/

The `pages` package contains Page Object classes.

Each Page Object stores locators that belong to a specific TBC Bank page or feature.

Examples include:

- `ConsumerLoanPage`
- `MoneyTransfersPage`
- `InstallmentPage`
- `PosTerminalPage`

Page Objects do not contain complete test scenarios. Their main responsibility is to represent page-specific UI elements.

### components/

The `components` package contains reusable UI elements that are shared between different pages.

For example, `NavigationComponent` represents common header navigation.

It contains reusable elements such as:

- Personal
- For Business
- localized navigation buttons

Page Components are separated from Page Objects because the header is not specific to one page.

For example, the same `NavigationComponent.personalButton` is reused by Consumer Loan, Money Transfer, and Installment scenarios instead of defining the Personal navigation button separately in every Page Object.

This reduces duplicated locators and makes maintenance easier if the common navigation changes.

### steps/

The `steps` package contains reusable business actions built on top of Page Objects and Components.

Examples include:

- opening the Personal menu
- navigating to Consumer Loan
- entering calculator data
- selecting a money transfer currency
- opening POS Terminal forms
- validating resulting UI content

This keeps test methods focused on the business scenario rather than low-level Playwright operations.

### api/

The `api` package contains Java models used to deserialize API responses.

For the Consumer Loan API, nested response objects are represented by POJOs such as:

- `ConsumerLoanResponse`
- `SectionComponent`
- `TabsInputs`
- `CalculatorTab`
- `CalculatorComponent`
- `CalculatorInputs`
- `CurrencyConfiguration`

`MoneyTransferFee` is used for Money Transfer API response deserialization.

### database/

The `database` package contains the SQL/MyBatis integration.

It includes:

- database configuration
- MyBatis mapper
- Java database model

`LoanDataMapper` retrieves calculator test data and maps database rows to `LoanData` objects.

### tests/

The test packages contain the actual TestNG scenarios.

They include:

- Playwright UI tests
- localization tests
- Rest Assured API tests
- SQL/DataProvider-driven tests
- API-to-UI validation
- Playwright network validation

Assertions and scenario orchestration are kept at the test/step level instead of being mixed into Page Object locator definitions.

---

# 2. Localization Strategy

The project validates both English and Georgian versions of the TBC Bank website without duplicating the test implementation.

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

The same localization test is executed for both data sets.

`NavigationComponent.getNavigationButton()` receives the expected localized navigation text dynamically. Therefore, separate English and Georgian test methods are not required.

The flow is:

```text
LocalizationDataProvider
        ↓
locale-specific URL and expected labels
        ↓
same TestNG test
        ↓
NavigationComponent
        ↓
Playwright UI validation
```

To add another locale, a new row can be added to the localization DataProvider with the locale URL and expected navigation labels. The test logic itself does not need to be duplicated.

---

# 3. SQL and Test Data Strategy

A local H2 database and MyBatis are used to separate Consumer Loan calculator input data from the automated test implementation.

The database contains multiple loan variations, for example:

```text
Amount = 3000   Period = 48
Amount = 5000   Period = 36
Amount = 10000  Period = 24
Amount = 15000  Period = 12
```

The complete data flow is:

```text
H2 Database
     ↓
database.sql
     ↓
MyBatis
     ↓
LoanDataMapper
     ↓
LoanData Java Model
     ↓
LoanDataProvider
     ↓
TestNG DataProvider
     ↓
Playwright Consumer Loan Test
```

`DatabaseConfig` initializes the H2 database and executes `database.sql`.

`LoanDataMapper` retrieves the rows from the `loan_data` table.

Each row is mapped to a `LoanData` Java object containing the loan amount and period.

`LoanDataProvider` supplies these objects to the same TestNG test method.

This means the Consumer Loan calculator scenario runs multiple times without creating separate test methods.

A new test-data variation can be introduced by adding another record to `database.sql`.

For example:

```sql
INSERT INTO loan_data (id, amount, period)
VALUES (5, 20000, 18);
```

No change to the Playwright test implementation is required.

---

# 4. API Testing Strategy

Rest Assured is used to validate public TBC Bank API functionality.

The Money Transfer Fees endpoint is tested with both positive and negative scenarios.

The happy-path scenario sends:

```text
amount=200
currencyCode=EUR
receiveCountryCode=GEO
```

The response is deserialized into `MoneyTransferFee` POJOs.

The test validates meaningful business values for money transfer systems and their corresponding fees.

The negative scenario sends a request without the required `currencyCode`.

The test verifies:

- HTTP 400 status
- validation error title
- `currencyCode` validation message

This ensures that the API tests validate both successful behavior and server-side validation rather than checking only HTTP 200 responses.

---

# 5. API → UI Consistency Strategy

The Consumer Loan page was selected for API-to-UI consistency validation.

The test calls:

```text
/api/v1/sites/pages/VL9d8DnAnqAGWv84sUJvZ?locale=en-US
```

using Rest Assured.

The response is deserialized into nested Consumer Loan POJOs.

The test extracts two meaningful calculator values from the API:

```text
yearlyPercent
effectivePercent
```

These values are used as the source of truth.

After retrieving them, Playwright opens the corresponding Consumer Loan page and verifies the values displayed by the UI for:

- Interest rate
- Effective interest rate

The expected UI values are built from the API response rather than hardcoded separately in the UI test.

The flow is:

```text
Consumer Loan API
        ↓
POJO deserialization
        ↓
yearlyPercent + effectivePercent
        ↓
Playwright Consumer Loan page
        ↓
UI interest-rate validation
```

This test can detect inconsistencies where the backend/content API provides one interest rate while the website displays a different value.

---

# 6. Playwright Network Validation

The Money Transfer Fee Calculator is used for browser network validation.

The UI scenario enters:

```text
Amount: 200
Currency: EUR
Country: Georgia
```

Selecting the receiving country triggers the browser request:

```text
GET /api/v1/moneyTransfer/fees
```

Playwright captures the actual browser response using `page.waitForResponse()`.

The test validates:

- expected endpoint
- HTTP method is GET
- response status is 200
- `amount=200`
- `currencyCode=EUR`
- `receiveCountryCode=GEO`

After validating the network request and response, the test verifies that a commission result card is displayed on the UI.

The flow is:

```text
UI interaction
      ↓
Select Georgia
      ↓
page.waitForResponse()
      ↓
Validate endpoint
      ↓
Validate GET
      ↓
Validate HTTP 200
      ↓
Validate request parameters
      ↓
Verify commission result on UI
```

The network event itself is used for synchronization. The scenario does not use `Thread.sleep()` or arbitrary timeout-based waiting.

---

# 7. Test Stability

The Money Transfer Network Validation scenario is one of the more advanced scenarios because it combines dynamic UI interaction with browser network monitoring.

Several possible sources of instability were considered.

### 1. Dynamic dropdown interaction

The currency selector is implemented as a custom UI dropdown.

The EUR option required targeting the actual dropdown item rather than relying on a broad text locator.

The implementation uses a locator scoped to the dropdown option title:

```java
locator(".tbcx-dropdown-popover-item__title")
```

This reduces the risk of interacting with another EUR text displayed elsewhere on the page.

### 2. Network timing

The money-transfer request is asynchronous and is triggered by a UI action.

Using a fixed delay could make the test unreliable on slower or faster environments.

Instead, the implementation uses:

```java
page.waitForResponse(...)
```

The response listener is registered around the UI action that triggers the request. The test therefore continues when the relevant network event occurs rather than after an arbitrary amount of time.

### 3. Dynamic UI rendering

The commission results are rendered after the calculator receives the response.

The test uses Playwright assertions such as `isVisible()` instead of fixed sleeps.

Playwright assertions automatically wait for the expected UI state within the configured timeout.

### 4. Shared browser state

Each UI test receives a new `BrowserContext` and `Page`.

The context is closed after each test.

This prevents cookies, navigation state, or selections from one scenario from affecting another scenario.

The framework therefore reduces instability through event-based synchronization, isolated browser contexts, scoped locators, and Playwright auto-waiting rather than unnecessary retries or arbitrary waits.

---

# 8. Zephyr Scale and Automation Traceability

Test scenarios are documented in Zephyr Scale.

The documented scope includes UI, localization, database-driven, API, API-to-UI, and network-validation scenarios.

Examples include:

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

Automated tests use TestNG descriptions to provide traceability back to the corresponding Zephyr scenario.

Example:

```java
@Test(
    description = "SCRUM-T58 | Verify Money Transfer Fee Network Request"
)
```

This provides a direct connection between documented manual scenarios and their automated implementations.

---

# 9. Parallel Execution

TestNG suite configuration is stored in `testng.xml`.

The suite uses class-level parallel execution:

```xml
<suite name="TBC Digital Automation Suite"
       parallel="classes"
       thread-count="3">
```

Class-level parallelism was selected because it allows independent test classes to execute concurrently while avoiding unnecessary sharing of individual UI test state between parallel test methods.

The UI framework creates isolated browser contexts for tests, further reducing state interference.

The complete TestNG suite has been executed successfully with:

```text
Total tests run: 15
Passes: 15
Failures: 0
Skips: 0
```

---

# 10. Running the Tests

## Run with IntelliJ IDEA

Open `testng.xml` and run the TestNG suite.

## Run with Maven

```bash
mvn clean test
```

The project requires Java 21 and Maven dependencies defined in `pom.xml`.

---

# Automated Coverage

The project includes:

- Playwright UI scenarios
- reusable Page Objects
- reusable Page Components
- Georgian and English localization validation
- SQL/H2 test data
- MyBatis mapping
- TestNG DataProvider execution
- Rest Assured happy-path API validation
- Rest Assured negative API validation
- POJO response deserialization
- API-to-UI consistency validation
- Playwright browser network validation
- Zephyr Scale automation traceability
- parallel TestNG execution