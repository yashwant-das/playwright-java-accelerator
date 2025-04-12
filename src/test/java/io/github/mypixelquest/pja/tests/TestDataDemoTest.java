package io.github.mypixelquest.pja.tests;

import io.github.mypixelquest.pja.data.TestDataGenerator;
import io.github.mypixelquest.pja.data.TestDataManager;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Demonstrates the test data management capabilities of the framework
 */
@Slf4j
public class TestDataDemoTest {
    private final TestDataManager dataManager;

    public TestDataDemoTest() {
        this.dataManager = new TestDataManager("qa");
    }

    @Test(description = "Demonstrate YAML data loading")
    @Description("Loads and uses data from YAML files")
    @Severity(SeverityLevel.NORMAL)
    @Story("Test Data Management")
    public void testYamlDataLoading() {
        log.info("Loading YAML test data");
        Map<String, Object> data = dataManager.loadYamlData("test-data.yaml");

        // Get user data
        String adminUsername = (String) dataManager.getValue(data, "users.admin.username");
        String adminPassword = (String) dataManager.getValue(data, "users.admin.password");
        String adminRole = (String) dataManager.getValue(data, "users.admin.role");

        log.info("Admin user: {} (Role: {}, Password length: {})", adminUsername, adminRole, adminPassword.length());
        assertThat(adminUsername).isEqualTo("admin@example.com");
        assertThat(adminRole).isEqualTo("ADMIN");
        assertThat(adminPassword).isNotEmpty();

        // Get product data
        String laptopName = (String) dataManager.getValue(data, "products.laptop.name");
        double laptopPrice = (double) dataManager.getValue(data, "products.laptop.price");
        String laptopProcessor = (String) dataManager.getValue(data, "products.laptop.specs.processor");

        log.info("Product: {} (Price: ${}, Processor: {})", laptopName, laptopPrice, laptopProcessor);
        assertThat(laptopName).isEqualTo("Premium Laptop");
        assertThat(laptopPrice).isEqualTo(1299.99);
    }

    @Test(description = "Demonstrate JSON data loading")
    @Description("Loads and uses data from JSON files")
    @Severity(SeverityLevel.NORMAL)
    @Story("Test Data Management")
    @SuppressWarnings("unchecked")
    public void testJsonDataLoading() {
        log.info("Loading JSON test data");
        Map<String, Object> data = dataManager.loadJsonData("orders.json");

        // Get order data
        Map<String, Object> firstOrder = (Map<String, Object>) ((java.util.List<?>) data.get("orders")).get(0);
        String orderId = (String) firstOrder.get("id");
        double orderTotal = (double) firstOrder.get("total");
        String orderStatus = (String) firstOrder.get("status");

        log.info("Order: {} (Total: ${}, Status: {})", orderId, orderTotal, orderStatus);
        assertThat(orderId).isEqualTo("ORD-001");
        assertThat(orderTotal).isEqualTo(1299.99);
        assertThat(orderStatus).isEqualTo("PENDING");

        // Get customer data
        Map<String, Object> customer = (Map<String, Object>) firstOrder.get("customer");
        String customerName = (String) customer.get("name");
        String customerEmail = (String) customer.get("email");

        log.info("Customer: {} ({})", customerName, customerEmail);
        assertThat(customerName).isEqualTo("John Doe");
        assertThat(customerEmail).isEqualTo("john@example.com");
    }

    @Test(description = "Demonstrate CSV data loading")
    @Description("Loads and uses data from CSV files")
    @Severity(SeverityLevel.NORMAL)
    @Story("Test Data Management")
    @SuppressWarnings("unchecked")
    public void testCsvDataLoading() {
        log.info("Loading CSV test data");
        Map<String, Object> data = dataManager.loadCsvData("products.csv");

        // Get product data
        Map<String, Object> firstProduct = (Map<String, Object>) ((java.util.List<?>) data.get("data")).get(0);
        String productId = (String) firstProduct.get("id");
        String productName = (String) firstProduct.get("name");
        double productPrice = Double.parseDouble((String) firstProduct.get("price"));
        int stock = Integer.parseInt((String) firstProduct.get("stock"));

        log.info("Product: {} - {} (Price: ${}, Stock: {})", 
                productId, productName, productPrice, stock);
        assertThat(productId).isEqualTo("PROD-001");
        assertThat(productName).isEqualTo("Premium Laptop");
        assertThat(productPrice).isEqualTo(1299.99);
        assertThat(stock).isEqualTo(50);
    }

    @Test(description = "Demonstrate dynamic data generation")
    @Description("Generates and uses dynamic test data")
    @Severity(SeverityLevel.NORMAL)
    @Story("Test Data Management")
    public void testDynamicDataGeneration() {
        // Generate user data
        String email = TestDataGenerator.generateEmail();
        String password = TestDataGenerator.generatePassword(12, true);
        String phone = TestDataGenerator.generatePhoneNumber();
        String address = TestDataGenerator.generateAddress();

        log.info("Generated user data:");
        log.info("Email: {}", email);
        log.info("Password: {}", password);
        log.info("Phone: {}", phone);
        log.info("Address: {}", address);

        // Validate generated data
        assertThat(email).contains("@example.com");
        assertThat(password).hasSize(12);
        assertThat(phone).matches("\\+1\\d{10}");
        assertThat(address).matches("\\d+ [\\w\\s]+,\\s+[\\w\\s]+ \\d{5}");
    }

    @Test(description = "Demonstrate environment-specific data", enabled = false)
    @Description("Loads environment-specific test data")
    @Severity(SeverityLevel.NORMAL)
    @Story("Test Data Management")
    public void testEnvironmentSpecificData() {
        // Create a new data manager for production environment
        TestDataManager prodDataManager = new TestDataManager("prod");
        
        // Load the same data file but from different environment
        Map<String, Object> qaData = dataManager.loadYamlData("test-data.yaml");
        Map<String, Object> prodData = prodDataManager.loadYamlData("test-data.yaml");

        // Compare data between environments
        String qaAdminUsername = (String) dataManager.getValue(qaData, "users.admin.username");
        String prodAdminUsername = (String) dataManager.getValue(prodData, "users.admin.username");

        log.info("QA Admin: {}", qaAdminUsername);
        log.info("Prod Admin: {}", prodAdminUsername);

        // In a real scenario, these would be different
        assertThat(qaAdminUsername).isNotEqualTo(prodAdminUsername);
    }
}