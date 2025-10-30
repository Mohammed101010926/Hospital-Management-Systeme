package com.hospital.dao;

import com.hospital.config.DatabaseConfig;
import com.hospital.model.Prescription;
import com.hospital.model.PrescriptionItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionDAO {
    // Create a new prescription
    public boolean createPrescription(Prescription prescription) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;
        
        try {
            conn = DatabaseConfig.getConnection();
            conn.setAutoCommit(false);
            
            // Insert prescription
            String prescriptionSql = "INSERT INTO prescriptions (prescription_id, patient_id, doctor_id, " +
                                    "prescription_date, status, notes, total) VALUES (?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(prescriptionSql);
            
            stmt.setString(1, prescription.getPrescriptionId());
            stmt.setInt(2, prescription.getPatientId());
            stmt.setInt(3, prescription.getDoctorId());
            stmt.setDate(4, new java.sql.Date(prescription.getDate().getTime()));
            stmt.setString(5, prescription.getStatus());
            stmt.setString(6, prescription.getNotes());
            stmt.setDouble(7, prescription.getTotal());
            
            int prescriptionRows = stmt.executeUpdate();
            
            if (prescriptionRows > 0 && prescription.getItems() != null) {
                // Insert prescription items
                String itemSql = "INSERT INTO prescription_items (prescription_id, medicine_id, " +
                                "dosage, duration, quantity, price) VALUES (?, ?, ?, ?, ?, ?)";
                stmt = conn.prepareStatement(itemSql);
                
                for (PrescriptionItem item : prescription.getItems()) {
                    stmt.setString(1, prescription.getPrescriptionId());
                    stmt.setInt(2, item.getMedicineId());
                    stmt.setString(3, item.getDosage());
                    stmt.setString(4, item.getDuration());
                    stmt.setInt(5, item.getQuantity());
                    stmt.setDouble(6, item.getPrice());
                    stmt.addBatch();
                }
                
                int[] itemRows = stmt.executeBatch();
                
                // Update medicine quantities
                String updateSql = "UPDATE medicines SET quantity = quantity - ? WHERE medicine_id = ?";
                stmt = conn.prepareStatement(updateSql);
                
                for (PrescriptionItem item : prescription.getItems()) {
                    stmt.setInt(1, item.getQuantity());
                    stmt.setInt(2, item.getMedicineId());
                    stmt.addBatch();
                }
                
                stmt.executeBatch();
                
                success = true;
                conn.commit();
            } else {
                conn.rollback();
            }
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
        
        return success;
    }
    
    // Get prescription by ID
    public Prescription getPrescriptionById(String prescriptionId) throws SQLException {
        Prescription prescription = null;
        String sql = "SELECT * FROM prescriptions WHERE prescription_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, prescriptionId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    prescription = new Prescription();
                    prescription.setPrescriptionId(rs.getString("prescription_id"));
                    prescription.setPatientId(rs.getInt("patient_id"));
                    prescription.setDoctorId(rs.getInt("doctor_id"));
                    prescription.setDate(rs.getDate("prescription_date"));
                    prescription.setStatus(rs.getString("status"));
                    prescription.setNotes(rs.getString("notes"));
                    prescription.setTotal(rs.getDouble("total"));
                    prescription.setCreatedAt(rs.getTimestamp("created_at"));
                    
                    // Get prescription items
                    prescription.setItems(getPrescriptionItems(prescriptionId));
                }
            }
        }
        
        return prescription;
    }
    
    // Get all prescriptions
    public List<Prescription> getAllPrescriptions() throws SQLException {
        List<Prescription> prescriptions = new ArrayList<>();
        String sql = "SELECT * FROM prescriptions ORDER BY prescription_date DESC";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Prescription prescription = new Prescription();
                prescription.setPrescriptionId(rs.getString("prescription_id"));
                prescription.setPatientId(rs.getInt("patient_id"));
                prescription.setDoctorId(rs.getInt("doctor_id"));
                prescription.setDate(rs.getDate("prescription_date"));
                prescription.setStatus(rs.getString("status"));
                prescription.setNotes(rs.getString("notes"));
                prescription.setTotal(rs.getDouble("total"));
                prescription.setCreatedAt(rs.getTimestamp("created_at"));
                
                prescriptions.add(prescription);
            }
        }
        
        return prescriptions;
    }
    
    // Get prescriptions by status
    public List<Prescription> getPrescriptionsByStatus(String status) throws SQLException {
        List<Prescription> prescriptions = new ArrayList<>();
        String sql = "SELECT * FROM prescriptions WHERE status = ? ORDER BY prescription_date DESC";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Prescription prescription = new Prescription();
                    prescription.setPrescriptionId(rs.getString("prescription_id"));
                    prescription.setPatientId(rs.getInt("patient_id"));
                    prescription.setDoctorId(rs.getInt("doctor_id"));
                    prescription.setDate(rs.getDate("prescription_date"));
                    prescription.setStatus(rs.getString("status"));
                    prescription.setNotes(rs.getString("notes"));
                    prescription.setTotal(rs.getDouble("total"));
                    prescription.setCreatedAt(rs.getTimestamp("created_at"));
                    
                    prescriptions.add(prescription);
                }
            }
        }
        
        return prescriptions;
    }
    
    // Update prescription status
    public boolean updatePrescriptionStatus(String prescriptionId, String status) throws SQLException {
        String sql = "UPDATE prescriptions SET status = ? WHERE prescription_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            stmt.setString(2, prescriptionId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    // Get prescription items
    private List<PrescriptionItem> getPrescriptionItems(String prescriptionId) throws SQLException {
        List<PrescriptionItem> items = new ArrayList<>();
        String sql = "SELECT * FROM prescription_items WHERE prescription_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, prescriptionId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    PrescriptionItem item = new PrescriptionItem();
                    item.setItemId(rs.getInt("item_id"));
                    item.setPrescriptionId(rs.getString("prescription_id"));
                    item.setMedicineId(rs.getInt("medicine_id"));
                    item.setDosage(rs.getString("dosage"));
                    item.setDuration(rs.getString("duration"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setPrice(rs.getDouble("price"));
                    
                    items.add(item);
                }
            }
        }
        
        return items;
    }
}