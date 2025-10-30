-- Create database
USE hospital_management;

-- Patients table
CREATE TABLE patients (
    patient_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    date_of_birth DATE NOT NULL,
    gender ENUM('Male', 'Female', 'Other') NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(100),
    blood_type VARCHAR(5),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Doctors table
CREATE TABLE doctors (
    doctor_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    specialization VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100),
    department VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Appointments table
CREATE TABLE appointments (
    appointment_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    appointment_date DATE NOT NULL,
    appointment_time TIME NOT NULL,
    status ENUM('Scheduled', 'Completed', 'Cancelled') DEFAULT 'Scheduled',
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id)
);

-- Users table for authentication
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('admin', 'doctor', 'staff') NOT NULL,
    associated_id INT, -- Can be doctor_id or staff_id
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert sample data
INSERT INTO patients (first_name, last_name, date_of_birth, gender, address, phone, email, blood_type)
VALUES 
('John', 'Doe', '1985-05-15', 'Male', '123 Main St, New York', '555-1234', 'john.doe@email.com', 'A+'),
('Jane', 'Smith', '1990-08-22', 'Female', '456 Oak Ave, Boston', '555-5678', 'jane.smith@email.com', 'B-'),
('Michael','Brown', '1978-10-30','Male', 'London road, UK','555-9012','michael.brown@gmail.com','O+'),
('Emily', 'Davis', '1995-06-06','Female', '123 brisbane road, UK','555-3456','emily.davis@email.com','AB+');

INSERT INTO doctors (first_name, last_name, specialization, phone, email, department)
VALUES 
('Robert', 'Johnson', 'Cardiology', '555-9012', 'r.johnson@hospital.com', 'Cardiology'),
('Emily', 'Williams', 'Pediatrics', '555-3456', 'e.williams@hospital.com', 'Pediatrics');

INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time, status, notes)
VALUES 
(1, 1, '2023-06-15', '10:00:00', 'Scheduled', 'Regular checkup'),
(2, 2, '2023-06-16', '14:30:00', 'Scheduled', 'Vaccination');

INSERT INTO users (username, password, role, associated_id)
VALUES 
('admin', '$2a$10$xS8.ZW5XZUw.L8nH5XQy.ej5hV7J3v6VJZ9VZ1YdJkK5m1bN2vL3K', 'admin', NULL),
('doctor1', '$2a$10$xS8.ZW5XZUw.L8nH5XQy.ej5hV7J3v6VJZ9VZ1YdJkK5m1bN2vL3K', 'doctor', 1);