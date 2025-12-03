package io.github.mypixelquest.pja.examples;

import io.github.mypixelquest.pja.core.PlaywrightTest;
import io.github.mypixelquest.pja.data.TestDataManager;
import io.qameta.allure.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Demonstrates data-driven testing capabilities using YAML, JSON, and CSV data sources
 */
@Epic("Framework Capabilities")
@Feature("Data-Driven Testing")
public class DataDrivenTest extends PlaywrightTest {
    private static final Logger log = LoggerFactory.getLogger(DataDrivenTest.class);
    private TestDataManager dataManager;

    @BeforeClass
    public void setupDataManager() {
        dataManager = new TestDataManager("qa");
    }

    @Test(description = "Data-driven test using YAML data")
    @Description("Loads test data from YAML and uses it in test")
    @Severity(SeverityLevel.NORMAL)
    @Story("Data-Driven Testing")
    @SuppressWarnings("unchecked")
    public void testWithYamlData() {
        log.info("Running data-driven test with YAML data");
        
        Map<String, Object> data = dataManager.loadYamlData("test-data.yaml");
        String adminUsername = (String) dataManager.getValue(data, "users.admin.username");
        String adminRole = (String) dataManager.getValue(data, "users.admin.role");
        
        log.info("Testing with user: {} (Role: {})", adminUsername, adminRole);
        
        getCurrentPage().ifPresent(page -> {
            page.navigate("https://playwright.dev");
            assertThat(page.title()).isNotEmpty();
        });
        
        assertThat(adminUsername).isEqualTo("admin@example.com");
        assertThat(adminRole).isEqualTo("ADMIN");
    }

    @Test(description = "Data-driven test using JSON data")
    @Description("Loads test data from JSON and uses it in test")
    @Severity(SeverityLevel.NORMAL)
    @Story("Data-Driven Testing")
    @SuppressWarnings("unchecked")
    public void testWithJsonData() {
        log.info("Running data-driven test with JSON data");
        
        Map<String, Object> data = dataManager.loadJsonData("orders.json");
        List<Map<String, Object>> orders = (List<Map<String, Object>>) data.get("orders");
        
        assertThat(orders).isNotEmpty();
        
        Map<String, Object> firstOrder = orders.get(0);
        String orderId = (String) firstOrder.get("id");
        double total = (double) firstOrder.get("total");
        
        log.info("Testing with order: {} (Total: ${})", orderId, total);
        
        getCurrentPage().ifPresent(page -> {
            page.navigate("https://playwright.dev");
            assertThat(page.url()).contains("playwright");
        });
        
        assertThat(orderId).isEqualTo("ORD-001");
        assertThat(total).isEqualTo(1299.99);
    }

    @Test(description = "Data-driven test using CSV data")
    @Description("Loads test data from CSV and uses it in test")
    @Severity(SeverityLevel.NORMAL)
    @Story("Data-Driven Testing")
    @SuppressWarnings("unchecked")
    public void testWithCsvData() {
        log.info("Running data-driven test with CSV data");
        
        Map<String, Object> data = dataManager.loadCsvData("products.csv");
        List<Map<String, Object>> products = (List<Map<String, Object>>) data.get("data");
        
        assertThat(products).isNotEmpty();
        
        Map<String, Object> firstProduct = products.get(0);
        String productId = (String) firstProduct.get("id");
        String productName = (String) firstProduct.get("name");
        
        log.info("Testing with product: {} - {}", productId, productName);
        
        getCurrentPage().ifPresent(page -> {
            page.navigate("https://playwright.dev");
            assertThat(page.title()).isNotEmpty();
        });
        
        assertThat(productId).isEqualTo("PROD-001");
        assertThat(productName).isEqualTo("Premium Laptop");
    }
}

