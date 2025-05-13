package oopproject.rpmsfinal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * UserManager backed by MySQL. Singleton.
 */
public class UserManager {
    private static UserManager instance;

    private UserManager() {
        // private constructor
    }

    public static synchronized UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    /**
     * Look up a user by their database ID.
     */
    public User getUserById(String userId) {
        // Try each table in turn
        User u = getPatientById(userId);
        if (u != null) return u;
        u = getDoctorById(userId);
        if (u != null) return u;
        return getAdminById(userId);
    }


    /**
     * Look up a user by their username across all three tables.
     */
    public User getUserByUsername(String username) {
        User u = getPatientByUsername(username);
        if (u != null) return u;
        u = getDoctorByUsername(username);
        if (u != null) return u;
        return getAdminByUsername(username);
    }

    /**
     * Return all users (patients + doctors + admins).
     */
    public List<User> getAllUsers() {
        List<User> all = new ArrayList<>();
        all.addAll(getAllPatients());
        all.addAll(getAllDoctors());
        all.addAll(getAllAdmins());
        return all;
    }

    public Patient getPatientById(String id) {
        String sql = "SELECT * FROM patients WHERE user_id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Patient(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("user_id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("assigned_doctor")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Patient getPatientByUsername(String username) {
        String sql = "SELECT * FROM patients WHERE username = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Patient(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("user_id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("assigned_doctor")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Patient> getAllPatients() {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM patients";
        try (Connection c = DBConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Patient(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("user_id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("assigned_doctor")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Doctor getDoctorById(String id) {
        String sql = "SELECT * FROM doctors WHERE user_id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Doctor(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("user_id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("specialization"),
                        rs.getString("working_hours")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Doctor getDoctorByUsername(String username) {
        String sql = "SELECT * FROM doctors WHERE username = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Doctor(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("user_id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("specialization"),
                        rs.getString("working_hours")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Doctor> getAllDoctors() {
        List<Doctor> list = new ArrayList<>();
        String sql = "SELECT * FROM doctors";
        try (Connection c = DBConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Doctor(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("user_id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("specialization"),
                        rs.getString("working_hours")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Administrator getAdminById(String id) {
        String sql = "SELECT * FROM administrators WHERE user_id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Administrator(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("user_id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getString("official_email"),
                        rs.getString("supervisor_name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Administrator getAdminByUsername(String username) {
        String sql = "SELECT * FROM administrators WHERE username = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Administrator(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("user_id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getString("official_email"),
                        rs.getString("supervisor_name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Administrator> getAllAdmins() {
        List<Administrator> list = new ArrayList<>();
        String sql = "SELECT * FROM administrators";
        try (Connection c = DBConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Administrator(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("user_id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getString("official_email"),
                        rs.getString("supervisor_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
