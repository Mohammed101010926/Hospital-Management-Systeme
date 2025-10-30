package com.hospital.dao;

import com.hospital.config.DatabaseConfig;
import com.hospital.model.Appointment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {
    // Create a new appointment
    public boolean createAppointment(Appointment appointment) throws SQLException {
        String sql = "INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time, status, notes) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, appointment.getPatientId());
            stmt.setInt(2, appointment.getDoctorId());
            stmt.setDate(3, new java.sql.Date(appointment.getAppointmentDate().getTime()));
            stmt.setTime(4, new java.sql.Time(appointment.getAppointmentTime().getTime()));
            stmt.setString(5, appointment.getStatus());
            stmt.setString(6, appointment.getNotes());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    // Get all appointments
    public List<Appointment> getAllAppointments() throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments ORDER BY appointment_date DESC, appointment_time DESC";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(rs.getInt("appointment_id"));
                appointment.setPatientId(rs.getInt("patient_id"));
                appointment.setDoctorId(rs.getInt("doctor_id"));
                appointment.setAppointmentDate(rs.getDate("appointment_date"));
                appointment.setAppointmentTime(rs.getTime("appointment_time"));
                appointment.setStatus(rs.getString("status"));
                appointment.setNotes(rs.getString("notes"));
                appointment.setCreatedAt(rs.getTimestamp("created_at"));
                
                appointments.add(appointment);
            }
        }
        
        return appointments;
    }
    
    // Get appointment by ID
    public Appointment getAppointmentById(int appointmentId) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE appointment_id = ?";
        Appointment appointment = null;
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, appointmentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    appointment = new Appointment();
                    appointment.setAppointmentId(rs.getInt("appointment_id"));
                    appointment.setPatientId(rs.getInt("patient_id"));
                    appointment.setDoctorId(rs.getInt("doctor_id"));
                    appointment.setAppointmentDate(rs.getDate("appointment_date"));
                    appointment.setAppointmentTime(rs.getTime("appointment_time"));
                    appointment.setStatus(rs.getString("status"));
                    appointment.setNotes(rs.getString("notes"));
                    appointment.setCreatedAt(rs.getTimestamp("created_at"));
                }
            }
        }
        
        return appointment;
    }
    
    // Update appointment
    public boolean updateAppointment(Appointment appointment) throws SQLException {
        String sql = "UPDATE appointments SET patient_id = ?, doctor_id = ?, appointment_date = ?, appointment_time = ?, status = ?, notes = ? WHERE appointment_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, appointment.getPatientId());
            stmt.setInt(2, appointment.getDoctorId());
            stmt.setDate(3, new java.sql.Date(appointment.getAppointmentDate().getTime()));
            stmt.setTime(4, new java.sql.Time(appointment.getAppointmentTime().getTime()));
            stmt.setString(5, appointment.getStatus());
            stmt.setString(6, appointment.getNotes());
            stmt.setInt(7, appointment.getAppointmentId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    // Delete appointment
    public boolean deleteAppointment(int appointmentId) throws SQLException {
        String sql = "DELETE FROM appointments WHERE appointment_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, appointmentId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    // Get appointments by date
    public List<Appointment> getAppointmentsByDate(Date date) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE appointment_date = ? ORDER BY appointment_time";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, new java.sql.Date(date.getTime()));
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setAppointmentId(rs.getInt("appointment_id"));
                    appointment.setPatientId(rs.getInt("patient_id"));
                    appointment.setDoctorId(rs.getInt("doctor_id"));
                    appointment.setAppointmentDate(rs.getDate("appointment_date"));
                    appointment.setAppointmentTime(rs.getTime("appointment_time"));
                    appointment.setStatus(rs.getString("status"));
                    appointment.setNotes(rs.getString("notes"));
                    appointment.setCreatedAt(rs.getTimestamp("created_at"));
                    
                    appointments.add(appointment);
                }
            }
        }
        
        return appointments;
    }
    
    // Get appointments by status
    public List<Appointment> getAppointmentsByStatus(String status) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE status = ? ORDER BY appointment_date DESC, appointment_time DESC";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setAppointmentId(rs.getInt("appointment_id"));
                    appointment.setPatientId(rs.getInt("patient_id"));
                    appointment.setDoctorId(rs.getInt("doctor_id"));
                    appointment.setAppointmentDate(rs.getDate("appointment_date"));
                    appointment.setAppointmentTime(rs.getTime("appointment_time"));
                    appointment.setStatus(rs.getString("status"));
                    appointment.setNotes(rs.getString("notes"));
                    appointment.setCreatedAt(rs.getTimestamp("created_at"));
                    
                    appointments.add(appointment);
                }
            }
        }
        
        return appointments;
    }
}