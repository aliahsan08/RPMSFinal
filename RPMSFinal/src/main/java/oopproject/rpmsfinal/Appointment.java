package oopproject.rpmsfinal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;


public class Appointment {
    private final String appointmentId;
    private final String patientId;
    private final String doctorId;
    private LocalDateTime dateTime;
    private String purpose;
    private AppointmentStatus status;
    private String notes;
    private String prescriptionId;

    public Appointment(String patientId, String doctorId, LocalDateTime dateTime, String purpose) throws SQLException {
        this.appointmentId = generateNextId("AP","appointments","appointment_id");
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.dateTime = dateTime;
        this.purpose = purpose;
        this.status = AppointmentStatus.REQUESTED;
        this.notes = "";
    }
    public Appointment(String appointmentId, String patientId, String doctorId, LocalDateTime dateTime, String purpose) throws SQLException {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.dateTime = dateTime;
        this.purpose = purpose;
        this.status = AppointmentStatus.REQUESTED;
        this.notes = "";
    }

    // Getters and setters
    public String getAppointmentId() { return appointmentId; }
    public String getPatientId() { return patientId; }
    public String getDoctorId() { return doctorId; }
    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
    public AppointmentStatus getStatus() { return status; }
    public void setStatus(AppointmentStatus status) { this.status = status; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public String getPrescriptionId() { return prescriptionId; }

    public void addNotes(String notes) {
        if (notes != null && !notes.trim().isEmpty()) {
            this.notes = this.notes.isEmpty() ? notes : this.notes + "\n" + notes;
        }
    }
    public enum AppointmentStatus {
        REQUESTED, CONFIRMED, CANCELLED, COMPLETED
    }
    public void linkWithPrescription(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public static String generateNextId(String prefix, String tableName, String idColumn) throws SQLException {
        String latestId = null;
        String sql = "SELECT " + idColumn + " FROM " + tableName + " WHERE " + idColumn + " LIKE ? ORDER BY " + idColumn + " DESC LIMIT 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, prefix + "%");
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                latestId = rs.getString(1);
            }
        }

        int nextNumber = 1;
        if (latestId != null && latestId.length() > prefix.length()) {
            String numberPart = latestId.substring(prefix.length());
            try {
                nextNumber = Integer.parseInt(numberPart) + 1;
            } catch (NumberFormatException e) {
                // fallback to 1 if parse fails
            }
        }

        return String.format("%s%03d", prefix, nextNumber);
    }

}