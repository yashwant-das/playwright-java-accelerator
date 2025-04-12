package io.github.mypixelquest.pja.config;

import lombok.Data;

/**
 * Configuration model class that maps to the YAML configuration structure
 */
@Data
public class ConfigModel {
    private EnvironmentConfig environment;
    private BrowserConfig browser;
    private ScreenshotConfig screenshot;
    private TestExecutionConfig testExecution;
    private RetryConfig retry;
    
    @Data
    public static class EnvironmentConfig {
        private String name;
        private String baseUrl;
    }
    
    @Data
    public static class BrowserConfig {
        private String type;
        private boolean headless;
        private int slowMo;
        private int timeout;
    }
    
    @Data
    public static class ScreenshotConfig {
        private boolean takeOnFailure;
        private boolean fullPage;
    }

    @Data
    public static class TestExecutionConfig {
        private boolean parallel;
        private int threadCount;
    }

    @Data
    public static class RetryConfig {
        private boolean enabled;
        private int maxRetries;
        private long delayBetweenRetries; // in milliseconds
    }
}