package oopproject.rpmsfinal;

public class DPAssignment {
    private final String doctorId;
    private final String patientId;

    public DPAssignment(String doctorId, String patientId) {
        this.doctorId = doctorId;
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getPatientId() {
        return patientId;
    }
}
