package oopproject.rpmsfinal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import static oopproject.rpmsfinal.Appointment.generateNextId;

/**
 * VitalRecord class
 * Represents a single record of patient vital signs
 */
public class VitalRecord {
    private final String vitalsId;
    private final String userId;
    private final LocalDateTime timestamp;
    private final double temperature;
    private final int heartRate;
    private final int systolicBP;
    private final int diastolicBP;
    private final int respirationRate;
    private final double oxygenSaturation;
    private final String notes;

    private static final Map<String, List<VitalRecord>> patientVitalsMap = new HashMap<>();
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // Add these constants at the top of the class
    private static final double MIN_TEMPERATURE = 36.1;
    private static final double MAX_TEMPERATURE = 37.8;
    private static final int MIN_HEART_RATE = 60;
    private static final int MAX_HEART_RATE = 100;
    private static final int MIN_SYSTOLIC = 90;
    private static final int MAX_SYSTOLIC = 140;
    private static final int MIN_DIASTOLIC = 60;
    private static final int MAX_DIASTOLIC = 90;
    private static final int MIN_RESPIRATION = 12;
    private static final int MAX_RESPIRATION = 20;
    private static final double MIN_OXYGEN = 95.0;

    private final List<String> alerts;
    private final boolean isCritical;
    private static final Map<String, List<VitalRecord>> patientVitalsDatabase = new HashMap<>();

    /**
     * Constructor for creating a new vital record
     *
     * @param patientId The ID of the patient
     * @param temperature The patient's temperature in Celsius
     * @param heartRate The patient's heart rate in bpm
     * @param systolicBP The patient's systolic blood pressure in mmHg
     * @param diastolicBP The patient's diastolic blood pressure in mmHg
     * @param respirationRate The patient's respiration rate in breaths per minute
     * @param oxygenSaturation The patient's oxygen saturation percentage
     * @param notes Additional notes about the vital signs
     */
    public VitalRecord(String patientId, double temperature, int heartRate, int systolicBP,
                       int diastolicBP, int respirationRate, double oxygenSaturation, String notes) throws SQLException {
        this.vitalsId = generateNextId("VR","vital_records","record_id");
        this.userId = patientId;
        this.timestamp = LocalDateTime.now();
        this.temperature = temperature;
        this.heartRate = heartRate;
        this.systolicBP = systolicBP;
        this.diastolicBP = diastolicBP;
        this.respirationRate = respirationRate;
        this.oxygenSaturation = oxygenSaturation;
        this.notes = notes;
        this.alerts = new ArrayList<>();

        this.isCritical = analyzeVitals();
        addToPatientHistory();
    }

    private boolean analyzeVitals() {
        boolean critical = false;

        if (temperature < MIN_TEMPERATURE || temperature > MAX_TEMPERATURE) {
            alerts.add("Abnormal Temperature: " + temperature + "°C");
            critical = true;
        }
        if (heartRate < MIN_HEART_RATE || heartRate > MAX_HEART_RATE) {
            alerts.add("Abnormal Heart Rate: " + heartRate + " bpm");
            critical = true;
        }
        if (systolicBP < MIN_SYSTOLIC || systolicBP > MAX_SYSTOLIC) {
            alerts.add("Abnormal Systolic BP: " + systolicBP + " mmHg");
            critical = true;
        }
        if (diastolicBP < MIN_DIASTOLIC || diastolicBP > MAX_DIASTOLIC) {
            alerts.add("Abnormal Diastolic BP: " + diastolicBP + " mmHg");
            critical = true;
        }
        if (respirationRate < MIN_RESPIRATION || respirationRate > MAX_RESPIRATION) {
            alerts.add("Abnormal Respiration Rate: " + respirationRate + " breaths/min");
            critical = true;
        }
        if (oxygenSaturation < MIN_OXYGEN) {
            alerts.add("Low Oxygen Saturation: " + oxygenSaturation + "%");
            critical = true;
        }
        return critical;
    }

    // Getters
    public String getVitalsId() {
        return vitalsId;
    }
    public String getUserId() {
        return userId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public int getSystolicBP() {
        return systolicBP;
    }

    public int getDiastolicBP() {
        return diastolicBP;
    }

    public int getRespirationRate() {
        return respirationRate;
    }

    public double getOxygenSaturation() {
        return oxygenSaturation;
    }

    public String getNotes() {
        return notes;
    }

    /**
     * Checks if any vital signs are outside normal ranges
     *
     * @return true if any vitals are abnormal, false otherwise
     */
    public boolean hasAbnormalVitals() {
        return temperature < 36.1 || temperature > 37.8 ||
                heartRate < 60 || heartRate > 100 ||
                systolicBP < 90 || systolicBP > 140 ||
                diastolicBP < 60 || diastolicBP > 90 ||
                respirationRate < 12 || respirationRate > 20 ||
                oxygenSaturation < 95.0;
    }

    /**
     * Gets a formatted string representation of the record timestamp
     *
     * @return Formatted timestamp string
     */
    public String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return timestamp.format(formatter);
    }

    /**
     * Gets all vital records for a specific patient
     *
     * @param patientId The ID of the patient
     * @return List of vital records sorted by timestamp (most recent first)
     */
    public static List<VitalRecord> getPatientVitals(String patientId) {
        List<VitalRecord> records = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM vital_records WHERE user_id=? ORDER BY timestamp DESC")) {

            ps.setString(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    VitalRecord record = new VitalRecord(
                            rs.getString("user_id"),
                            rs.getDouble("temperature"),
                            rs.getInt("heart_rate"),
                            rs.getInt("systolic_bp"),
                            rs.getInt("diastolic_bp"),
                            rs.getInt("respiration_rate"),
                            rs.getDouble("oxygen_saturation"),
                            rs.getString("notes")
                    );
                    records.add(record);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return records;
    }


    /**
     * Adds a vital record to the patient's history
     * Called automatically by the constructor
     */
    private void addToPatientHistory() {
        patientVitalsMap.computeIfAbsent(this.userId, k -> new ArrayList<>()).add(this);
    }

    @Override
    public String toString() {
        return String.format("""
            Timestamp: %s
            Temperature: %.1f°C
            Heart Rate: %d bpm
            Blood Pressure: %d/%d mmHg
            Respiration Rate: %d breaths/min
            Oxygen Saturation: %.1f%%
            Status: %s
            """,
                timestamp.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                temperature,
                heartRate,
                systolicBP, diastolicBP,
                respirationRate,
                oxygenSaturation,
                isCritical ? "CRITICAL" : " Normal"
        );
    }
    private static boolean isValidVitalData(double temperature, int heartRate,
                                            int systolicBP, int diastolicBP,
                                            int respirationRate, double oxygenSaturation) {
        return temperature >= 30 && temperature <= 43 &&
                heartRate >= 30 && heartRate <= 200 &&
                systolicBP >= 70 && systolicBP <= 200 &&
                diastolicBP >= 40 && diastolicBP <= 130 &&
                respirationRate >= 8 && respirationRate <= 40 &&
                oxygenSaturation >= 70 && oxygenSaturation <= 100;
    }



    // Add getter for alerts
    public List<String> getAlerts() {
        return new ArrayList<>(alerts);
    }

    // Add getter for critical status
    public boolean isCritical() {
        return isCritical;
    }
}