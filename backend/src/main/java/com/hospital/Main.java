package com.hospital;

import com.hospital.config.DatabaseConfig;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // Test database connection
        try {
            Connection conn = DatabaseConfig.getConnection();
            if (conn != null) {
                System.out.println("Database connection successful!");
                conn.close();
            } else {
                System.out.println("Failed to make database connection");
            }
        } catch (SQLException e) {
            System.out.println("Database connection failed:");
            e.printStackTrace();
        }
        
        // Start the web server (this would typically be handled by your servlet container)
        System.out.println("Hospital Management System Backend");
    }
}