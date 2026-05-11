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

    public UserListPage navigateToUserManagement() {                       // changed return type
        wait.until(ExpectedConditions.elementToBeClickable(adminMenu)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(@class,'oxd-button--secondary')]//i[contains(@class,'bi-plus')]")));
        return new UserListPage(driver);                                   // new
    }

    public String getLoggedInUserFirstName() {
        WebElement nameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("oxd-userdropdown-name")));
        String fullName = nameElement.getText().trim();
        return fullName.split("\\s+")[0];
    }
}