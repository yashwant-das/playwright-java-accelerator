package io.github.mypixelquest.pja.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;

/**
 * Page Object for the Appium Documentation Home Page
 */
public class ExamplePage extends BasePage {
    // URL for the Appium documentation - using latest version
    private static final String APPIUM_DOCS_URL = "https://appium.io/docs/en/latest/";
    
    // Locators
    private final Locator searchButton;
    private final Locator searchInput;
    private final Locator navigationLinks;
    private final Locator darkModeButton;
    private final Locator lightModeButton;
    private final Locator systemPrefButton;
    private final Locator cookieAcceptButton;
    
    /**
     * Constructor for ExamplePage
     *
     * @param page Playwright Page object
     */
    public ExamplePage(Page page) {
        super(page);
        // Updated locators based on Playwright codegen with proper AriaRole enums
        this.cookieAcceptButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Accept"));
        this.lightModeButton = page.getByTitle("Switch to light mode");
        this.darkModeButton = page.getByTitle("Switch to dark mode");
        this.systemPrefButton = page.getByTitle("Switch to system preference");
        this.searchButton = page.getByLabel("Search");
        this.searchInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search"));
        this.navigationLinks = page.locator("nav.md-tabs__list a.md-tabs__link");
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
        
        // Accept cookie consent if present
        acceptCookies();
        
        return this;
    }
    
    /**
     * Accept cookies on the page if the consent dialog is present
     * 
     * @return ExamplePage instance for method chaining
     */
    @Step("Accept cookies if present")
    public ExamplePage acceptCookies() {
        log.info("Attempting to accept cookies if present");
        try {
            if (cookieAcceptButton.isVisible()) {
                log.info("Cookie consent dialog found, accepting");
                cookieAcceptButton.click();
            }
        } catch (Exception e) {
            log.debug("No cookie consent dialog found or it was already accepted");
        }
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
        
        searchButton.click();
        searchInput.fill(searchTerm);
        searchInput.press("Enter");
        page.waitForLoadState();
        
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
        
        // Use the navigation links with the exact name from tabs
        page.locator("nav.md-tabs__list a.md-tabs__link:has-text('" + sectionName + "')").first().click();
        page.waitForLoadState();
        
        return this;
    }
    
    /**
     * Switch to light mode
     * 
     * @return ExamplePage instance for method chaining
     */
    @Step("Switch to light mode")
    public ExamplePage switchToLightMode() {
        log.info("Switching to light mode");
        lightModeButton.click();
        return this;
    }
    
    /**
     * Switch to dark mode
     * 
     * @return ExamplePage instance for method chaining
     */
    @Step("Switch to dark mode")
    public ExamplePage switchToDarkMode() {
        log.info("Switching to dark mode");
        darkModeButton.click();
        return this;
    }
    
    /**
     * Switch to system preference mode
     * 
     * @return ExamplePage instance for method chaining
     */
    @Step("Switch to system preference")
    public ExamplePage switchToSystemPreference() {
        log.info("Switching to system preference");
        systemPrefButton.click();
        return this;
    }
    
    /**
     * Get the current documentation page title
     *
     * @return The title of the current page
     */
    @Step("Get documentation page title")
    public String getDocumentationTitle() {
        String title = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setLevel(1)).textContent();
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