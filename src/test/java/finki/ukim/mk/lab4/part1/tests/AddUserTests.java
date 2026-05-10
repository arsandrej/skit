package finki.ukim.mk.lab4.part1.tests;

import finki.ukim.mk.lab4.part1.base.BaseTest;
import finki.ukim.mk.lab4.part1.pages.DashboardPage;
import finki.ukim.mk.lab4.part1.pages.LoginPage;
import finki.ukim.mk.lab4.part1.pages.UserManagementPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.UUID;

public class AddUserTests extends BaseTest {

    private UserManagementPage userPage;

    @BeforeEach
    public void loginAndNavigate() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");
        DashboardPage dashboardPage = new DashboardPage(driver);
        String employeeFirstName = dashboardPage.getLoggedInUserFirstName();
        System.out.println("Employeefirstname:");
        System.out.println(employeeFirstName);
        userPage = dashboardPage.navigateToUserManagement();
        userPage.setEmployeeName(employeeFirstName);

    }

    @Test
    public void shouldAddNewUserWithValidDetails() throws InterruptedException {
        String uniqueUsername = "test_" + UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        userPage.addUser("Admin", "Enabled", uniqueUsername, "Admin@123", "Admin@123");

        // Filter the table by the new username before checking presence
        Assertions.assertTrue(userPage.isUserPresent(uniqueUsername),
                "User should appear in the list after creation");

    }
    //@Test
    public void shouldSearchForExistingUser() {
        // debug method for searching a specific username
        String knownUsername = "Admin";
        Assertions.assertTrue(userPage.isUserPresent(knownUsername),
                "The default Admin user should be found by the search");
    }
    //@Test
    public void debugPrintUserTableHtml() {
        // Print only the user table container
        WebElement tableContainer = driver.findElement(By.xpath("//div[@class='orangehrm-container']"));
        System.out.println("=== User Table HTML ===");
        System.out.println(tableContainer.getAttribute("innerHTML"));

        // Print the entire page source
//         System.out.println("=== FULL PAGE SOURCE ===");
//         System.out.println(driver.getPageSource());
    }
    //@Test
    public void shouldNotAllowDuplicateUsername() throws InterruptedException {
        String baseUsername = "dup_" + UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        userPage.addUser("Admin", "Enabled", baseUsername, "Admin@123", "Admin@123");

        // Attempt to create a duplicate – it should fail with a validation error
        try {
            userPage.addUser("ESS", "Enabled", baseUsername, "Admin@123", "Admin@123");
        } catch (Exception e) {
            // Expected: the addUser method waits for success toast that never appears
        }

        // Explicitly wait for the error message to become visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
        By errorLocator = By.xpath(
                "//span[contains(@class,'oxd-input-field-error-message') and text()='Already exists']"
        );
        boolean errorPresent = !wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(errorLocator)
        ).isEmpty();
        System.out.println("Error message duplicate");
        System.out.println(errorPresent);
        Assertions.assertTrue(errorPresent,
                "Duplicate username should trigger 'Already exists' error message");
    }
}