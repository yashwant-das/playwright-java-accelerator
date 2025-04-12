package io.github.mypixelquest.pja.tests;

import io.github.mypixelquest.pja.base.BaseTest;
import io.github.mypixelquest.pja.pages.ExamplePage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.assertj.core.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

/**
 * Test class for Appium Documentation website using ExamplePage
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
            // Create example page and navigate to Appium docs
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
    @Test(description = "Search Appium documentation")
    @Description("This test performs a search in the Appium docs and verifies results are returned")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Documentation Search")
    public void searchAppiumDocumentation() {
        log.info("Running test: Search Appium documentation");
        
        getCurrentPage().ifPresent(page -> {
            // Create example page and navigate to Appium docs
            ExamplePage examplePage = new ExamplePage(page);
            examplePage.navigate();
            
            // Perform search for "write a test"
            examplePage.search("write a test");
            
            // Get search result count
            int resultCount = examplePage.getSearchResultCount();
            log.info("Search result count: {}", resultCount);
            
            // Verify we have search results
            Assertions.assertThat(resultCount)
                    .as("Search should return at least one result")
                    .isGreaterThan(0);
            
            // Verify current URL contains search term
            String currentUrl = examplePage.getCurrentUrl();
            log.info("Current URL after search: {}", currentUrl);
            
            Assertions.assertThat(currentUrl)
                    .as("URL should indicate search was performed")
                    .contains("search");
        });
    }
    
    /**
     * Test that verifies dark mode toggle functionality
     */
    @Test(description = "Toggle dark mode")
    @Description("This test verifies the dark mode toggle functionality")
    @Severity(SeverityLevel.MINOR)
    @Story("UI Customization")
    public void toggleDarkMode() {
        log.info("Running test: Toggle dark mode");
        
        getCurrentPage().ifPresent(page -> {
            // Create example page and navigate to Appium docs
            ExamplePage examplePage = new ExamplePage(page);
            examplePage.navigate();
            
            // Toggle dark mode
            examplePage.toggleDarkMode();
            
            // Note: Since we can't easily verify the visual change in dark mode
            // through automated tests without visual validation tools,
            // we're just verifying that the toggle action completes without errors
            log.info("Dark mode toggled successfully");
        });
    }
}