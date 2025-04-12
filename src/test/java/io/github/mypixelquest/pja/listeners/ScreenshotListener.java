package io.github.mypixelquest.pja.listeners;

import com.microsoft.playwright.Page;
import io.github.mypixelquest.pja.base.BaseTest;
import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class ScreenshotListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        // No action needed
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.debug("Test failed: {}", result.getName());
        takeScreenshot(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.debug("Test skipped: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // No action needed
    }

    private void takeScreenshot(ITestResult result) {
        try {
            Object instance = result.getInstance();
            if (!(instance instanceof BaseTest)) {
                log.warn("Test instance is not a BaseTest, cannot take screenshot");
                return;
            }

            BaseTest test = (BaseTest) instance;
            test.getCurrentPage().ifPresentOrElse(
                page -> captureAndAttachScreenshot(page, result),
                () -> log.warn("No active page found to capture screenshot")
            );
        } catch (Exception e) {
            log.error("Failed to take screenshot", e);
        }
    }

    private void captureAndAttachScreenshot(Page page, ITestResult result) {
        try {
            String testName = result.getName();
            log.info("Taking screenshot for test: {}", testName);
            
            // Ensure screenshots directory exists
            Path screenshotsDir = Paths.get("target/screenshots");
            Files.createDirectories(screenshotsDir);
            
            // Take screenshot
            byte[] screenshot = page.screenshot(new Page.ScreenshotOptions()
                .setPath(screenshotsDir.resolve(testName + "_" + System.currentTimeMillis() + ".png"))
                .setFullPage(true));

            // Attach to Allure report
            Allure.addAttachment(
                testName + "_failure",
                "image/png",
                new ByteArrayInputStream(screenshot),
                "png"
            );
        } catch (Exception e) {
            log.error("Failed to capture or attach screenshot for test: {}", result.getName(), e);
        }
    }
}