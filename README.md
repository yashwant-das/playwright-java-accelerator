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

## Configuration

The framework uses YAML configuration files located in `src/test/resources/config/`.

Example configuration (qa.yaml):
```yaml
environment:
  name: qa
  baseUrl: https://appium.io/docs/en/latest/

browser:
  type: chromium  # chromium, firefox, or webkit
  headless: true
  slowMo: 0       # milliseconds to wait between actions
  timeout: 30000  # default timeout in milliseconds
  
screenshot:
  takeOnFailure: true
  fullPage: true
```

## Included Test Examples

The framework includes a comprehensive example test:

### Example Website Tests

The `ExampleTest` class demonstrates how to interact with the Appium documentation website:
- Navigation to specific sections of the documentation
- Searching for content using the search functionality
- Testing the dark mode toggle functionality
- Verifying documentation titles and search results

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
    // URL for the page
    private static final String EXAMPLE_URL = "https://appium.io/docs/en/latest/";
    
    // Locators
    private final Locator searchButton;
    private final Locator searchInput;
    private final Locator navigationLinks;
    private final Locator quickstartLink;
    private final Locator darkModeButton;
    
    /**
     * Constructor for ExamplePage
     *
     * @param page Playwright Page object
     */
    public ExamplePage(Page page) {
        super(page);
        this.searchButton = page.locator("button[class*='md-search__icon']").first();
        this.searchInput = page.locator("input[placeholder='Search']");
        this.navigationLinks = page.locator("nav.md-nav--primary a.md-nav__link");
        this.quickstartLink = page.locator("a:has-text('Quickstart')");
        this.darkModeButton = page.locator("button[data-md-color-scheme]");
    }
    
    /**
     * Navigate to the example page
     * 
     * @return ExamplePage instance for method chaining
     */
    @Step("Navigate to example page")
    public ExamplePage navigate() {
        log.info("Navigating to example page: {}", EXAMPLE_URL);
        page.navigate(EXAMPLE_URL);
        page.waitForLoadState();
        return this;
    }
    
    /**
     * Search for a given term
     *
     * @param searchTerm The term to search for
     * @return ExamplePage instance for method chaining
     */
    @Step("Search for: {searchTerm}")
    public ExamplePage search(String searchTerm) {
        log.info("Searching for: {}", searchTerm);
        searchButton.click();
        searchInput.fill(searchTerm);
        searchInput.press("Enter");
        page.waitForLoadState();
        return this;
    }
    
    /**
     * Navigate to specific section in documentation
     *
     * @param sectionName The name of the section to navigate to
     * @return ExamplePage instance for method chaining
     */
    @Step("Navigate to section: {sectionName}")
    public ExamplePage navigateToSection(String sectionName) {
        log.info("Navigating to section: {}", sectionName);
        page.locator("a:has-text('" + sectionName + "')").first().click();
        page.waitForLoadState();
        return this;
    }
    
    /**
     * Toggle between dark and light mode
     * 
     * @return ExamplePage instance for method chaining
     */
    @Step("Toggle dark/light mode")
    public ExamplePage toggleDarkMode() {
        log.info("Toggling dark/light mode");
        darkModeButton.click();
        return this;
    }
    
    /**
     * Get the current documentation page title
     *
     * @return The title of the current page
     */
    @Step("Get documentation page title")
    public String getDocumentationTitle() {
        String title = page.locator("article h1").textContent();
        log.info("Documentation title: {}", title);
        return title;
    }
    
    /**
     * Check if the page is loaded
     *
     * @return true if page is loaded, false otherwise
     */
    @Step("Check if page is loaded")
    public boolean isLoaded() {
        log.debug("Checking if page is loaded");
        return page.url().contains("appium.io/docs") && 
               page.title().contains("Appium");
    }
}
```

### 2. Create a Test Class

```java
/**
 * Test class for Appium Documentation website
 */
@Epic("Documentation Tests")
@Feature("Appium Documentation")
public class ExampleTest extends BaseTest {
    private static final Logger log = LoggerFactory.getLogger(ExampleTest.class);

    /**
     * Test that verifies navigation to the Quickstart section
     */
    @Test(description = "Navigate to Quickstart section")
    @Description("This test navigates to the Quickstart section and verifies the title")
    @Severity(SeverityLevel.NORMAL)
    @Story("Documentation Navigation")
    public void navigateToQuickstartSection() {
        log.info("Running test: Navigate to Quickstart section");
        
        getCurrentPage().ifPresent(page -> {
            // Create example page and navigate
            ExamplePage examplePage = new ExamplePage(page);
            examplePage.navigate();
            
            // Verify page loaded correctly
            Assertions.assertThat(examplePage.isLoaded())
                    .as("Appium documentation page should be loaded")
                    .isTrue();
            
            // Navigate to Quickstart section
            examplePage.navigateToSection("Quickstart");
            
            // Get the documentation title and verify it
            String sectionTitle = examplePage.getDocumentationTitle();
            log.info("Section title: {}", sectionTitle);
            
            // Verify the section title contains "Quickstart"
            Assertions.assertThat(sectionTitle)
                    .as("Section title should contain 'Quickstart'")
                    .containsIgnoringCase("quickstart");
        });
    }

    /**
     * Test that searches for specific documentation content
     */
    @Test(description = "Search documentation")
    @Description("This test performs a search and verifies results are returned")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Documentation Search")
    public void searchAppiumDocumentation() {
        log.info("Running test: Search documentation");
        
        getCurrentPage().ifPresent(page -> {
            // Create example page and navigate
            ExamplePage examplePage = new ExamplePage(page);
            examplePage.navigate();
            
            // Perform search
            examplePage.search("write a test");
            
            // Verify search results
            Assertions.assertThat(examplePage.getSearchResultCount())
                    .as("Search should return at least one result")
                    .isGreaterThan(0);
        });
    }
}
```

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License - see the LICENSE file for details.
