# Playwright Java Accelerator

[![Playwright Tests](https://github.com/yashwant-das/playwright-java-accelerator/actions/workflows/playwright.yml/badge.svg)](https://github.com/yashwant-das/playwright-java-accelerator/actions/workflows/playwright.yml)
[![Java](https://img.shields.io/badge/java-17%2B-orange.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](LICENSE.md)

![banner](https://github.com/user-attachments/assets/dd8a014a-8002-4568-a512-b0f818905b8e)


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
- **Flexible Test Data Management**: Support for multiple data sources and formats

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
                TestConfig.java       # Configuration access singleton
              data/                   # Test data management
                TestDataManager.java  # Data loading from various formats
                TestDataGenerator.java # Dynamic test data generation
              listeners/              # TestNG & Allure listeners
                ScreenshotListener.java  # Auto-screenshot on failure
                RetryAnalyzer.java    # Test retry mechanism
              pages/                  # Page Object Model classes
                BasePage.java         # Base page object functionality
                ExamplePage.java      # Example page implementation
              tests/                  # TestNG test classes
                ExampleTest.java      # Example test implementation
                TestDataDemoTest.java # Data management demonstration tests
              utils/                  # Helper utilities
                ConfigReader.java     # Configuration loader
                WebDriverManager.java # WebDriver singleton for Selenium
    resources/
      config/                         # Configuration files
        qa.yaml                       # QA environment config
      data/                           # Test data files
        orders.json                   # Example JSON test data
        products.csv                  # Example CSV test data
        test-data.yaml               # Example YAML test data
        qa/                           # Environment-specific data (QA)
        prod/                         # Environment-specific data (Production)
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
   git clone https://github.com/yashwant-das/playwright-java-accelerator.git
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

### Viewing Test Results

#### Local Test Results
After running tests locally, you can view the Allure report by running:
```bash
mvn allure:report
mvn allure:serve
```
This will generate and open the Allure report in your default browser.

#### CI/CD Test Results
The test results are automatically published to GitHub Pages after each CI/CD run. You can view them at:
[https://yashwant-das.github.io/playwright-java-accelerator/](https://yashwant-das.github.io/playwright-java-accelerator/)

The report includes:
- Test execution history
- Test case details
- Screenshots of failed tests
- Test duration and status
- Environment information
- Test categories and tags

<img width="4010" height="2486" alt="report" src="https://github.com/user-attachments/assets/d961dfca-f3e5-4bdc-8372-269e5ac1ff32" />

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

## Test Data Management

The framework provides comprehensive test data management capabilities through multiple approaches. These are implemented through the `TestDataManager` and `TestDataGenerator` classes and demonstrated in the `TestDataDemoTest` class.

### Data Management Components

#### TestDataManager

The `TestDataManager` class serves as the central component for loading and accessing test data from different sources:

- **Supports multiple file formats**: YAML, JSON, and CSV
- **Environment-specific data**: Loads data from environment-specific directories (e.g., qa, prod)
- **Caching mechanism**: Improves performance by caching loaded data
- **Path-based value access**: Retrieves nested values using dot notation (e.g., "users.admin.username")

```java
public class TestDataManager {
    private final String environment;
    
    // Constructor accepts environment name (qa, prod, etc.)
    public TestDataManager(String environment) {
        this.environment = environment;
        // Initialize data mappers
    }
    
    // Load data from YAML files
    public Map<String, Object> loadYamlData(String fileName) { /* implementation */ }
    
    // Load data from JSON files
    public Map<String, Object> loadJsonData(String fileName) { /* implementation */ }
    
    // Load data from CSV files
    public Map<String, Object> loadCsvData(String fileName) { /* implementation */ }
    
    // Retrieve value by path (e.g., "users.admin.username")
    public Object getValue(Map<String, Object> data, String path) { /* implementation */ }
}
```

#### TestDataGenerator

The `TestDataGenerator` class provides utility methods for generating dynamic test data:

- **Email generation**: Creates random email addresses
- **Password generation**: Creates secure passwords with configurable complexity
- **Phone number generation**: Generates formatted phone numbers
- **Address generation**: Creates realistic address strings
- **Other dynamic data**: Dates, IDs, and other common test data needs

```java
public class TestDataGenerator {
    // Generate random email addresses
    public static String generateEmail() { /* implementation */ }
    
    // Generate secure passwords
    public static String generatePassword(int length, boolean includeSpecialChars) { /* implementation */ }
    
    // Generate formatted phone numbers
    public static String generatePhoneNumber() { /* implementation */ }
    
    // Generate realistic addresses
    public static String generateAddress() { /* implementation */ }
}
```

### Data Files

The framework includes example data files in the `src/test/resources/data/` directory:

#### 1. YAML Data (test-data.yaml)

```yaml
users:
  admin:
    username: admin@example.com
    password: admin123
    role: ADMIN
  regular:
    username: user@example.com
    password: user123
    role: USER

products:
  laptop:
    name: "Premium Laptop"
    price: 1299.99
    specs:
      processor: "Intel i7"
      ram: "16GB"
      storage: "512GB SSD"
  phone:
    name: "Smart Phone"
    price: 699.99
    specs:
      processor: "A15"
      ram: "8GB"
      storage: "256GB"
```

#### 2. JSON Data (orders.json)

```json
{
  "orders": [
    {
      "id": "ORD-001",
      "total": 1299.99,
      "status": "PENDING",
      "customer": {
        "name": "John Doe",
        "email": "john@example.com"
      },
      "items": [
        {
          "productId": "PROD-001",
          "quantity": 1
        }
      ]
    }
  ]
}
```

#### 3. CSV Data (products.csv)

```csv
id,name,price,category,stock,rating
PROD-001,Premium Laptop,1299.99,Electronics,50,4.8
PROD-002,Smart Phone,699.99,Electronics,100,4.6
PROD-003,Wireless Headphones,199.99,Accessories,75,4.5
```

### Data Management Tests (TestDataDemoTest)

The `TestDataDemoTest` class demonstrates how to use the data management capabilities of the framework:

#### 1. YAML Data Loading

Demonstrates loading and parsing YAML data:
```java
@Test(description = "Demonstrate YAML data loading")
public void testYamlDataLoading() {
    Map<String, Object> data = dataManager.loadYamlData("test-data.yaml");
    
    // Get user data using dot notation
    String adminUsername = (String) dataManager.getValue(data, "users.admin.username");
    String adminRole = (String) dataManager.getValue(data, "users.admin.role");
    
    // Get product data
    String laptopName = (String) dataManager.getValue(data, "products.laptop.name");
    double laptopPrice = (double) dataManager.getValue(data, "products.laptop.price");
    
    // Assertions verify data is loaded correctly
    assertThat(adminUsername).isEqualTo("admin@example.com");
    assertThat(laptopName).isEqualTo("Premium Laptop");
}
```

#### 2. JSON Data Loading

Demonstrates loading and parsing JSON data:
```java
@Test(description = "Demonstrate JSON data loading")
public void testJsonDataLoading() {
    Map<String, Object> data = dataManager.loadJsonData("orders.json");
    
    // Access nested JSON objects and arrays
    Map<String, Object> firstOrder = (Map<String, Object>) ((List<?>) data.get("orders")).get(0);
    String orderId = (String) firstOrder.get("id");
    
    // Access customer data from nested object
    Map<String, Object> customer = (Map<String, Object>) firstOrder.get("customer");
    String customerName = (String) customer.get("name");
    
    // Verify JSON data
    assertThat(orderId).isEqualTo("ORD-001");
    assertThat(customerName).isEqualTo("John Doe");
}
```

#### 3. CSV Data Loading

Demonstrates loading and parsing CSV data:
```java
@Test(description = "Demonstrate CSV data loading")
public void testCsvDataLoading() {
    Map<String, Object> data = dataManager.loadCsvData("products.csv");
    
    // Get first row from CSV data
    Map<String, Object> firstProduct = (Map<String, Object>) ((List<?>) data.get("data")).get(0);
    String productId = (String) firstProduct.get("id");
    String productName = (String) firstProduct.get("name");
    
    // Verify CSV data
    assertThat(productId).isEqualTo("PROD-001");
    assertThat(productName).isEqualTo("Premium Laptop");
}
```

#### 4. Dynamic Data Generation

Demonstrates generating dynamic test data:
```java
@Test(description = "Demonstrate dynamic data generation")
public void testDynamicDataGeneration() {
    String email = TestDataGenerator.generateEmail();
    String password = TestDataGenerator.generatePassword(12, true);
    String phone = TestDataGenerator.generatePhoneNumber();
    String address = TestDataGenerator.generateAddress();
    
    // Verify generated data meets expected patterns
    assertThat(email).contains("@example.com");
    assertThat(password).hasSize(12);
    assertThat(phone).matches("\\+1\\d{10}");
}
```

#### 5. Environment-Specific Data

Demonstrates loading environment-specific data:
```java
@Test(description = "Demonstrate environment-specific data")
public void testEnvironmentSpecificData() {
    TestDataManager prodDataManager = new TestDataManager("prod");
    
    // Compare data between environments
    Map<String, Object> qaData = dataManager.loadYamlData("test-data.yaml");
    Map<String, Object> prodData = prodDataManager.loadYamlData("test-data.yaml");
    
    // Access equivalent data points from different environments
    String qaAdminUsername = (String) dataManager.getValue(qaData, "users.admin.username");
    String prodAdminUsername = (String) dataManager.getValue(prodData, "users.admin.username");
    
    // Verify environment-specific values
    assertThat(qaAdminUsername).isNotEqualTo(prodAdminUsername);
}
```

### Running Data Management Tests

To run the data management demonstration tests:
```bash
mvn test -Dtest=TestDataDemoTest
```

To run a specific test method:
```bash
mvn test -Dtest=TestDataDemoTest#testYamlDataLoading
mvn test -Dtest=TestDataDemoTest#testJsonDataLoading
mvn test -Dtest=TestDataDemoTest#testCsvDataLoading
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
