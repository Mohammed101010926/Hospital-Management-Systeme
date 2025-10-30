package com.hospital.dao;

import com.hospital.config.DatabaseConfig;
import com.hospital.model.Patient;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {
    // Create a new patient
    public boolean createPatient(Patient patient) throws SQLException {
        String sql = "INSERT INTO patients (first_name, last_name, date_of_birth, gender, address, phone, email, blood_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, patient.getFirstName());
            stmt.setString(2, patient.getLastName());
            stmt.setDate(3, new java.sql.Date(patient.getDateOfBirth().getTime()));
            stmt.setString(4, patient.getGender());
            stmt.setString(5, patient.getAddress());
            stmt.setString(6, patient.getPhone());
            stmt.setString(7, patient.getEmail());
            stmt.setString(8, patient.getBloodType());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    // Get all patients
    public List<Patient> getAllPatients() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Patient patient = new Patient();
                patient.setPatientId(rs.getInt("patient_id"));
                patient.setFirstName(rs.getString("first_name"));
                patient.setLastName(rs.getString("last_name"));
                patient.setDateOfBirth(rs.getDate("date_of_birth"));
                patient.setGender(rs.getString("gender"));
                patient.setAddress(rs.getString("address"));
                patient.setPhone(rs.getString("phone"));
                patient.setEmail(rs.getString("email"));
                patient.setBloodType(rs.getString("blood_type"));
                patient.setCreatedAt(rs.getTimestamp("created_at"));
                
                patients.add(patient);
            }
        }
        
        return patients;
    }
    
    // Get patient by ID
    public Patient getPatientById(int patientId) throws SQLException {
        String sql = "SELECT * FROM patients WHERE patient_id = ?";
        Patient patient = null;
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, patientId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    patient = new Patient();
                    patient.setPatientId(rs.getInt("patient_id"));
                    patient.setFirstName(rs.getString("first_name"));
                    patient.setLastName(rs.getString("last_name"));
                    patient.setDateOfBirth(rs.getDate("date_of_birth"));
                    patient.setGender(rs.getString("gender"));
                    patient.setAddress(rs.getString("address"));
                    patient.setPhone(rs.getString("phone"));
                    patient.setEmail(rs.getString("email"));
                    patient.setBloodType(rs.getString("blood_type"));
                    patient.setCreatedAt(rs.getTimestamp("created_at"));
                }
            }
        }
        
        return patient;
    }
    
    // Update patient
    public boolean updatePatient(Patient patient) throws SQLException {
        String sql = "UPDATE patients SET first_name = ?, last_name = ?, date_of_birth = ?, gender = ?, address = ?, phone = ?, email = ?, blood_type = ? WHERE patient_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, patient.getFirstName());
            stmt.setString(2, patient.getLastName());
            stmt.setDate(3, new java.sql.Date(patient.getDateOfBirth().getTime()));
            stmt.setString(4, patient.getGender());
            stmt.setString(5, patient.getAddress());
            stmt.setString(6, patient.getPhone());
            stmt.setString(7, patient.getEmail());
            stmt.setString(8, patient.getBloodType());
            stmt.setInt(9, patient.getPatientId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    // Delete patient
    public boolean deletePatient(int patientId) throws SQLException {
        String sql = "DELETE FROM patients WHERE patient_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, patientId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}