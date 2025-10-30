package com.hospital.dao;

import com.hospital.config.DatabaseConfig;
import com.hospital.model.Supplier;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {
    // Create a new supplier
    public boolean createSupplier(Supplier supplier) throws SQLException {
        String sql = "INSERT INTO suppliers (supplier_id, name, contact_person, phone, email, address) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, supplier.getSupplierId());
            stmt.setString(2, supplier.getName());
            stmt.setString(3, supplier.getContactPerson());
            stmt.setString(4, supplier.getPhone());
            stmt.setString(5, supplier.getEmail());
            stmt.setString(6, supplier.getAddress());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    // Get all suppliers
    public List<Supplier> getAllSuppliers() throws SQLException {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM suppliers ORDER BY name";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Supplier supplier = new Supplier();
                supplier.setSupplierId(rs.getString("supplier_id"));
                supplier.setName(rs.getString("name"));
                supplier.setContactPerson(rs.getString("contact_person"));
                supplier.setPhone(rs.getString("phone"));
                supplier.setEmail(rs.getString("email"));
                supplier.setAddress(rs.getString("address"));
                supplier.setCreatedAt(rs.getTimestamp("created_at"));
                
                suppliers.add(supplier);
            }
        }
        
        return suppliers;
    }
    
    // Get supplier by ID
    public Supplier getSupplierById(String supplierId) throws SQLException {
        String sql = "SELECT * FROM suppliers WHERE supplier_id = ?";
        Supplier supplier = null;
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, supplierId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    supplier = new Supplier();
                    supplier.setSupplierId(rs.getString("supplier_id"));
                    supplier.setName(rs.getString("name"));
                    supplier.setContactPerson(rs.getString("contact_person"));
                    supplier.setPhone(rs.getString("phone"));
                    supplier.setEmail(rs.getString("email"));
                    supplier.setAddress(rs.getString("address"));
                    supplier.setCreatedAt(rs.getTimestamp("created_at"));
                }
            }
        }
        
        return supplier;
    }
    
    // Update supplier
    public boolean updateSupplier(Supplier supplier) throws SQLException {
        String sql = "UPDATE suppliers SET name = ?, contact_person = ?, phone = ?, " +
                     "email = ?, address = ? WHERE supplier_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, supplier.getName());
            stmt.setString(2, supplier.getContactPerson());
            stmt.setString(3, supplier.getPhone());
            stmt.setString(4, supplier.getEmail());
            stmt.setString(5, supplier.getAddress());
            stmt.setString(6, supplier.getSupplierId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    // Delete supplier
    public boolean deleteSupplier(String supplierId) throws SQLException {
        String sql = "DELETE FROM suppliers WHERE supplier_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, supplierId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    // Count medicines supplied by a supplier
    public int countMedicinesBySupplier(String supplierId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM medicines WHERE supplier_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, supplierId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        
        return 0;
    }
}