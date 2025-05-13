package oopproject.rpmsfinal;

public class UserRow {
    private final String userId, name, phone, email, role;

    public UserRow(String userId, String name, String phone, String email, String role) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.role = role;
    }

    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
}
