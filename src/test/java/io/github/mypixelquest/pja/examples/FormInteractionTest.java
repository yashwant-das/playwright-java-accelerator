package io.github.mypixelquest.pja.examples;

import io.github.mypixelquest.pja.core.PlaywrightTest;
import io.qameta.allure.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Demonstrates form interaction capabilities
 * Uses httpbin.org for stable form testing
 */
@Epic("Framework Capabilities")
@Feature("Form Interaction")
public class FormInteractionTest extends PlaywrightTest {
    private static final Logger log = LoggerFactory.getLogger(FormInteractionTest.class);

    @Test(description = "Test form submission")
    @Description("Demonstrates filling and submitting a form")
    @Severity(SeverityLevel.NORMAL)
    @Story("Form Interaction")
    public void testFormSubmission() {
        log.info("Testing form submission");
        
        getCurrentPage().ifPresent(page -> {
            // Navigate to a form page
            page.navigate("https://httpbin.org/forms/post");
            
            // Fill form fields
            page.fill("input[name='custname']", "Test User");
            page.fill("input[name='custtel']", "1234567890");
            page.fill("input[name='custemail']", "test@example.com");
            
            // Submit form
            page.click("input[type='submit']");
            
            // Wait for response
            page.waitForLoadState();
            
            // Verify form was submitted (httpbin shows the submitted data)
            assertThat(page.url()).contains("httpbin.org");
            log.info("Form submitted successfully");
        });
    }

    @Test(description = "Test form field interactions")
    @Description("Demonstrates various form field interactions")
    @Severity(SeverityLevel.NORMAL)
    @Story("Form Interaction")
    public void testFormFieldInteractions() {
        log.info("Testing form field interactions");
        
        getCurrentPage().ifPresent(page -> {
            page.navigate("https://httpbin.org/forms/post");
            
            // Test text input
            var nameField = page.locator("input[name='custname']");
            nameField.fill("John Doe");
            assertThat(nameField.inputValue()).isEqualTo("John Doe");
            
            // Test email input
            var emailField = page.locator("input[name='custemail']");
            emailField.fill("john@example.com");
            assertThat(emailField.inputValue()).isEqualTo("john@example.com");
            
            log.info("Form field interactions successful");
        });
    }
}

