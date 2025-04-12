package io.github.mypixelquest.pja.listeners;

import io.github.mypixelquest.pja.utils.ConfigReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IAlterSuiteListener;
import org.testng.xml.XmlSuite;

import java.util.List;

/**
 * TestNG listener that configures test parameters based on YAML configuration
 */
public class ConfigurationListener implements IAlterSuiteListener {
    private static final Logger log = LoggerFactory.getLogger(ConfigurationListener.class);
    private final ConfigReader configReader = ConfigReader.getInstance();

    @Override
    public void alter(List<XmlSuite> suites) {
        for (XmlSuite suite : suites) {
            configureSuiteParameters(suite);
        }
    }

    private void configureSuiteParameters(XmlSuite suite) {
        var browser = configReader.getConfig().getBrowser();
        var environment = configReader.getConfig().getEnvironment();

        // Set parameters from YAML configuration
        suite.getParameters().put("browser", browser.getType());
        suite.getParameters().put("headless", String.valueOf(browser.isHeadless()));
        suite.getParameters().put("environment", environment.getName());

        log.info("Configured suite parameters from YAML - browser: {}, headless: {}, environment: {}", 
                browser.getType(), browser.isHeadless(), environment.getName());
    }
}