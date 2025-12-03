package io.github.mypixelquest.pja.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;

/**
 * Page Object for the Playwright Documentation Homepage
 */
public class PlaywrightDocsPage extends BasePage {
    // Base URL from config
    private static final String BASE_URL = "https://playwright.dev/";

    // Navigation and Header Elements
    private final Locator navbar;
    private final Locator docs;
    private final Locator api;
    private final Locator community;
    private final Locator search;
    private final Locator searchModal;
    private final Locator skipToContent;
    private final Locator getStartedButton;
    private final Locator languageDropdown;

    // Language Links
    private final Locator javaLink;
    private final Locator pythonLink;
    private final Locator javascriptLink;
    private final Locator typescriptLink;
    private final Locator dotnetLink;
    private final Locator nodejsLink;

    // Tool Links
    private final Locator codegenLink;
    private final Locator playwrightInspectorLink;
    private final Locator traceViewerLink;

    /**
     * Constructor for PlaywrightDocsPage
     *
     * @param page Playwright Page object
     */
    public PlaywrightDocsPage(Page page) {
        super(page);

        // Initialize navigation elements
        this.navbar = page.locator("nav.navbar");
        this.docs = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Docs"));
        this.api = page.locator("a[href='/docs/api/class-playwright']");
        this.community = page.locator("a[href='/community/welcome']");
        this.search = page.locator("button.DocSearch");
        this.searchModal = page.locator("div.DocSearch-Modal");
        this.skipToContent = page.locator("a.skipToContent_fXgn");
        this.getStartedButton = page.locator("a.getStarted_Sjon");
        this.languageDropdown = page.locator("div.navbar__item.dropdown.dropdown--hoverable");

        // Initialize language links
        this.javaLink = page.locator("a[href='/java/']");
        this.pythonLink = page.locator("a[href='/python/']");
        this.javascriptLink = page.locator("text=JavaScript");
        this.typescriptLink = page.locator("text=TypeScript");
        this.dotnetLink = page.locator("a[href='/dotnet/']");
        this.nodejsLink = page.locator("a[href='#']");

        // Initialize tool links
        this.codegenLink = page.locator("a[href='docs/codegen']");
        this.playwrightInspectorLink = page.locator("a[href='docs/debug#playwright-inspector']");
        this.traceViewerLink = page.locator("a[href='docs/trace-viewer-intro']");
    }

    /**
     * Navigate to the Playwright documentation home page
     * 
     * @return PlaywrightDocsPage instance for method chaining
     */
    @Step("Navigate to Playwright documentation")
    public PlaywrightDocsPage navigate() {
        page.navigate(BASE_URL);
        return this;
    }

    /**
     * Click Get Started button
     * 
     * @return PlaywrightDocsPage instance for method chaining
     */
    @Step("Click Get Started button")
    public PlaywrightDocsPage clickGetStarted() {
        getStartedButton.click();
        return this;
    }

    /**
     * Open search dialog and wait for it to be visible
     * 
     * @return PlaywrightDocsPage instance for method chaining
     */
    @Step("Open search dialog")
    public PlaywrightDocsPage openSearch() {
        search.click();
        // Wait for the search modal to be visible
        searchModal.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return this;
    }

    /**
     * Check if search modal is visible
     *
     * @return true if search modal is visible, false otherwise
     */
    @Step("Check if search modal is visible")
    public boolean isSearchModalVisible() {
        return searchModal.isVisible();
    }

    /**
     * Click the language dropdown to show language options
     * 
     * @return PlaywrightDocsPage instance for method chaining
     */
    @Step("Click language dropdown")
    public PlaywrightDocsPage clickLanguageDropdown() {
        languageDropdown.click();
        return this;
    }

    /**
     * Navigate to language-specific documentation
     * 
     * @param language The programming language (java, python, javascript, typescript, dotnet)
     * @return PlaywrightDocsPage instance for method chaining
     */
    @Step("Navigate to {language} documentation")
    public PlaywrightDocsPage navigateToLanguage(String language) {
        // First click the language dropdown
        clickLanguageDropdown();
        
        // Then select the specific language
        switch (language.toLowerCase()) {
            case "java":
                javaLink.click();
                break;
            case "python":
                pythonLink.click();
                break;
            case "javascript":
                javascriptLink.click();
                break;
            case "typescript":
                typescriptLink.click();
                break;
            case "dotnet":
            case ".net":
                dotnetLink.click();
                break;
            case "nodejs":
            case "node.js":
                nodejsLink.click();
                break;
            default:
                throw new IllegalArgumentException("Unsupported language: " + language);
        }
        return this;
    }

    /**
     * Navigate to a tool page
     * 
     * @param tool The tool name (codegen, inspector, trace-viewer)
     * @return PlaywrightDocsPage instance for method chaining
     */
    @Step("Navigate to {tool}")
    public PlaywrightDocsPage navigateToTool(String tool) {
        switch (tool.toLowerCase()) {
            case "codegen":
                codegenLink.click();
                break;
            case "inspector":
                playwrightInspectorLink.click();
                break;
            case "trace-viewer":
                traceViewerLink.click();
                break;
            default:
                throw new IllegalArgumentException("Unsupported tool: " + tool);
        }
        return this;
    }

    /**
     * Check if the page is loaded
     *
     * @return true if the page is loaded, false otherwise
     */
    @Step("Check if page is loaded")
    public boolean isLoaded() {
        return navbar.isVisible() && getStartedButton.isVisible();
    }

    /**
     * Get the current page URL
     *
     * @return the current page URL
     */
    @Step("Get current URL")
    public String getCurrentUrl() {
        return page.url();
    }

    /**
     * Click on Docs link in the navigation
     * 
     * @return PlaywrightDocsPage instance for method chaining
     */
    @Step("Click Docs link")
    public PlaywrightDocsPage clickDocs() {
        docs.click();
        return this;
    }

    /**
     * Click on API link in the navigation
     * 
     * @return PlaywrightDocsPage instance for method chaining
     */
    @Step("Click API link")
    public PlaywrightDocsPage clickApi() {
        api.click();
        return this;
    }

    /**
     * Click on Community link in the navigation
     * 
     * @return PlaywrightDocsPage instance for method chaining
     */
    @Step("Click Community link")
    public PlaywrightDocsPage clickCommunity() {
        community.click();
        return this;
    }

    /**
     * Click on Skip to Content link for accessibility
     * 
     * @return PlaywrightDocsPage instance for method chaining
     */
    @Step("Click Skip to Content link")
    public PlaywrightDocsPage clickSkipToContent() {
        skipToContent.click();
        return this;
    }
}

