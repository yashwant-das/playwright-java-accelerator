package io.github.mypixelquest.pja.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

/**
 * Page Object for the Appium Documentation Home Page
 */
public class ExamplePage extends BasePage {
    // URL for the Appium documentation - updated to the latest version
    private static final String APPIUM_DOCS_URL = "https://appium.io/docs/en/2.17/";
    
    // Locators
    private final Locator searchButton;
    private final Locator searchInput;
    private final Locator navigationLinks;
    private final Locator quickstartLink;
    private final Locator darkModeButton;
    
    /**
     * Constructor for ExamplePage
     *
     * @param page Playwright Page object
     */
    public ExamplePage(Page page) {
        super(page);
        // Updated locators based on current website structure
        this.searchButton = page.locator("label.md-header__button[for='__search']");
        this.searchInput = page.locator("input.md-search__input");
        this.navigationLinks = page.locator("nav.md-nav--primary a.md-nav__link");
        this.quickstartLink = page.locator("a[href^='/docs/en/2.17/quickstart/']");
        this.darkModeButton = page.locator("form[data-md-component='palette'] input[name='__palette']").first();
    }
    
    /**
     * Navigate to the Appium documentation home page
     * 
     * @return ExamplePage instance for method chaining
     */
    @Step("Navigate to Appium documentation")
    public ExamplePage navigate() {
        log.info("Navigating to Appium documentation: {}", APPIUM_DOCS_URL);
        page.navigate(APPIUM_DOCS_URL);
        page.waitForLoadState();
        
        // Try to accept any cookie consent dialog
        acceptCookieConsentIfPresent();
        
        return this;
    }
    
    /**
     * Search for a given term
     *
     * @param searchTerm The term to search for
     * @return ExamplePage instance for method chaining
     */
    @Step("Search for: {searchTerm}")
    public ExamplePage search(String searchTerm) {
        log.info("Searching for: {}", searchTerm);
        
        // Try to accept any cookie consent dialog before interacting
        acceptCookieConsentIfPresent();
        
        searchButton.click();
        searchInput.fill(searchTerm);
        searchInput.press("Enter");
        page.waitForLoadState();
        
        // Try again after search results load
        acceptCookieConsentIfPresent();
        
        return this;
    }
    
    /**
     * Navigate to specific section in documentation
     *
     * @param sectionName The name of the section to navigate to
     * @return ExamplePage instance for method chaining
     */
    @Step("Navigate to section: {sectionName}")
    public ExamplePage navigateToSection(String sectionName) {
        log.info("Navigating to section: {}", sectionName);
        
        // Try to accept any cookie consent dialog before clicking
        acceptCookieConsentIfPresent();
        
        // Updated to match the current navigation structure
        page.locator("nav.md-tabs__list a.md-tabs__link:has-text('" + sectionName + "')").first().click();
        page.waitForLoadState();
        
        // Try again after the page loads in case a new dialog appears
        acceptCookieConsentIfPresent();
        
        return this;
    }
    
    /**
     * Toggle between dark and light mode
     * 
     * @return ExamplePage instance for method chaining
     */
    @Step("Toggle dark/light mode")
    public ExamplePage toggleDarkMode() {
        log.info("Toggling dark/light mode");
        // Click on the dark mode label instead of the input directly
        page.locator("form[data-md-component='palette'] label.md-header__button").first().click();
        return this;
    }
    
    /**
     * Get the current documentation page title
     *
     * @return The title of the current page
     */
    @Step("Get documentation page title")
    public String getDocumentationTitle() {
        // Updated to match the current page structure
        String title = page.locator("div.md-content__inner h1").first().textContent();
        log.info("Documentation title: {}", title);
        return title;
    }
    
    /**
     * Count the number of search results
     *
     * @return Number of search results found
     */
    @Step("Count search results")
    public int getSearchResultCount() {
        // Updated to match the current search results structure
        return page.locator("ol.md-search-result__list li").count();
    }
    
    /**
     * Check if the page is loaded
     *
     * @return true if page is loaded, false otherwise
     */
    @Step("Check if Appium documentation is loaded")
    public boolean isLoaded() {
        log.debug("Checking if Appium documentation is loaded");
        return page.url().contains("appium.io/docs") && 
               page.title().contains("Appium");
    }
}