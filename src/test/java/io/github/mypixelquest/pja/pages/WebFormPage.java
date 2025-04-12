package io.github.mypixelquest.pja.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import java.nio.file.Paths;

/**
 * Page Object for Selenium Web Form
 * https://www.selenium.dev/selenium/web/web-form.html
 */
public class WebFormPage extends BasePage {
    // URL for the web form
    private static final String WEB_FORM_URL = "https://www.selenium.dev/selenium/web/web-form.html";
    
    // Form element locators
    private final Locator textInput;
    private final Locator passwordInput;
    private final Locator textareaInput;
    private final Locator disabledInput;
    private final Locator readonlyInput;
    private final Locator dropdown;
    private final Locator datalistInput;
    private final Locator fileInput;
    private final Locator checkedCheckbox;
    private final Locator defaultCheckbox;
    private final Locator submitButton;
    private final Locator colorPicker;
    private final Locator datePicker;
    private final Locator rangeInput;
    private final Locator checkedRadio;
    private final Locator defaultRadio;
    
    /**
     * Constructor for WebFormPage
     *
     * @param page Playwright Page object
     */
    public WebFormPage(Page page) {
        super(page);
        
        this.textInput = page.locator("input[name='my-text']");
        this.passwordInput = page.locator("input[name='my-password']");
        this.textareaInput = page.locator("textarea[name='my-textarea']");
        this.disabledInput = page.locator("input[name='my-disabled']");
        this.readonlyInput = page.locator("input[name='my-readonly']");
        this.dropdown = page.locator("select[name='my-select']");
        this.datalistInput = page.locator("input[list='my-options']");
        this.fileInput = page.locator("input[type='file']");
        this.checkedCheckbox = page.locator("input[id='my-check-1']");
        this.defaultCheckbox = page.locator("input[id='my-check-2']");
        this.submitButton = page.locator("button[type='submit']");
        this.colorPicker = page.locator("input[type='color']");
        this.datePicker = page.locator("input[name='my-date']");
        this.rangeInput = page.locator("input[name='my-range']");
        this.checkedRadio = page.locator("input[id='my-radio-1']");
        this.defaultRadio = page.locator("input[id='my-radio-2']");
    }

    /**
     * Navigate to the web form
     * 
     * @return WebFormPage instance for method chaining
     */
    @Step("Navigate to web form page")
    public WebFormPage navigate() {
        log.info("Navigating to web form page: {}", WEB_FORM_URL);
        page.navigate(WEB_FORM_URL);
        page.waitForLoadState();
        return this;
    }
    
    /**
     * Fill the text input field
     * 
     * @param text Text to enter
     * @return WebFormPage instance for method chaining
     */
    @Step("Enter text: {text}")
    public WebFormPage fillTextInput(String text) {
        log.info("Filling text input with: {}", text);
        textInput.fill(text);
        return this;
    }
    
    /**
     * Fill the password field
     * 
     * @param password Password to enter
     * @return WebFormPage instance for method chaining
     */
    @Step("Enter password")
    public WebFormPage fillPasswordInput(String password) {
        log.info("Filling password input");
        passwordInput.fill(password);
        return this;
    }
    
    /**
     * Fill the textarea field
     * 
     * @param text Text to enter in textarea
     * @return WebFormPage instance for method chaining
     */
    @Step("Enter textarea content: {text}")
    public WebFormPage fillTextarea(String text) {
        log.info("Filling textarea with: {}", text);
        textareaInput.fill(text);
        return this;
    }
    
    /**
     * Select an option from dropdown
     * 
     * @param option Option to select (One, Two, Three)
     * @return WebFormPage instance for method chaining
     */
    @Step("Select dropdown option: {option}")
    public WebFormPage selectDropdownOption(String option) {
        log.info("Selecting dropdown option: {}", option);
        dropdown.selectOption(option);
        return this;
    }
    
    /**
     * Select a datalist option
     * 
     * @param option Option to fill in datalist
     * @return WebFormPage instance for method chaining
     */
    @Step("Select datalist option: {option}")
    public WebFormPage fillDatalistOption(String option) {
        log.info("Filling datalist with option: {}", option);
        datalistInput.fill(option);
        return this;
    }
    
    /**
     * Upload a file
     * 
     * @param filePath Path to file to upload
     * @return WebFormPage instance for method chaining
     */
    @Step("Upload file: {filePath}")
    public WebFormPage uploadFile(String filePath) {
        log.info("Uploading file: {}", filePath);
        fileInput.setInputFiles(Paths.get(filePath));
        return this;
    }
    
    /**
     * Set checkbox state
     * 
     * @param checkboxId The checkbox ID (my-check-1 or my-check-2)
     * @param checked Whether to check or uncheck
     * @return WebFormPage instance for method chaining
     */
    @Step("Set checkbox {checkboxId} checked: {checked}")
    public WebFormPage setCheckbox(String checkboxId, boolean checked) {
        log.info("Setting checkbox {} to checked: {}", checkboxId, checked);
        Locator checkbox = page.locator("input[id='" + checkboxId + "']");
        if (checkbox.isChecked() != checked) {
            checkbox.click();
        }
        return this;
    }
    
    /**
     * Select radio button
     * 
     * @param radioId The radio button ID (my-radio-1 or my-radio-2)
     * @return WebFormPage instance for method chaining
     */
    @Step("Select radio button: {radioId}")
    public WebFormPage selectRadio(String radioId) {
        log.info("Selecting radio button: {}", radioId);
        page.locator("input[id='" + radioId + "']").check();
        return this;
    }
    
    /**
     * Set date picker value
     * 
     * @param date Date in YYYY-MM-DD format
     * @return WebFormPage instance for method chaining
     */
    @Step("Set date to: {date}")
    public WebFormPage setDate(String date) {
        log.info("Setting date to: {}", date);
        datePicker.fill(date);
        return this;
    }
    
    /**
     * Set color picker value
     * 
     * @param hexColor Color in hex format (#RRGGBB)
     * @return WebFormPage instance for method chaining
     */
    @Step("Set color to: {hexColor}")
    public WebFormPage setColor(String hexColor) {
        log.info("Setting color to: {}", hexColor);
        colorPicker.fill(hexColor);
        return this;
    }
    
    /**
     * Set range slider value
     * 
     * @param value Numeric value for range slider
     * @return WebFormPage instance for method chaining
     */
    @Step("Set range to: {value}")
    public WebFormPage setRangeValue(int value) {
        log.info("Setting range slider to: {}", value);
        rangeInput.fill(String.valueOf(value));
        return this;
    }
    
    /**
     * Submit the form
     */
    @Step("Submit form")
    public void submitForm() {
        log.info("Submitting form");
        submitButton.click();
        page.waitForLoadState();
    }
    
    /**
     * Check if page is loaded
     * 
     * @return true if page is loaded, false otherwise
     */
    @Step("Check if web form page is loaded")
    public boolean isLoaded() {
        log.debug("Checking if web form page is loaded");
        return page.title().contains("Web form");
    }
    
    /**
     * Get the form title
     *
     * @return The title of the form
     */
    @Step("Get form title")
    public String getFormTitle() {
        return page.locator("h1").textContent();
    }
    
    /**
     * Get value of disabled input
     * 
     * @return the value of the disabled input field
     */
    @Step("Get disabled input value")
    public String getDisabledInputValue() {
        log.info("Getting disabled input value");
        return disabledInput.inputValue();
    }
    
    /**
     * Check if disabled input is actually disabled
     * 
     * @return true if the input is disabled, false otherwise
     */
    @Step("Check if disabled input is disabled")
    public boolean isDisabledInputDisabled() {
        log.info("Checking if disabled input is disabled");
        return disabledInput.isDisabled();
    }
    
    /**
     * Get value of readonly input
     * 
     * @return the value of the readonly input field
     */
    @Step("Get readonly input value")
    public String getReadonlyInputValue() {
        log.info("Getting readonly input value");
        return readonlyInput.inputValue();
    }
    
    /**
     * Check if readonly input has readonly attribute
     * 
     * @return true if the input has readonly attribute, false otherwise
     */
    @Step("Check if readonly input has readonly attribute")
    public boolean hasReadonlyAttribute() {
        log.info("Checking if input has readonly attribute");
        return "true".equals(readonlyInput.getAttribute("readonly"));
    }
    
    /**
     * Check if a checkbox is checked
     * 
     * @param checkboxId The ID of the checkbox (my-check-1 or my-check-2)
     * @return true if the checkbox is checked, false otherwise
     */
    @Step("Check if checkbox {checkboxId} is checked")
    public boolean isCheckboxChecked(String checkboxId) {
        log.info("Checking if checkbox {} is checked", checkboxId);
        if ("my-check-1".equals(checkboxId)) {
            return checkedCheckbox.isChecked();
        } else if ("my-check-2".equals(checkboxId)) {
            return defaultCheckbox.isChecked();
        }
        throw new IllegalArgumentException("Invalid checkbox ID: " + checkboxId);
    }
    
    /**
     * Check if a radio button is checked
     * 
     * @param radioId The ID of the radio button (my-radio-1 or my-radio-2)
     * @return true if the radio button is checked, false otherwise
     */
    @Step("Check if radio {radioId} is checked")
    public boolean isRadioChecked(String radioId) {
        log.info("Checking if radio {} is checked", radioId);
        if ("my-radio-1".equals(radioId)) {
            return checkedRadio.isChecked();
        } else if ("my-radio-2".equals(radioId)) {
            return defaultRadio.isChecked();
        }
        throw new IllegalArgumentException("Invalid radio ID: " + radioId);
    }
}