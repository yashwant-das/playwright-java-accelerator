package io.github.mypixelquest.pja.listeners;

import io.github.mypixelquest.pja.util.ConfigReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TestNG retry analyzer that handles test retries based on configuration
 */
public class RetryAnalyzer implements IRetryAnalyzer {
    private static final Logger log = LoggerFactory.getLogger(RetryAnalyzer.class);
    private final ConfigReader configReader = ConfigReader.getInstance();
    // Track retry count per test to handle parallel execution correctly
    private final Map<ITestResult, Integer> retryCountMap = new ConcurrentHashMap<>();

    @Override
    public boolean retry(ITestResult result) {
        var retryConfig = configReader.getConfig().getRetry();
        if (retryConfig == null || !retryConfig.isEnabled()) {
            return false;
        }

        int retryCount = retryCountMap.getOrDefault(result, 0);
        if (retryCount < retryConfig.getMaxRetries()) {
            retryCount++;
            retryCountMap.put(result, retryCount);
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

        // Clean up after max retries reached
        retryCountMap.remove(result);
        return false;
    }
}