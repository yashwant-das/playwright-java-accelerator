package io.github.mypixelquest.pja.examples;

import io.github.mypixelquest.pja.core.PlaywrightTest;
import io.github.mypixelquest.pja.pages.PlaywrightDocsPage;
import io.qameta.allure.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for Playwright.dev website using PlaywrightDocsPage
 * Demonstrates basic navigation and interaction patterns
 */
@Epic("Playwright Java Accelerator")
@Feature("Playwright Documentation Navigation")
public class PlaywrightDocsNavigationTest extends PlaywrightTest {
    private static final Logger log = LoggerFactory.getLogger(PlaywrightDocsNavigationTest.class);

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
            PlaywrightDocsPage docsPage = new PlaywrightDocsPage(page);
            
            // Navigate to homepage
            docsPage.navigate();
            
            // Verify page loaded successfully
            assertThat(docsPage.isLoaded())
                    .as("Homepage should be loaded")
                    .isTrue();
            
            // Click Get Started button
            docsPage.clickGetStarted();
            
            // Verify URL contains docs/intro
            assertThat(docsPage.getCurrentUrl())
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
            PlaywrightDocsPage docsPage = new PlaywrightDocsPage(page);
            
            // Navigate to homepage
            docsPage.navigate();
            
            // Navigate to Java documentation
            docsPage.navigateToLanguage("java");
            
            // Verify URL contains java
            assertThat(docsPage.getCurrentUrl())
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
            PlaywrightDocsPage docsPage = new PlaywrightDocsPage(page);
            
            // Navigate to homepage
            docsPage.navigate();
            
            // Open search dialog
            docsPage.openSearch();
            
            // Verify search dialog is visible using the page object method
            assertThat(docsPage.isSearchModalVisible())
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
            PlaywrightDocsPage docsPage = new PlaywrightDocsPage(page);
            
            // Navigate to homepage
            docsPage.navigate();
            
            // Navigate to Codegen tool
            docsPage.navigateToTool("codegen");
            
            // Verify URL contains codegen
            assertThat(docsPage.getCurrentUrl())
                    .as("URL should contain codegen")
                    .contains("codegen");
            
            // Navigate back to homepage
            docsPage.navigate();
            
            // Navigate to Trace Viewer
            docsPage.navigateToTool("trace-viewer");
            
            // Verify URL contains trace-viewer
            assertThat(docsPage.getCurrentUrl())
                    .as("URL should contain trace-viewer")
                    .contains("trace-viewer");
        });
    }
}

