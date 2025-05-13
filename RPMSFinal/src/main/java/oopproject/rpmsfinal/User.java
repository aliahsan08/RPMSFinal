package oopproject.rpmsfinal;
public abstract class User {
    private final String username;
    private final String password;
    private final String userId;
    private String name;
    private int age;
    private String gender;
    private String phone;
    private String email;

    public User(String username, String password, String userId, String name, int age, String gender, String phone, String email) {
        this.username = username;
        this.password = password;
        this.userId = userId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean checkPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    @Override
    public String toString() {
        return "User ID: " + userId +
                "\nName: " + name +
                "\nAge: " + age +
                "\nGender: " + gender +
                "\nPhone: " + phone +
                "\nEmail: " + email;
    }
}

