# Playwright Java Accelerator

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
                HomePage.java         # Example page implementation
              tests/                  # TestNG test classes
                SampleTest.java       # Example test implementation
              utils/                  # Helper utilities
                ConfigReader.java     # Configuration loader
    resources/
      config/                         # Configuration files
        qa.yaml                       # QA environment config
      logback.xml                     # Logging configuration
      suites/                         # TestNG XML suite files
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

## Configuration

The framework uses YAML configuration files located in `src/test/resources/config/`.

Example configuration (qa.yaml):
```yaml
environment:
  name: qa
  baseUrl: https://qa.example.com

browser:
  type: chromium  # chromium, firefox, or webkit
  headless: true
  slowMo: 0       # milliseconds to wait between actions
  timeout: 30000  # default timeout in milliseconds
  
screenshot:
  takeOnFailure: true
  fullPage: true
```

## Creating Tests

### 1. Create a Page Object

```java
public class LoginPage extends BasePage {
    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator loginButton;
    
    public LoginPage(Page page) {
        super(page);
        this.usernameInput = page.locator("#username");
        this.passwordInput = page.locator("#password");
        this.loginButton = page.locator("#login");
    }
    
    @Step("Login with username: {username}")
    public DashboardPage login(String username, String password) {
        log.info("Logging in with username: {}", username);
        usernameInput.fill(username);
        passwordInput.fill(password);
        loginButton.click();
        page.waitForLoadState();
        return new DashboardPage(page);
    }
}
```

### 2. Create a Test Class

```java
@Epic("Authentication")
@Feature("Login")
public class LoginTests extends BaseTest {
    
    @Test(description = "Verify successful login")
    @Description("Tests that a user can successfully log in with valid credentials")
    @Severity(SeverityLevel.CRITICAL)
    public void verifySuccessfulLogin() {
        getCurrentPage().ifPresent(page -> {
            // Create page objects
            LoginPage loginPage = new LoginPage(page);
            
            // Perform actions
            DashboardPage dashboardPage = loginPage.login("testuser", "password123");
            
            // Assertions
            assertThat(dashboardPage.getUsernameDisplay())
                .as("Username should be displayed after login")
                .isEqualTo("testuser");
        });
    }
}
```

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License - see the LICENSE file for details.
