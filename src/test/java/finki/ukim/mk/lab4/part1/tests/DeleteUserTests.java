package finki.ukim.mk.lab4.part1.tests;

import finki.ukim.mk.lab4.part1.base.BaseTest;
import finki.ukim.mk.lab4.part1.pages.DashboardPage;
import finki.ukim.mk.lab4.part1.pages.LoginPage;
import finki.ukim.mk.lab4.part1.pages.UserManagementPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class DeleteUserTests extends BaseTest {

    private UserManagementPage userPage;

    @BeforeEach
    public void setup() {
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
    public void shouldDeleteAnExistingUser() throws InterruptedException {
        String username = "del_" + UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        userPage.addUser("Admin", "Enabled", username, "Admin@123", "Admin@123");
        Assertions.assertTrue(userPage.isUserPresent(username), "User should exist before deletion");
        Assertions.assertFalse(userPage.isUserPresent(username),
                "User should be removed from the list after deletion");
    }
}