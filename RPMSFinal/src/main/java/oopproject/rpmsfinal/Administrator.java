

package oopproject.rpmsfinal;

/**
 * Administrator class
 * Represents an administrator user in the Remote Health Monitoring System
 */
public class Administrator extends User {
    private String role;
    private String officialEmail;
    private String supervisorName;

    public Administrator(String username, String password, String userId, String name, int age, String gender,
                         String phone, String email, String role, String officialEmail, String supervisorName) {
        super(username, password, userId, name, age, gender, phone, email);
        this.role = role;
        this.officialEmail = officialEmail;
        this.supervisorName = supervisorName;
    }

//getters and Setters
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getOfficialEmail() {
        return officialEmail;
    }

    public void setOfficialEmail(String officialEmail) {
        this.officialEmail = officialEmail;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nRole: " + role +
                "\nOfficial Email: " + officialEmail +
                "\nSupervisor: " + supervisorName;
    }
}