package finki.ukim.mk.lab4.part1.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private final By adminMenu = By.xpath("//a[contains(@href, 'viewAdminModule')]");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public UserManagementPage navigateToUserManagement() {
        wait.until(ExpectedConditions.elementToBeClickable(adminMenu)).click();
        // Wait for Add button to appear – indicates user list page loaded
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[contains(@class,'oxd-button--secondary')]//i[contains(@class,'bi-plus')]")));
        return new UserManagementPage(driver);
    }
    public String getLoggedInUserFirstName() {
        // Wait for the dropdown name to be visible
        WebElement nameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.className("oxd-userdropdown-name")
        ));
        String fullName = nameElement.getText().trim();
        // Take only the first part (e.g., "Andrej" from "Andrej Arsovski")
        return fullName.split("\\s+")[0];
    }
}