package io.github.mypixelquest.pja.tests;

import io.github.mypixelquest.pja.base.BaseTest;
import io.github.mypixelquest.pja.pages.HomePage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.assertj.core.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

/**
 * Sample test class demonstrating how to use the framework
 */
@Epic("Sample Tests")
@Feature("Basic Functionality")
public class SampleTest extends BaseTest {
    private static final Logger log = LoggerFactory.getLogger(SampleTest.class);

    /**
     * Sample test that demonstrates how to use Page Objects and assertions
     */
    @Test(description = "Verify page title")
    @Description("This test verifies that the homepage title contains the expected value")
    @Severity(SeverityLevel.NORMAL)
    public void verifyPageTitle() {
        log.info("Running sample test: Verify page title");
        
        // Get the Page instance from BaseTest (via Optional)
        getCurrentPage().ifPresent(page -> {
            // Create a Page Object
            HomePage homePage = new HomePage(page);
            
            // Perform actions and assertions
            String pageTitle = homePage.getTitle();
            log.info("Page title: {}", pageTitle);
            
            // Using AssertJ for assertions
            Assertions.assertThat(pageTitle)
                    .as("Page title should contain 'Example Domain'")
                    .contains("Example Domain");
        });
    }

    /**
     * Sample test that demonstrates how to perform a search
     */
    @Test(description = "Perform search")
    @Description("This test performs a search and verifies the URL changes")
    @Severity(SeverityLevel.CRITICAL)
    public void performSearch() {
        log.info("Running sample test: Perform search");
        
        getCurrentPage().ifPresent(page -> {
            // Create a Page Object
            HomePage homePage = new HomePage(page);
            
            // Perform search
            homePage.search("playwright java");
            
            // Get the URL after search
            String currentUrl = homePage.getCurrentUrl();
            log.info("Current URL after search: {}", currentUrl);
            
            // Using AssertJ for assertions
            Assertions.assertThat(currentUrl)
                    .as("URL should contain search term")
                    .contains("search");
        });
    }
}