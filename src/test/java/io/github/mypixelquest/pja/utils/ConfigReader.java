package io.github.mypixelquest.pja.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.github.mypixelquest.pja.config.ConfigModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * Singleton utility class for loading and accessing test configuration
 */
public class ConfigReader {
    private static final Logger log = LoggerFactory.getLogger(ConfigReader.class);
    private static ConfigReader instance;
    private ConfigModel config;
    
    private ConfigReader() {
        loadConfig();
    }
    
    /**
     * Get the singleton instance of ConfigReader
     * 
     * @return ConfigReader instance
     */
    public static synchronized ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }
    
    /**
     * Load configuration from the appropriate YAML file
     */
    private void loadConfig() {
        String environment = System.getProperty("environment", "qa");
        String configFile = String.format("/config/%s.yaml", environment);
        
        log.info("Loading configuration from {}", configFile);
        
        try (InputStream inputStream = getClass().getResourceAsStream(configFile)) {
            if (inputStream == null) {
                throw new IOException("Configuration file not found: " + configFile);
            }
            
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            config = mapper.readValue(inputStream, ConfigModel.class);
            log.info("Configuration loaded successfully for environment: {}", environment);
        } catch (IOException e) {
            log.error("Failed to load configuration", e);
            throw new RuntimeException("Failed to load configuration", e);
        }
    }
    
    /**
     * Get the loaded configuration
     * 
     * @return ConfigModel containing all configuration settings
     */
    public ConfigModel getConfig() {
        return config;
    }
    
    /**
     * Get base URL from configuration
     * 
     * @return Base URL string
     */
    public String getBaseUrl() {
        return Optional.ofNullable(config.getEnvironment().getBaseUrl())
                .orElseThrow(() -> new RuntimeException("Base URL not configured"));
    }
    
    /**
     * Get browser type from configuration
     * 
     * @return Browser type string (chromium, firefox, webkit)
     */
    public String getBrowserType() {
        return Optional.ofNullable(config.getBrowser().getType())
                .orElse("chromium");
    }
    
    /**
     * Check if browser should run in headless mode
     * 
     * @return True if headless, false otherwise
     */
    public boolean isHeadless() {
        return config.getBrowser().isHeadless();
    }
    
    /**
     * Get default timeout for Playwright actions
     * 
     * @return Timeout in milliseconds
     */
    public int getTimeout() {
        return config.getBrowser().getTimeout();
    }
}