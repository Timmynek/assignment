# Whalebone QA Assignment

Automated test suite covering REST API testing and UI browser testing, built with Java 21, TestNG, RestAssured, and Playwright.

---

## Tech Stack

| Tool | Purpose |
|------|---------|
| Java 21 | Language |
| Maven | Build & dependency management |
| TestNG | Test framework |
| RestAssured | REST API assertions |
| Playwright (Chromium) | Browser automation (UI & web scraping) |
| Jackson | JSON parsing |

---

## Project Structure

```
assignment/
├── pom.xml
└── src/
    └── test/
        ├── java/
        │   ├── api/
        │   │   ├── TeamsApiTest.java          # REST API tests for the teams endpoint
        │   │   └── RosterScrapingTest.java    # Scrapes oldest team's roster page via Playwright
        │   └── ui/
        │       ├── enums/
        │       │   └── SampleAppStatus.java   # Enum for expected login status messages
        │       ├── pages/                     # Page Object Model classes
        │       │   ├── HomePage.java
        │       │   ├── SampleAppPage.java
        │       │   ├── LoadDelayPage.java
        │       │   └── ProgressBarPage.java
        │       └── tests/
        │           ├── BaseTest.java          # Shared browser setup/teardown
        │           ├── SampleAppTest.java     # Login / logout scenarios
        │           ├── LoadDelayTest.java     # Page load time assertion
        │           └── ProgressBarTest.java   # Stop progress bar at 75%
        └── resources/
            └── testng.xml                     # Test suite definition
```

---

## Prerequisites

- **Java 21** or newer  
  Verify: `java -version`
- **Maven 3.6+**  
  Verify: `mvn -version`
- An active internet connection (tests hit live URLs)

> Playwright downloads Chromium browser binaries automatically on the first run via Maven — no manual browser installation needed.

---

## Running the Tests

### Run the full suite
```bash
mvn test
```
This executes all test groups defined in `src/test/resources/testng.xml` (API Tests → Sample App Tests → Load Delay Tests → Progress Bar Tests).

### Run a specific test class
```bash
mvn test -Dtest=TeamsApiTest
mvn test -Dtest=SampleAppTest
```

### Run only API tests
```bash
mvn test -Dtest="TeamsApiTest,RosterScrapingTest"
```

### Run only UI tests
```bash
mvn test -Dtest="SampleAppTest,LoadDelayTest,ProgressBarTest"
```

---

## Test Reports

After a run, Surefire generates HTML reports inside `target/surefire-reports/`:

- `index.html` — aggregated suite summary
- `emailable-report.html` — single-file report suitable for sharing
- `UI Test Suite/` — per-test-group HTML reports

Open any `.html` file in a browser to view results.
