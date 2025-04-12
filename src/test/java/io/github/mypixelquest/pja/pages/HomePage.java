package io.github.mypixelquest.pja.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

/**
 * Page Object for the Appium Documentation Home Page
 */
public class HomePage extends BasePage {
    // URL for the Appium documentation
    private static final String APPIUM_DOCS_URL = "https://appium.io/docs/en/latest/";
    
    // Locators
    private final Locator searchButton;
    private final Locator searchInput;
    private final Locator navigationLinks;
    private final Locator quickstartLink;
    private final Locator darkModeButton;
    
    /**
     * Constructor for HomePage
     *
     * @param page Playwright Page object
     */
    public HomePage(Page page) {
        super(page);
        this.searchButton = page.locator("button[class*='md-search__icon']").first();
        this.searchInput = page.locator("input[placeholder='Search']");
        this.navigationLinks = page.locator("nav.md-nav--primary a.md-nav__link");
        this.quickstartLink = page.locator("a:has-text('Quickstart')");
        this.darkModeButton = page.locator("button[data-md-color-scheme]");
    }
    
    /**
     * Navigate to the Appium documentation home page
     * 
     * @return HomePage instance for method chaining
     */
    @Step("Navigate to Appium documentation")
    public HomePage navigate() {
        log.info("Navigating to Appium documentation: {}", APPIUM_DOCS_URL);
        page.navigate(APPIUM_DOCS_URL);
        page.waitForLoadState();
        return this;
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
     * @return HomePage instance for method chaining
     */
    @Step("Navigate to section: {sectionName}")
    public HomePage navigateToSection(String sectionName) {
        log.info("Navigating to section: {}", sectionName);
        page.locator("a:has-text('" + sectionName + "')").first().click();
        page.waitForLoadState();
        return this;
    }
    
    /**
     * Toggle between dark and light mode
     * 
     * @return HomePage instance for method chaining
     */
    @Step("Toggle dark/light mode")
    public HomePage toggleDarkMode() {
        log.info("Toggling dark/light mode");
        darkModeButton.click();
        return this;
    }
    
    /**
     * Get the current documentation page title
     *
     * @return The title of the current page
     */
    @Step("Get documentation page title")
    public String getDocumentationTitle() {
        String title = page.locator("article h1").textContent();
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
        return page.locator("article ul.md-search-result__list li").count();
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