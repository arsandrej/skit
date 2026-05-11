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

import java.util.UUID;

public class DeleteUserTests extends BaseTest {

    private UserListPage listPage;
    private UserAddPage addPage;
    private String employeeFirstName;

    @BeforeEach
    public void setup() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");
        DashboardPage dashboardPage = new DashboardPage(driver);
        employeeFirstName = dashboardPage.getLoggedInUserFirstName();
        System.out.println("Employeefirstname:");
        System.out.println(employeeFirstName);

        listPage = dashboardPage.navigateToUserManagement();
        addPage = listPage.navigateToAddUser();
    }

    @Test
    public void shouldDeleteAnExistingUser() throws InterruptedException {
        String username = "del_" + UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        UserDetails user = new UserDetails("Admin", employeeFirstName, "Enabled", username, "Admin@123", "Admin@123");
        addPage.addUser(user);
        Assertions.assertTrue(listPage.isUserPresent(username), "User should exist before deletion");
        Assertions.assertFalse(listPage.isUserPresent(username), "User should be removed from the list after deletion");
    }
}