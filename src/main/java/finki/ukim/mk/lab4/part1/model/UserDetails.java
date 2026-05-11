package finki.ukim.mk.lab4.part1.model;

public class UserDetails {
    private String userRole;
    private String employeeNamePartial;
    private String status;
    private String username;
    private String password;
    private String confirmPassword;

    public UserDetails(String userRole, String employeeNamePartial, String status, String username, String password, String confirmPassword) {
        this.userRole = userRole;
        this.employeeNamePartial = employeeNamePartial;
        this.status = status;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getUserRole() {
        return userRole;
    }

    public String getEmployeeNamePartial() {
        return employeeNamePartial;
    }

    public String getStatus() {
        return status;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}