package com.hospital.dao;

import com.hospital.config.DatabaseConfig;
import com.hospital.model.Doctor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {
    // Create a new doctor
    public boolean createDoctor(Doctor doctor) throws SQLException {
        String sql = "INSERT INTO doctors (first_name, last_name, specialization, phone, email, department) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, doctor.getFirstName());
            stmt.setString(2, doctor.getLastName());
            stmt.setString(3, doctor.getSpecialization());
            stmt.setString(4, doctor.getPhone());
            stmt.setString(5, doctor.getEmail());
            stmt.setString(6, doctor.getDepartment());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    // Get all doctors
    public List<Doctor> getAllDoctors() throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctors ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Doctor doctor = new Doctor();
                doctor.setDoctorId(rs.getInt("doctor_id"));
                doctor.setFirstName(rs.getString("first_name"));
                doctor.setLastName(rs.getString("last_name"));
                doctor.setSpecialization(rs.getString("specialization"));
                doctor.setPhone(rs.getString("phone"));
                doctor.setEmail(rs.getString("email"));
                doctor.setDepartment(rs.getString("department"));
                doctor.setCreatedAt(rs.getTimestamp("created_at"));
                
                doctors.add(doctor);
            }
        }
        
        return doctors;
    }
    
    // Get doctor by ID
    public Doctor getDoctorById(int doctorId) throws SQLException {
        String sql = "SELECT * FROM doctors WHERE doctor_id = ?";
        Doctor doctor = null;
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, doctorId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    doctor = new Doctor();
                    doctor.setDoctorId(rs.getInt("doctor_id"));
                    doctor.setFirstName(rs.getString("first_name"));
                    doctor.setLastName(rs.getString("last_name"));
                    doctor.setSpecialization(rs.getString("specialization"));
                    doctor.setPhone(rs.getString("phone"));
                    doctor.setEmail(rs.getString("email"));
                    doctor.setDepartment(rs.getString("department"));
                    doctor.setCreatedAt(rs.getTimestamp("created_at"));
                }
            }
        }
        
        return doctor;
    }
    
    // Update doctor
    public boolean updateDoctor(Doctor doctor) throws SQLException {
        String sql = "UPDATE doctors SET first_name = ?, last_name = ?, specialization = ?, phone = ?, email = ?, department = ? WHERE doctor_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, doctor.getFirstName());
            stmt.setString(2, doctor.getLastName());
            stmt.setString(3, doctor.getSpecialization());
            stmt.setString(4, doctor.getPhone());
            stmt.setString(5, doctor.getEmail());
            stmt.setString(6, doctor.getDepartment());
            stmt.setInt(7, doctor.getDoctorId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    // Delete doctor
    public boolean deleteDoctor(int doctorId) throws SQLException {
        String sql = "DELETE FROM doctors WHERE doctor_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, doctorId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}