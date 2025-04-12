package io.github.mypixelquest.pja.listeners;

import com.microsoft.playwright.Page;
import io.github.mypixelquest.pja.base.BaseTest;
import io.qameta.allure.Allure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;
import java.util.Optional;

/**
 * TestNG listener that captures screenshots on test failures
 * and attaches them to the Allure report
 */
public class ScreenshotListener implements ITestListener {
    private static final Logger log = LoggerFactory.getLogger(ScreenshotListener.class);

    @Override
    public void onTestFailure(ITestResult result) {
        log.debug("Test failed: {}", result.getName());
        captureAndAttachScreenshot(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.debug("Test skipped: {}", result.getName());
        captureAndAttachScreenshot(result);
    }

    /**
     * Capture screenshot and attach to Allure report
     *
     * @param result TestNG ITestResult object
     */
    private void captureAndAttachScreenshot(ITestResult result) {
        try {
            // Get the test instance (should be a BaseTest)
            Object testInstance = result.getInstance();
            if (!(testInstance instanceof BaseTest)) {
                log.warn("Test instance is not a BaseTest, cannot take screenshot");
                return;
            }

            // Get current thread's Page object from BaseTest
            BaseTest baseTest = (BaseTest) testInstance;
            Optional<Page> pageOptional = baseTest.getCurrentPage();

            if (pageOptional.isPresent()) {
                Page page = pageOptional.get();
                log.info("Taking screenshot for failed test: {}", result.getName());

                // Take screenshot with Playwright
                byte[] screenshotBytes = page.screenshot(
                        new Page.ScreenshotOptions().setFullPage(true));

                // Attach screenshot to Allure report
                Allure.addAttachment(
                        "Screenshot on Failure - " + result.getName(),
                        "image/png",
                        new ByteArrayInputStream(screenshotBytes),
                        "png");
            } else {
                log.warn("No active Page object found, cannot take screenshot");
            }
        } catch (Exception e) {
            log.error("Failed to capture screenshot", e);
        }
    }
}