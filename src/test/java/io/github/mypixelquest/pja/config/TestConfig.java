package io.github.mypixelquest.pja.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Configuration class for test settings
 */
@Slf4j
@Getter
public class TestConfig {
    private static TestConfig instance;
    private final String environment;
    private final String baseUrl;
    private final int timeoutSeconds;

    private TestConfig() {
        // Default values
        this.environment = System.getProperty("env", "qa");
        this.baseUrl = System.getProperty("baseUrl", "https://example.com");
        this.timeoutSeconds = Integer.parseInt(System.getProperty("timeout", "30"));
        
        log.info("Test configuration initialized with environment: {}", environment);
    }

    public static TestConfig getInstance() {
        if (instance == null) {
            instance = new TestConfig();
        }
        return instance;
    }
} 