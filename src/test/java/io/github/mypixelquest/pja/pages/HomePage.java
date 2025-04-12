package io.github.mypixelquest.pja.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

/**
 * Sample Page Object for a Home Page
 */
public class HomePage extends BasePage {
    // Locators
    private final Locator searchInput;
    private final Locator searchButton;
    
    /**
     * Constructor for HomePage
     *
     * @param page Playwright Page object
     */
    public HomePage(Page page) {
        super(page);
        // These are sample locators - you would replace with actual selectors
        this.searchInput = page.locator("#search-input");
        this.searchButton = page.locator("#search-button");
    }
    
    /**
     * Search for a given term
     *
     * @param searchTerm The term to search for
     * @return HomePage instance for method chaining
     */
    @Step("Search for: {searchTerm}")
    public HomePage search(String searchTerm) {
        log.info("Searching for: {}", searchTerm);
        searchInput.fill(searchTerm);
        searchButton.click();
        page.waitForLoadState();
        return this;
    }
    
    /**
     * Check if the page is loaded
     *
     * @return true if page is loaded, false otherwise
     */
    @Step("Check if homepage is loaded")
    public boolean isLoaded() {
        log.debug("Checking if homepage is loaded");
        return page.url().contains("example.com");  // Replace with actual URL check
    }
}