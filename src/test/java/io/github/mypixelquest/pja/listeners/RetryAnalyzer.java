package io.github.mypixelquest.pja.listeners;

import io.github.mypixelquest.pja.utils.ConfigReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * TestNG retry analyzer that handles test retries based on configuration
 */
public class RetryAnalyzer implements IRetryAnalyzer {
    private static final Logger log = LoggerFactory.getLogger(RetryAnalyzer.class);
    private final ConfigReader configReader = ConfigReader.getInstance();
    private int retryCount = 0;

    @Override
    public boolean retry(ITestResult result) {
        var retryConfig = configReader.getConfig().getRetry();
        if (retryConfig == null || !retryConfig.isEnabled()) {
            return false;
        }

        if (retryCount < retryConfig.getMaxRetries()) {
            retryCount++;
            log.info("Retrying test '{}' for the {} time", result.getName(), retryCount);
            
            // Wait between retries if configured
            if (retryConfig.getDelayBetweenRetries() > 0) {
                try {
                    Thread.sleep(retryConfig.getDelayBetweenRetries());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.warn("Retry delay was interrupted", e);
                }
            }
            
            return true;
        }
        
        return false;
    }
}