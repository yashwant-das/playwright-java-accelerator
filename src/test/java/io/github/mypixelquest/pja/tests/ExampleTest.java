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
 * Test class for Playwright.dev website using ExamplePage
 */
@Epic("Playwright Website Tests")
@Feature("Basic Website Navigation")
public class ExampleTest extends BaseTest {
    private static final Logger log = LoggerFactory.getLogger(ExampleTest.class);

    /**
     * Test 1: Verify basic navigation to the homepage and Get Started functionality
     */
    @Test(description = "Verify homepage navigation and Get Started button")
    @Description("Navigates to the homepage and clicks the Get Started button")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Homepage Navigation")
    public void testHomePageNavigation() {
        log.info("Running test: Homepage navigation");
        
        getCurrentPage().ifPresent(page -> {
            ExamplePage examplePage = new ExamplePage(page);
            
            // Navigate to homepage
            examplePage.navigate();
            
            // Verify page loaded successfully
            Assertions.assertThat(examplePage.isLoaded())
                    .as("Homepage should be loaded")
                    .isTrue();
            
            // Click Get Started button
            examplePage.clickGetStarted();
            
            // Verify URL contains docs/intro
            Assertions.assertThat(examplePage.getCurrentUrl())
                    .as("URL should contain docs/intro after clicking Get Started")
                    .contains("docs/intro");
        });
    }

    /**
     * Test 2: Verify Java documentation navigation
     */
    @Test(description = "Navigate to Java documentation")
    @Description("Navigates to Java-specific documentation and verifies the URL")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Language Documentation")
    public void testJavaDocumentation() {
        log.info("Running test: Java documentation navigation");
        
        getCurrentPage().ifPresent(page -> {
            ExamplePage examplePage = new ExamplePage(page);
            
            // Navigate to homepage
            examplePage.navigate();
            
            // Navigate to Java documentation
            examplePage.navigateToLanguage("java");
            
            // Verify URL contains java
            Assertions.assertThat(examplePage.getCurrentUrl())
                    .as("URL should contain java")
                    .contains("/java");
        });
    }

    /**
     * Test 3: Verify search functionality
     */
    @Test(description = "Verify search functionality")
    @Description("Opens search dialog and verifies it's visible")
    @Severity(SeverityLevel.NORMAL)
    @Story("Search Functionality")
    public void testSearch() {
        log.info("Running test: Search functionality");
        
        getCurrentPage().ifPresent(page -> {
            ExamplePage examplePage = new ExamplePage(page);
            
            // Navigate to homepage
            examplePage.navigate();
            
            // Open search dialog
            examplePage.openSearch();
            
            // Verify search dialog is visible (using page reference directly for this check)
            boolean isSearchVisible = page.locator("div.DocSearch-Modal").isVisible();
            Assertions.assertThat(isSearchVisible)
                    .as("Search modal should be visible")
                    .isTrue();
        });
    }

    /**
     * Test 4: Verify Playwright tools navigation
     */
    @Test(description = "Navigate to Playwright tools")
    @Description("Navigates to various Playwright tools (Codegen, Inspector, Trace Viewer)")
    @Severity(SeverityLevel.NORMAL)
    @Story("Tools Navigation")
    public void testToolsNavigation() {
        log.info("Running test: Tools navigation");
        
        getCurrentPage().ifPresent(page -> {
            ExamplePage examplePage = new ExamplePage(page);
            
            // Navigate to homepage
            examplePage.navigate();
            
            // Navigate to Codegen tool
            examplePage.navigateToTool("codegen");
            
            // Verify URL contains codegen
            Assertions.assertThat(examplePage.getCurrentUrl())
                    .as("URL should contain codegen")
                    .contains("codegen");
            
            // Navigate back to homepage
            examplePage.navigate();
            
            // Navigate to Trace Viewer
            examplePage.navigateToTool("trace-viewer");
            
            // Verify URL contains trace-viewer
            Assertions.assertThat(examplePage.getCurrentUrl())
                    .as("URL should contain trace-viewer")
                    .contains("trace-viewer");
        });
    }
}