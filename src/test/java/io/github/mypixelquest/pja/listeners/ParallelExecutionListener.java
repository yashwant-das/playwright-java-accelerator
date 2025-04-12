package io.github.mypixelquest.pja.listeners;

import io.github.mypixelquest.pja.utils.ConfigReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IAlterSuiteListener;
import org.testng.xml.XmlSuite;

import java.util.List;

/**
 * TestNG listener that configures parallel execution based on configuration settings
 */
public class ParallelExecutionListener implements IAlterSuiteListener {
    private static final Logger log = LoggerFactory.getLogger(ParallelExecutionListener.class);
    private final ConfigReader configReader = ConfigReader.getInstance();

    @Override
    public void alter(List<XmlSuite> suites) {
        for (XmlSuite suite : suites) {
            configureParallelExecution(suite);
        }
    }

    private void configureParallelExecution(XmlSuite suite) {
        var testExecution = configReader.getConfig().getTestExecution();
        
        if (testExecution != null && testExecution.isParallel()) {
            log.info("Enabling parallel execution with {} threads", testExecution.getThreadCount());
            suite.setParallel(XmlSuite.ParallelMode.METHODS);
            suite.setThreadCount(testExecution.getThreadCount());
        } else {
            log.info("Parallel execution is disabled");
            suite.setParallel(XmlSuite.ParallelMode.NONE);
        }
    }
}