package io.github.mypixelquest.pja.tests;

import io.github.mypixelquest.pja.base.BaseTest;
import io.github.mypixelquest.pja.pages.ExamplePage;
import io.qameta.allure.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for Playwright.dev website using ExamplePage
 */
@Epic("Playwright Java Accelerator")
@Feature("Example Tests")
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
            assertThat(examplePage.isLoaded())
                    .as("Homepage should be loaded")
                    .isTrue();
            
            // Click Get Started button
            examplePage.clickGetStarted();
            
            // Verify URL contains docs/intro
            assertThat(examplePage.getCurrentUrl())
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
            assertThat(examplePage.getCurrentUrl())
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
            
            // Verify search dialog is visible using the page object method
            assertThat(examplePage.isSearchModalVisible())
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
            assertThat(examplePage.getCurrentUrl())
                    .as("URL should contain codegen")
                    .contains("codegen");
            
            // Navigate back to homepage
            examplePage.navigate();
            
            // Navigate to Trace Viewer
            examplePage.navigateToTool("trace-viewer");
            
            // Verify URL contains trace-viewer
            assertThat(examplePage.getCurrentUrl())
                    .as("URL should contain trace-viewer")
                    .contains("trace-viewer");
        });
    }

    /**
     * Test to demonstrate retry functionality
     * This test will fail on first attempt due to timing, but should pass on retry
     */
    @Test(description = "Demonstrate retry functionality", enabled = false)
    @Description("Test that demonstrates retry mechanism by having an intentional timing issue")
    @Severity(SeverityLevel.NORMAL)
    @Story("Retry Mechanism")
    public void testRetryFunctionality() {
        log.info("Running test: Retry functionality demonstration");
        
        getCurrentPage().ifPresent(page -> {
            ExamplePage examplePage = new ExamplePage(page);
            examplePage.navigate();
            
            // Click search without waiting for animation
            examplePage.openSearch();
            
            // Try to verify search dialog immediately (likely to fail first time)
            assertThat(examplePage.isSearchModalVisible())
                    .as("Search modal should be visible immediately")
                    .isTrue();
        });
    }

    /**
     * Test to demonstrate screenshot capture on failure
     * This test will always fail but should generate a screenshot
     */
    @Test(description = "Screenshot on failure demonstration", enabled = false)
    @Story("Screenshot Capture")
    @Description("Demonstrates screenshot capture on test failure")
    @Severity(SeverityLevel.NORMAL)
    public void testScreenshotOnFailure() {
        log.info("Running test: Screenshot on failure demonstration");
        getCurrentPage().ifPresent(page -> {
            page.navigate("https://playwright.dev");
            assertThat(page.url())
                .as("URL should contain an impossible value")
                .contains("this-will-never-exist");
        });
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        if (!result.isSuccess()) {
            getCurrentPage().ifPresent(page -> {
                captureScreenshot(page.screenshot());
            });
        }
    }

    @Attachment(value = "Page Screenshot", type = "image/png")
    private byte[] captureScreenshot(byte[] screenshot) {
        return screenshot;
    }
}