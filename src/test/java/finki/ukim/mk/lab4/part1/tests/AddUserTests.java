package finki.ukim.mk.lab4.part1.tests;

import finki.ukim.mk.lab4.part1.base.BaseTest;
import finki.ukim.mk.lab4.part1.model.UserDetails;
import finki.ukim.mk.lab4.part1.pages.DashboardPage;
import finki.ukim.mk.lab4.part1.pages.LoginPage;
import finki.ukim.mk.lab4.part1.pages.UserAddPage;
import finki.ukim.mk.lab4.part1.pages.UserListPage;
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

    private UserListPage listPage;
    private UserAddPage addPage;
    private String employeeFirstName;

    @BeforeEach
    public void loginAndNavigate() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");
        DashboardPage dashboardPage = new DashboardPage(driver);
        employeeFirstName = dashboardPage.getLoggedInUserFirstName();
        System.out.println("Employeefirstname:");
        System.out.println(employeeFirstName);

        listPage = dashboardPage.navigateToUserManagement();
        addPage = listPage.navigateToAddUser();        // we are now on the Add User form
    }

    @Test
    public void shouldAddNewUserWithValidDetails() throws InterruptedException {
        String uniqueUsername = "test_" + UUID.randomUUID().toString().replace("-", "").substring(0, 10);

        UserDetails user = new UserDetails("Admin", employeeFirstName, "Enabled", uniqueUsername, "Admin@123", "Admin@123");
        addPage.addUser(user);

        Assertions.assertTrue(listPage.isUserPresent(uniqueUsername), "User should appear in the list after creation");
    }

    //@Test
    public void shouldSearchForExistingUser() {
        String knownUsername = "Admin";
        Assertions.assertTrue(listPage.isUserPresent(knownUsername), "The default Admin user should be found by the search");
    }

    //@Test
    public void debugPrintUserTableHtml() {
        WebElement tableContainer = driver.findElement(By.xpath("//div[@class='orangehrm-container']"));
        System.out.println("=== User Table HTML ===");
        System.out.println(tableContainer.getAttribute("innerHTML"));
    }

    //@Test
    public void shouldNotAllowDuplicateUsername() throws InterruptedException {
        String baseUsername = "dup_" + UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        UserDetails newUser = new UserDetails("Admin", employeeFirstName, "Enabled", baseUsername, "Admin@123", "Admin@123");
        addPage.addUser(newUser);

        // Attempt to create a duplicate – must navigate again to the Add form
        UserAddPage secondAddPage = listPage.navigateToAddUser();
        try {
            UserDetails duplicateUser = new UserDetails("ESS", employeeFirstName, "Enabled", baseUsername, "Admin@123", "Admin@123");
            secondAddPage.addUser(duplicateUser);
        } catch (Exception e) {
            // Expected: the addUser method waits for success toast that never appears
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
        By errorLocator = By.xpath("//span[contains(@class,'oxd-input-field-error-message') and text()='Already exists']");
        boolean errorPresent = !wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(errorLocator)).isEmpty();
        System.out.println("Error message duplicate");
        System.out.println(errorPresent);
        Assertions.assertTrue(errorPresent, "Duplicate username should trigger 'Already exists' error message");
    }
}