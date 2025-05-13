package oopproject.rpmsfinal;
public class Patient extends User {
    private String assignedDoctor;
    private String emergencyContact;
    private String emergencyPhone;

    public Patient(String username, String password, String userId, String name, int age, String gender,
                   String phone, String email, String assignedDoctor) {
        super(username, password, userId, name, age, gender, phone, email);
        this.assignedDoctor = assignedDoctor;
    }

    public String getAssignedDoctor() {
        return assignedDoctor;
    }

    public void setAssignedDoctor(String assignedDoctor) {
        this.assignedDoctor = assignedDoctor;
    }

    public void setEmergencyContact(String email, String phone) {
        this.emergencyContact = email;
        this.emergencyPhone = phone;
    }

    public String getEmergencyContact() { return emergencyContact; }
    public String getEmergencyPhone() { return emergencyPhone; }

    @Override
    public String toString() {
        return super.toString() + "\nAssigned Doctor: " + assignedDoctor;
    }
}