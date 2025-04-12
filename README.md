# Playwright Java Accelerator

[![Playwright Tests](https://github.com/mypixelquest/playwright-java-accelerator/actions/workflows/playwright.yml/badge.svg)](https://github.com/mypixelquest/playwright-java-accelerator/actions/workflows/playwright.yml)

A robust, maintainable, and modern test automation framework using Java 17 and Playwright. This framework utilizes TestNG for test orchestration, Allure for rich reporting, SLF4j/Logback for logging, and includes best practices like the Page Object Model, parallel execution readiness, and automatic screenshots on failure.

## Features

- **Modern Language**: Built with Java 17
- **Powerful Browser Automation**: Microsoft Playwright for Java
- **Robust Test Orchestration**: TestNG for parallel execution, grouping, and more
- **Rich Reporting**: Allure Reports with screenshots, steps, and test details
- **Effective Logging**: SLF4j with Logback implementation
- **Maintainable Design Patterns**: Page Object Model and fluent interfaces
- **Configuration Management**: YAML-based configuration with environment-specific profiles
- **Streamlined Assertions**: AssertJ for fluent, powerful assertions
- **Clean Code**: Optional use of Lombok to reduce boilerplate code
- **CI/CD Integration**: GitHub Actions workflow for automated testing and reporting

## Dependencies

- Java 17
- Maven 3.6+
- Playwright 1.42.0
- TestNG 7.9.0
- Allure 2.25.0
- SLF4J 2.0.11
- Logback 1.4.14
- AssertJ 3.25.1
- Jackson 2.16.1
- Lombok 1.18.30

## Project Structure

```
src/
  main/
    java/
      io/
        github/
          mypixelquest/
            pja/                      # Main source code (if applicable)
  test/
    java/
      io/
        github/
          mypixelquest/
            pja/
              base/                   # Base test classes & Playwright setup
                BaseTest.java         # Core test setup & teardown
              config/                 # Configuration models
                ConfigModel.java      # YAML configuration POJO
              listeners/              # TestNG & Allure listeners
                ScreenshotListener.java  # Auto-screenshot on failure
              pages/                  # Page Object Model classes
                BasePage.java         # Base page object functionality
                ExamplePage.java      # Example page implementation
              tests/                  # TestNG test classes
                ExampleTest.java      # Example test implementation
              utils/                  # Helper utilities
                ConfigReader.java     # Configuration loader
    resources/
      config/                         # Configuration files
        qa.yaml                       # QA environment config
      logback.xml                     # Logging configuration
      suites/                         # TestNG XML suite files
        example-suite.xml             # Example-specific test suite
        testng.xml                    # Main test suite
```

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Getting Started

### Installation

1. Clone this repository:
   ```bash
   git clone https://github.com/mypixelquest/playwright-java-accelerator.git
   cd playwright-java-accelerator
   ```

2. Install browser binaries for Playwright:
   ```bash
   mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
   ```

### Running Tests

Run the default test suite:
```bash
mvn test
```

Run specific test suites:
```bash
mvn test -DsuiteXmlFile=src/test/resources/suites/example-suite.xml
mvn test -DsuiteXmlFile=src/test/resources/suites/testng.xml
```

Run with specific browser:
```bash
mvn test -Dbrowser=firefox
```

Run in headed mode:
```bash
mvn test -Dheadless=false
```

Run tests with specific environment:
```bash
mvn test -Denvironment=qa
```

Run a specific test group:
```bash
mvn test -Dgroups=smoke
```

### Generating Reports

Generate and open Allure report:
```bash
mvn allure:report
mvn allure:serve
```

## Continuous Integration/Deployment

The project includes a GitHub Actions workflow that:
- Runs tests automatically on push to main/master and pull requests
- Generates and deploys Allure reports to GitHub Pages
- Uploads test screenshots on failure
- Uses macOS runners for test execution
- Caches Maven dependencies for faster builds

### GitHub Actions Configuration

The project uses GitHub Actions for CI/CD with the following features:
- Automatic test execution on code changes
- Allure report generation and deployment
- Screenshot capture on test failures
- Maven dependency caching
- Java 17 setup
- Browser installation automation

To configure GitHub Actions:
1. Set up the `GH_PAGES_TOKEN` secret in your repository settings
2. The workflow will automatically deploy reports to the gh-pages branch

## Parallel Execution

The framework supports parallel test execution through:
- TestNG parallel execution configuration
- GitHub Actions parallel job execution
- Configurable thread count
- Environment-specific parallel settings

### Logging Configuration

The framework uses SLF4J with Logback for comprehensive logging. Logs are configured in `src/test/resources/logback.xml`.

#### Log Files Location
- **Main log file**: `logs/playwright-tests.log`
- **Archived logs**: `logs/archived/playwright-tests.YYYY-MM-DD.i.log`

#### Log Rotation Policy
- Log files are automatically rotated when:
  - Size reaches 10MB
  - A new day starts
- Retention policy:
  - Keeps logs for 30 days
  - Total size cap of 100MB for all archived logs

#### Log Levels
Default log levels are configured as follows:
- Framework code (io.github.mypixelquest.pja): DEBUG
- TestNG: INFO
- Playwright: INFO
- Root logger: INFO

#### Customizing Logging
You can modify logging behavior by editing `logback.xml`:
- Change log levels
- Modify rotation policies
- Add new appenders
- Customize log patterns

Example log output:
```
2025-04-13 00:07:32.290 [main] INFO  i.g.m.pja.utils.ConfigReader - Loading configuration
2025-04-13 00:07:32.546 [TestNG-1] DEBUG i.g.m.pja.base.BaseTest - Setting up browser
```

Console output uses a more concise format with highlighted log levels and cyan logger names for better readability.

## Configuration

The framework uses YAML configuration files located in `src/test/resources/config/` as the single source of truth for all configuration settings.

Example configuration (qa.yaml):
```yaml
environment:
  name: qa
  baseUrl: https://playwright.dev/

browser:
  type: chromium  # chromium, firefox, or webkit
  headless: true
  slowMo: 0       # milliseconds to wait between actions
  timeout: 30000  # default timeout in milliseconds
  
screenshot:
  takeOnFailure: true
  fullPage: true

testExecution:
  parallel: true     # enable/disable parallel execution
  threadCount: 3     # number of parallel threads to use

retry:
  enabled: true      # enable/disable test retries
  maxRetries: 2      # maximum number of retry attempts
  delayBetweenRetries: 1000  # delay in milliseconds between retries
```

### Configuration Management

The framework uses a configuration management system that:
- Centralizes all settings in YAML configuration files
- Supports environment-specific configurations
- Allows runtime overrides via system properties
- Uses TestNG listeners for dynamic configuration
- Provides automatic test retry capabilities

Common configuration overrides:
```bash
# Override browser settings
mvn test -Dbrowser.type=firefox -Dbrowser.headless=true

# Override parallel execution
mvn test -DtestExecution.parallel=false
mvn test -DtestExecution.threadCount=4

# Override retry settings
mvn test -Dretry.enabled=true
mvn test -Dretry.maxRetries=3
mvn test -Dretry.delayBetweenRetries=2000

# Override environment
mvn test -Denvironment=qa
```

### Test Retry Configuration

The framework includes automatic test retry capabilities for handling flaky tests or temporary environment issues:

#### Retry Features:
- Configurable retry attempts for failed tests
- Optional delay between retry attempts
- Automatic retry analyzer applied to all test methods
- Detailed retry logging and reporting
- Environment-specific retry configuration

Configure retry behavior through YAML:
```yaml
retry:
  enabled: true      # Enable/disable retry mechanism
  maxRetries: 2      # Maximum retry attempts (0-N)
  delayBetweenRetries: 1000  # Milliseconds to wait between retries
```

Or override at runtime:
```bash
mvn test -Dretry.enabled=true -Dretry.maxRetries=3
```

### Parallel Execution

The framework supports dynamic parallel execution configuration through the YAML config file. Parallel execution can be:
- Enabled/disabled via YAML configuration
- Controlled at runtime via system properties
- Configured for number of parallel threads
- Applied at the method level for maximum parallelization

To modify parallel execution settings:
1. Via YAML (qa.yaml):
   ```yaml
   testExecution:
     parallel: true
     threadCount: 3
     ```

## Included Test Examples

The framework includes examples demonstrating how to test the Playwright.dev website:

### Playwright Website Tests

The `ExampleTest` class demonstrates various interactions with the Playwright documentation website:

- **Homepage Navigation Test**: Verifies basic navigation and "Get Started" functionality
- **Java Documentation Test**: Tests navigation to Java-specific documentation
- **Search Functionality Test**: Validates the search feature and modal dialog
- **Tools Navigation Test**: Tests navigation between different Playwright tools (Codegen, Trace Viewer)

These tests showcase:
- Page Object Model implementation
- Playwright's auto-waiting capabilities
- Handling different types of UI elements
- Navigation between different sections
- Modal dialog interaction
- URL verification
- Element visibility checks

Run these tests with:
```bash
mvn test
```

Or specifically:
```bash
mvn test -DsuiteXmlFile=src/test/resources/suites/example-suite.xml
```

## Creating Tests

### 1. Create a Page Object

```java
public class ExamplePage extends BasePage {
    // Example implementation showing Playwright locators and methods
    private final Locator getStartedButton;
    private final Locator searchButton;
    private final Locator javaLink;
    
    public ExamplePage(Page page) {
        super(page);
        this.getStartedButton = page.locator("a.getStarted_Sjon");
        this.searchButton = page.locator("button.DocSearch");
        this.javaLink = page.locator("a[href='/java/']");
    }
    
    @Step("Navigate to homepage")
    public ExamplePage navigate() {
        page.navigate("https://playwright.dev");
        return this;
    }
    
    @Step("Click Get Started button")
    public ExamplePage clickGetStarted() {
        getStartedButton.click();
        return this;
    }
    
    @Step("Open search dialog")
    public ExamplePage openSearch() {
        searchButton.click();
        return this;
    }
}
```

### 2. Create a Test Class

```java
@Epic("Playwright Website Tests")
@Feature("Basic Website Navigation")
public class ExampleTest extends BaseTest {
    @Test(description = "Verify homepage navigation")
    @Severity(SeverityLevel.BLOCKER)
    public void testHomePageNavigation() {
        getCurrentPage().ifPresent(page -> {
            ExamplePage examplePage = new ExamplePage(page);
            examplePage.navigate()
                      .clickGetStarted();
                      
            Assertions.assertThat(examplePage.getCurrentUrl())
                     .contains("docs/intro");
        });
    }
}
```

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License - see the LICENSE file for details.
