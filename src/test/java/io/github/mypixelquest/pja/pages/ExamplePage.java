package io.github.mypixelquest.pja.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;

/**
 * Page Object for the Playwright Documentation Homepage
 */
public class ExamplePage extends BasePage {
    // Base URL from config
    private static final String BASE_URL = "https://playwright.dev/";

    // Navigation and Header Elements
    private final Locator navbar;
    private final Locator docs;
    private final Locator api;
    private final Locator community;
    private final Locator search;
    private final Locator skipToContent;
    private final Locator getStartedButton;

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

    // Community Links
    private final Locator discordLink;
    private final Locator githubLink;
    private final Locator twitterLink;
    private final Locator linkedinLink;
    private final Locator youtubeLink;
    private final Locator stackOverflowLink;

    // Example Links
    private final Locator accessibilityInsightsLink;
    private final Locator adobeLink;
    private final Locator bingLink;
    private final Locator disneyHotstarLink;
    private final Locator outlookLink;
    private final Locator vsCodeLink;
    private final Locator materialUiLink;
    private final Locator reactNavigationLink;

    /**
     * Constructor for ExamplePage
     *
     * @param page Playwright Page object
     */
    public ExamplePage(Page page) {
        super(page);

        // Initialize navigation elements
        this.navbar = page.locator("nav.navbar");
        this.docs = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Docs"));
        this.api = page.locator("a[href='/docs/api/class-playwright']");
        this.community = page.locator("a[href='/community/welcome']");
        this.search = page.locator("button.DocSearch");
        this.skipToContent = page.locator("a.skipToContent_fXgn");
        this.getStartedButton = page.locator("a.getStarted_Sjon");

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

        // Initialize community links
        this.discordLink = page.locator("a[href*='discord']");
        this.githubLink = page.locator("footer a[href*='github.com/microsoft/playwright']");
        this.twitterLink = page.locator("a[href='https://twitter.com/playwrightweb']");
        this.linkedinLink = page.locator("a[href='https://www.linkedin.com/company/playwrightweb']");
        this.youtubeLink = page.locator("a[href*='youtube.com']");
        this.stackOverflowLink = page.locator("a[href*='stackoverflow.com']");

        // Initialize example links
        this.accessibilityInsightsLink = page.locator("a[href='https://accessibilityinsights.io/']");
        this.adobeLink = page.locator("a[href*='github.com/adobe']");
        this.bingLink = page.locator("a[href='https://bing.com']");
        this.disneyHotstarLink = page.locator("a[href='https://www.hotstar.com/']");
        this.outlookLink = page.locator("a[href='https://outlook.com']");
        this.vsCodeLink = page.locator("a[href='https://code.visualstudio.com']");
        this.materialUiLink = page.locator("a[href*='material-ui']");
        this.reactNavigationLink = page.locator("a[href*='react-navigation']");
    }

    /**
     * Navigate to the Playwright documentation home page
     * 
     * @return ExamplePage instance for method chaining
     */
    @Step("Navigate to Playwright documentation")
    public ExamplePage navigate() {
        page.navigate(BASE_URL);
        return this;
    }

    /**
     * Click Get Started button
     * 
     * @return ExamplePage instance for method chaining
     */
    @Step("Click Get Started button")
    public ExamplePage clickGetStarted() {
        getStartedButton.click();
        return this;
    }

    /**
     * Open search dialog
     * 
     * @return ExamplePage instance for method chaining
     */
    @Step("Open search dialog")
    public ExamplePage openSearch() {
        search.click();
        return this;
    }

    /**
     * Navigate to language-specific documentation
     * 
     * @param language The programming language (java, python, javascript, typescript, dotnet)
     * @return ExamplePage instance for method chaining
     */
    @Step("Navigate to {language} documentation")
    public ExamplePage navigateToLanguage(String language) {
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
     * @return ExamplePage instance for method chaining
     */
    @Step("Navigate to {tool}")
    public ExamplePage navigateToTool(String tool) {
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
     * Navigate to community resources
     * 
     * @param platform The platform name (discord, github, twitter, linkedin, youtube, stackoverflow)
     * @return ExamplePage instance for method chaining
     */
    @Step("Navigate to {platform}")
    public ExamplePage navigateToCommunity(String platform) {
        switch (platform.toLowerCase()) {
            case "discord":
                discordLink.click();
                break;
            case "github":
                githubLink.click();
                break;
            case "twitter":
                twitterLink.click();
                break;
            case "linkedin":
                linkedinLink.click();
                break;
            case "youtube":
                youtubeLink.click();
                break;
            case "stackoverflow":
                stackOverflowLink.click();
                break;
            default:
                throw new IllegalArgumentException("Unsupported platform: " + platform);
        }
        return this;
    }

    /**
     * Navigate to example sites
     * 
     * @param site The example site name
     * @return ExamplePage instance for method chaining
     */
    @Step("Navigate to example site: {site}")
    public ExamplePage navigateToExample(String site) {
        switch (site.toLowerCase()) {
            case "accessibility-insights":
                accessibilityInsightsLink.click();
                break;
            case "adobe":
                adobeLink.click();
                break;
            case "bing":
                bingLink.click();
                break;
            case "disney-hotstar":
                disneyHotstarLink.click();
                break;
            case "outlook":
                outlookLink.click();
                break;
            case "vscode":
                vsCodeLink.click();
                break;
            case "material-ui":
                materialUiLink.click();
                break;
            case "react-navigation":
                reactNavigationLink.click();
                break;
            default:
                throw new IllegalArgumentException("Unsupported example site: " + site);
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
     * @return ExamplePage instance for method chaining
     */
    @Step("Click Docs link")
    public ExamplePage clickDocs() {
        docs.click();
        return this;
    }

    /**
     * Click on API link in the navigation
     * 
     * @return ExamplePage instance for method chaining
     */
    @Step("Click API link")
    public ExamplePage clickApi() {
        api.click();
        return this;
    }

    /**
     * Click on Community link in the navigation
     * 
     * @return ExamplePage instance for method chaining
     */
    @Step("Click Community link")
    public ExamplePage clickCommunity() {
        community.click();
        return this;
    }

    /**
     * Click on Skip to Content link for accessibility
     * 
     * @return ExamplePage instance for method chaining
     */
    @Step("Click Skip to Content link")
    public ExamplePage clickSkipToContent() {
        skipToContent.click();
        return this;
    }
}