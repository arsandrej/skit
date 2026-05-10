package finki.ukim.mk.lab4.part1.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class UserManagementPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public UserManagementPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    private String employeeName = "Admin";

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    // User list page
    private final By addButton = By.xpath("//button[contains(@class,'oxd-button--secondary')]//i[contains(@class,'bi-plus')]");
    private final By searchButton = By.xpath("//button[@type='submit' and contains(@class, 'oxd-button--secondary')]");

    // Add user form
    private final By usernameField = By.xpath("//label[text()='Username']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private final By passwordField = By.xpath("//label[text()='Password']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private final By confirmPasswordField = By.xpath("//label[text()='Confirm Password']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private final By saveButton = By.xpath("//button[@type='submit']");

    private final By employeeNameInput = By.xpath("//input[@placeholder='Type for hints...']");
    private final By dropdownArrow = By.xpath("//i[contains(@class,'oxd-select-text--arrow')]");

    private final By successToast = By.xpath("//div[contains(@class,'oxd-toast--success')]");

    private final By searchUsernameInput = By.xpath(
            "//form[contains(@class,'oxd-form')]//label[text()='Username']/ancestor::div[contains(@class,'oxd-input-group')]//input"
    );

    // The toggle button that expands the filter area
    private final By filterToggleButton = By.xpath(
            "//div[contains(@class,'oxd-table-filter-header-options')]//button[contains(@class,'oxd-icon-button')]"
    );

    // The filter area itself (we check its style to know if it's hidden)
    private final By filterArea = By.xpath("//div[contains(@class,'oxd-table-filter-area')]");

    private void ensureFilterExpanded() {
        WebElement area = driver.findElement(filterArea);
        String display = area.getCssValue("display");
        if ("none".equals(display)) {
            // Click the down‑arrow button to open the filter
            wait.until(ExpectedConditions.elementToBeClickable(filterToggleButton)).click();
            // Wait until the area becomes visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(filterArea));
        }
    }
    private void selectDropdownOption(int arrowIndex, String optionText) throws InterruptedException {
        List<WebElement> arrows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(dropdownArrow));
        arrows.get(arrowIndex).click();
        By optionLocator = By.xpath("//div[@role='listbox']//span[text()='" + optionText + "']");
        if (driver.findElements(optionLocator).isEmpty()) {
            optionLocator = By.xpath("//div[@role='listbox']//div[text()='" + optionText + "']");
        }
        wait.until(ExpectedConditions.elementToBeClickable(optionLocator)).click();
    }

    private void selectEmployeeName() throws InterruptedException {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(employeeNameInput));
        input.clear();
        input.sendKeys(employeeName);
        WebDriverWait pause = new WebDriverWait(driver, Duration.ofSeconds(2));
        try {
            pause.until(driver -> false);  // condition never becomes true
        } catch (TimeoutException e) {
            // explicitly wait 2s
        }
        List<WebElement> autocompleteDivs = driver.findElements(By.cssSelector("div.oxd-autocomplete-dropdown"));
        if (autocompleteDivs.isEmpty()) {
            throw new RuntimeException("No autocomplete dropdown for: " + employeeName);
        }
        List<WebElement> suggestions = autocompleteDivs.get(0).findElements(By.xpath(".//div"));
        if (suggestions.isEmpty()) {
            throw new RuntimeException("No suggestions for: " + employeeName);
        }

        suggestions.get(0).click();
    }

    public void searchForUser(String username) {
        ensureFilterExpanded();

        WebElement searchInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(searchUsernameInput)
        );
        searchInput.clear();
        searchInput.sendKeys(username);

        // Click the Search button
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();

        // Wait for the table to refresh – either the user row appears or "No Records Found"
        wait.until(driver -> {
            // No records?
            if (!driver.findElements(By.xpath("//span[contains(text(),'No Records Found')]")).isEmpty()) {
                return true;
            }
            // User row present?
            if (!driver.findElements(
                    By.xpath("//div[contains(@class,'oxd-table-body')]//div[text()='" + username + "']")
            ).isEmpty()) {
                return true;
            }
            return false;
        });
    }

    public void addUser(String userRole, String status,
                        String username, String password, String confirmPassword) throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));

        selectDropdownOption(0, userRole);
        selectEmployeeName();
        selectDropdownOption(1, status);

        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(confirmPasswordField).sendKeys(confirmPassword);

        driver.findElement(saveButton).click();

        // Wait for success toast and redirect to list page
        wait.until(ExpectedConditions.presenceOfElementLocated(successToast));
        wait.until(ExpectedConditions.urlContains("viewSystemUsers"));
        // Wait for the Add button to be present – this confirms the list page is ready
        wait.until(ExpectedConditions.presenceOfElementLocated(addButton));
    }

    public boolean isUserPresent(String username) {
        try {
            searchForUser(username);
            return !driver.findElements(
                    By.xpath("//div[contains(@class,'oxd-table-body')]//div[text()='" + username + "']")
            ).isEmpty();
        } catch (TimeoutException e) {
            return false;
        }
    }
}