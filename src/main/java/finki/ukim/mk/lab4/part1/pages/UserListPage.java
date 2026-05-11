package finki.ukim.mk.lab4.part1.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class UserListPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public UserListPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // --- locators (unchanged from original UserManagementPage) ---
    private final By addButton = By.xpath("//button[contains(@class,'oxd-button--secondary')]//i[contains(@class,'bi-plus')]");
    private final By searchButton = By.xpath("//button[@type='submit' and contains(@class, 'oxd-button--secondary')]");
    private final By searchUsernameInput = By.xpath("//form[contains(@class,'oxd-form')]//label[text()='Username']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private final By filterToggleButton = By.xpath("//div[contains(@class,'oxd-table-filter-header-options')]//button[contains(@class,'oxd-icon-button')]");
    private final By filterArea = By.xpath("//div[contains(@class,'oxd-table-filter-area')]");

    // --- helper method (exactly as before) ---
    private void ensureFilterExpanded() {
        WebElement area = driver.findElement(filterArea);
        String display = area.getCssValue("display");
        if ("none".equals(display)) {
            wait.until(ExpectedConditions.elementToBeClickable(filterToggleButton)).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(filterArea));
        }
    }

    // --- public methods (unchanged logic) ---
    public void searchForUser(String username) {
        ensureFilterExpanded();

        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(searchUsernameInput));
        searchInput.clear();
        searchInput.sendKeys(username);

        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();

        wait.until(driver -> {
            if (!driver.findElements(By.xpath("//span[contains(text(),'No Records Found')]")).isEmpty()) {
                return true;
            }
            if (!driver.findElements(By.xpath("//div[contains(@class,'oxd-table-body')]//div[text()='" + username + "']")).isEmpty()) {
                return true;
            }
            return false;
        });
    }

    public boolean isUserPresent(String username) {
        try {
            searchForUser(username);
            return !driver.findElements(By.xpath("//div[contains(@class,'oxd-table-body')]//div[text()='" + username + "']")).isEmpty();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public UserAddPage navigateToAddUser() {                            // new method
        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
        // Wait for Add user form to be visible (username field)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Username']/ancestor::div[contains(@class,'oxd-input-group')]//input")));
        return new UserAddPage(driver);
    }
}