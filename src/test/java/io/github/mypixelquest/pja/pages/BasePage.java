package io.github.mypixelquest.pja.pages;

import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for all Page Objects
 */
public abstract class BasePage {
    protected final Page page;
    protected final Logger log;
    
    /**
     * Constructor for BasePage
     *
     * @param page Playwright Page object
     */
    public BasePage(Page page) {
        this.page = page;
        this.log = LoggerFactory.getLogger(getClass());
        log.debug("Initializing {}", getClass().getSimpleName());
    }
    
    /**
     * Get the page title
     *
     * @return Page title
     */
    @Step("Get page title")
    public String getTitle() {
        String title = page.title();
        log.debug("Page title: {}", title);
        return title;
    }
    
    /**
     * Get the current URL
     *
     * @return Current URL
     */
    @Step("Get current URL")
    public String getCurrentUrl() {
        String url = page.url();
        log.debug("Current URL: {}", url);
        return url;
    }
    
    /**
     * Try to accept cookie consent dialog if it appears
     * This method attempts to locate and click common cookie consent buttons
     * 
     * @return true if cookies were accepted, false if no dialog was found
     */
    @Step("Accept cookie consent if present")
    public boolean acceptCookieConsentIfPresent() {
        log.debug("Attempting to accept cookie consent dialog if present");
        
        // Common cookie consent button selectors
        String[] consentSelectors = {
            "button[id*='cookie'][id*='accept']",
            "button[id*='cookie'][id*='agree']",
            "button[id*='consent'][id*='accept']",
            "button[id*='consent'][id*='agree']",
            "button[id*='cookie-consent']",
            "button[id*='accept-cookies']",
            "button[class*='cookie'][class*='accept']",
            "button[class*='cookie'][class*='agree']",
            "a[id*='cookie'][id*='accept']",
            "a[id*='cookie'][id*='agree']",
            "div[id*='cookie-banner'] button",
            "#cookieConsentAgree",
            "#acceptCookies",
            "button:has-text('Accept')",
            "button:has-text('Accept All')",
            "button:has-text('Accept Cookies')",
            "button:has-text('Allow')",
            "button:has-text('Allow All')",
            "button:has-text('I Agree')",
            "button:has-text('Agree')",
            "button:has-text('Got it')"
        };
        
        for (String selector : consentSelectors) {
            try {
                if (page.locator(selector).count() > 0) {
                    log.info("Found cookie consent button with selector: {}", selector);
                    page.locator(selector).first().click();
                    log.info("Cookie consent accepted");
                    return true;
                }
            } catch (Exception e) {
                log.debug("No cookie consent found for selector: {}", selector);
            }
        }
        
        log.debug("No cookie consent dialog found or it was already accepted");
        return false;
    }
    
    /**
     * Wait for page load state to be complete
     *
     * @return The page object for method chaining
     */
    @Step("Wait for page to load completely")
    public BasePage waitForPageLoad() {
        log.debug("Waiting for page to load completely");
        page.waitForLoadState();
        return this;
    }
}