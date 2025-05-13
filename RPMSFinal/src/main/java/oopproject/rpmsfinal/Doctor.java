

package oopproject.rpmsfinal;

import java.util.ArrayList;
import java.util.List;

/**
 * Doctor class
 * Represents a doctor user in the Remote Health Monitoring System
 */
public class Doctor extends User {
    private String specialization;
    private String workingHours;
    private final List<String> assignedPatientIds;
    private int patientCounter = 0;

    public Doctor(String username, String password, String userId, String name, int age, String gender,
                  String phone, String email, String specialization, String workingHours) {
        super(username, password, userId, name, age, gender, phone, email);
        this.specialization = specialization;
        this.workingHours = workingHours;
        this.assignedPatientIds = new ArrayList<>();
    }

  //getters and setters
    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }


    public String getWorkingHours() {
        return workingHours;
    }
    public List<String> getAssignedPatientIds(){
        return assignedPatientIds;
    }


    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }


    public List<String> getAssignedPatients() {
        return assignedPatientIds;
    }


    public void addAssignedPatient(String patientId) {
        if (!assignedPatientIds.contains(patientId)) {
            assignedPatientIds.add(patientCounter,patientId);
            patientCounter++;
        }
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nSpecialization: " + specialization +
                "\nWorking Hours: " + workingHours +
                "\nAssigned Patients: " + assignedPatientIds.size();
    }
}