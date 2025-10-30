package com.hospital.controller;

import com.hospital.dao.PatientDAO;
import com.hospital.dao.DoctorDAO;
import com.hospital.dao.AppointmentDAO;
import com.hospital.model.Patient;
import com.hospital.model.Doctor;
import com.hospital.model.Appointment;
import com.google.gson.Gson;

import java.io.*;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/api/*")
public class ApiController extends HttpServlet {
    private PatientDAO patientDAO;
    private DoctorDAO doctorDAO;
    private AppointmentDAO appointmentDAO;
    private Gson gson = new Gson();
    
    @Override
    public void init() throws ServletException {
        super.init();
        patientDAO = new PatientDAO();
        doctorDAO = new DoctorDAO();
        appointmentDAO = new AppointmentDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid API endpoint");
                return;
            }
            
            String[] parts = pathInfo.split("/");
            String resource = parts[1];
            
            switch (resource) {
                case "patients":
                    handlePatientsGet(request, response, parts);
                    break;
                case "doctors":
                    handleDoctorsGet(request, response, parts);
                    break;
                case "appointments":
                    handleAppointmentsGet(request, response, parts);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource not found");
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
            e.printStackTrace();
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid API endpoint");
                return;
            }
            
            String[] parts = pathInfo.split("/");
            String resource = parts[1];
            
            switch (resource) {
                case "patients":
                    handlePatientsPost(request, response);
                    break;
                case "doctors":
                    handleDoctorsPost(request, response);
                    break;
                case "appointments":
                    handleAppointmentsPost(request, response);
                    break;
                case "auth":
                    handleAuthPost(request, response, parts);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource not found");
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
            e.printStackTrace();
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid API endpoint");
                return;
            }
            
            String[] parts = pathInfo.split("/");
            String resource = parts[1];
            
            switch (resource) {
                case "patients":
                    handlePatientsPut(request, response, parts);
                    break;
                case "doctors":
                    handleDoctorsPut(request, response, parts);
                    break;
                case "appointments":
                    handleAppointmentsPut(request, response, parts);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource not found");
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
            e.printStackTrace();
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid API endpoint");
                return;
            }
            
            String[] parts = pathInfo.split("/");
            String resource = parts[1];
            
            switch (resource) {
                case "patients":
                    handlePatientsDelete(request, response, parts);
                    break;
                case "doctors":
                    handleDoctorsDelete(request, response, parts);
                    break;
                case "appointments":
                    handleAppointmentsDelete(request, response, parts);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource not found");
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
            e.printStackTrace();
        }
    }
    
    // Patients handlers
    private void handlePatientsGet(HttpServletRequest request, HttpServletResponse response, String[] parts) 
            throws SQLException, IOException {
        if (parts.length == 2) {
            // GET /api/patients - Get all patients
            List<Patient> patients = patientDAO.getAllPatients();
            sendJsonResponse(response, patients);
        } else if (parts.length == 3) {
            // GET /api/patients/{id} - Get patient by ID
            int patientId = Integer.parseInt(parts[2]);
            Patient patient = patientDAO.getPatientById(patientId);
            
            if (patient != null) {
                sendJsonResponse(response, patient);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Patient not found");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid endpoint");
        }
    }
    
    private void handlePatientsPost(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        // POST /api/patients - Create new patient
        Patient patient = gson.fromJson(request.getReader(), Patient.class);
        boolean created = patientDAO.createPatient(patient);
        
        if (created) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"message\": \"Patient created successfully\"}");
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to create patient");
        }
    }
    
    private void handlePatientsPut(HttpServletRequest request, HttpServletResponse response, String[] parts) 
            throws SQLException, IOException {
        if (parts.length == 3) {
            // PUT /api/patients/{id} - Update patient
            int patientId = Integer.parseInt(parts[2]);
            Patient patient = gson.fromJson(request.getReader(), Patient.class);
            patient.setPatientId(patientId);
            
            boolean updated = patientDAO.updatePatient(patient);
            
            if (updated) {
                response.getWriter().write("{\"message\": \"Patient updated successfully\"}");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Patient not found");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid endpoint");
        }
    }
    
    private void handlePatientsDelete(HttpServletRequest request, HttpServletResponse response, String[] parts) 
            throws SQLException, IOException {
        if (parts.length == 3) {
            // DELETE /api/patients/{id} - Delete patient
            int patientId = Integer.parseInt(parts[2]);
            boolean deleted = patientDAO.deletePatient(patientId);
            
            if (deleted) {
                response.getWriter().write("{\"message\": \"Patient deleted successfully\"}");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Patient not found");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid endpoint");
        }
    }
    
    // Doctors handlers (similar structure to patients)
    private void handleDoctorsGet(HttpServletRequest request, HttpServletResponse response, String[] parts) 
            throws SQLException, IOException {
        // Implement similar to patients
    }
    
    private void handleDoctorsPost(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        // Implement similar to patients
    }
    
    private void handleDoctorsPut(HttpServletRequest request, HttpServletResponse response, String[] parts) 
            throws SQLException, IOException {
        // Implement similar to patients
    }
    
    private void handleDoctorsDelete(HttpServletRequest request, HttpServletResponse response, String[] parts) 
            throws SQLException, IOException {
        // Implement similar to patients
    }
    
    // Appointments handlers (similar structure to patients)
    private void handleAppointmentsGet(HttpServletRequest request, HttpServletResponse response, String[] parts) 
            throws SQLException, IOException {
        // Implement similar to patients
    }
    
    private void handleAppointmentsPost(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        // Implement similar to patients
    }
    
    private void handleAppointmentsPut(HttpServletRequest request, HttpServletResponse response, String[] parts) 
            throws SQLException, IOException {
        // Implement similar to patients
    }
    
    private void handleAppointmentsDelete(HttpServletRequest request, HttpServletResponse response, String[] parts) 
            throws SQLException, IOException {
        // Implement similar to patients
    }
    
    // Authentication handlers
    private void handleAuthPost(HttpServletRequest request, HttpServletResponse response, String[] parts) 
            throws SQLException, IOException {
        if (parts.length >= 3) {
            String action = parts[2];
            
            switch (action) {
                case "login":
                    // Handle login
                    break;
                case "register":
                    // Handle registration
                    break;
                case "logout":
                    // Handle logout
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Action not found");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid endpoint");
        }
    }
    
    // Helper method to send JSON responses
    private void sendJsonResponse(HttpServletResponse response, Object data) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(data));
    }
    // Add these methods to the ApiController class

// Doctors handlers
private void handleDoctorsGet(HttpServletRequest request, HttpServletResponse response, String[] parts) 
throws SQLException, IOException {
if (parts.length == 2) {
// GET /api/doctors - Get all doctors
List<Doctor> doctors = doctorDAO.getAllDoctors();
sendJsonResponse(response, doctors);
} else if (parts.length == 3) {
// GET /api/doctors/{id} - Get doctor by ID
int doctorId = Integer.parseInt(parts[2]);
Doctor doctor = doctorDAO.getDoctorById(doctorId);

if (doctor != null) {
    sendJsonResponse(response, doctor);
} else {
    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Doctor not found");
}
} else {
response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid endpoint");
}
}

private void handleDoctorsPost(HttpServletRequest request, HttpServletResponse response) 
throws SQLException, IOException {
// POST /api/doctors - Create new doctor
Doctor doctor = gson.fromJson(request.getReader(), Doctor.class);
boolean created = doctorDAO.createDoctor(doctor);

if (created) {
response.setStatus(HttpServletResponse.SC_CREATED);
response.getWriter().write("{\"message\": \"Doctor created successfully\"}");
} else {
response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to create doctor");
}
}

private void handleDoctorsPut(HttpServletRequest request, HttpServletResponse response, String[] parts) 
throws SQLException, IOException {
if (parts.length == 3) {
// PUT /api/doctors/{id} - Update doctor
int doctorId = Integer.parseInt(parts[2]);
Doctor doctor = gson.fromJson(request.getReader(), Doctor.class);
doctor.setDoctorId(doctorId);

boolean updated = doctorDAO.updateDoctor(doctor);

if (updated) {
    response.getWriter().write("{\"message\": \"Doctor updated successfully\"}");
} else {
    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Doctor not found");
}
} else {
response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid endpoint");
}
}

private void handleDoctorsDelete(HttpServletRequest request, HttpServletResponse response, String[] parts) 
throws SQLException, IOException {
if (parts.length == 3) {
// DELETE /api/doctors/{id} - Delete doctor
int doctorId = Integer.parseInt(parts[2]);
boolean deleted = doctorDAO.deleteDoctor(doctorId);

if (deleted) {
    response.getWriter().write("{\"message\": \"Doctor deleted successfully\"}");
} else {
    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Doctor not found");
}
} else {
response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid endpoint");
}
}

// Appointments handlers
private void handleAppointmentsGet(HttpServletRequest request, HttpServletResponse response, String[] parts) 
throws SQLException, IOException {
if (parts.length == 2) {
// GET /api/appointments - Get all appointments
List<Appointment> appointments = appointmentDAO.getAllAppointments();
sendJsonResponse(response, appointments);
} else if (parts.length == 3) {
// GET /api/appointments/{id} - Get appointment by ID
int appointmentId = Integer.parseInt(parts[2]);
Appointment appointment = appointmentDAO.getAppointmentById(appointmentId);

if (appointment != null) {
    sendJsonResponse(response, appointment);
} else {
    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Appointment not found");
}
} else if (parts.length == 4 && parts[2].equals("date")) {
// GET /api/appointments/date/{date} - Get appointments by date
String dateStr = parts[3];
try {
    java.sql.Date date = java.sql.Date.valueOf(dateStr);
    List<Appointment> appointments = appointmentDAO.getAppointmentsByDate(date);
    sendJsonResponse(response, appointments);
} catch (IllegalArgumentException e) {
    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date format. Use YYYY-MM-DD");
}
} else if (parts.length == 4 && parts[2].equals("status")) {
// GET /api/appointments/status/{status} - Get appointments by status
String status = parts[3];
List<Appointment> appointments = appointmentDAO.getAppointmentsByStatus(status);
sendJsonResponse(response, appointments);
} else {
response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid endpoint");
}
}

private void handleAppointmentsPost(HttpServletRequest request, HttpServletResponse response) 
throws SQLException, IOException {
// POST /api/appointments - Create new appointment
Appointment appointment = gson.fromJson(request.getReader(), Appointment.class);
boolean created = appointmentDAO.createAppointment(appointment);

if (created) {
response.setStatus(HttpServletResponse.SC_CREATED);
response.getWriter().write("{\"message\": \"Appointment created successfully\"}");
} else {
response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to create appointment");
}
}

private void handleAppointmentsPut(HttpServletRequest request, HttpServletResponse response, String[] parts) 
throws SQLException, IOException {
if (parts.length == 3) {
// PUT /api/appointments/{id} - Update appointment
int appointmentId = Integer.parseInt(parts[2]);
Appointment appointment = gson.fromJson(request.getReader(), Appointment.class);
appointment.setAppointmentId(appointmentId);

boolean updated = appointmentDAO.updateAppointment(appointment);

if (updated) {
    response.getWriter().write("{\"message\": \"Appointment updated successfully\"}");
} else {
    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Appointment not found");
}
} else {
response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid endpoint");
}
}

private void handleAppointmentsDelete(HttpServletRequest request, HttpServletResponse response, String[] parts) 
throws SQLException, IOException {
if (parts.length == 3) {
// DELETE /api/appointments/{id} - Delete appointment
int appointmentId = Integer.parseInt(parts[2]);
boolean deleted = appointmentDAO.deleteAppointment(appointmentId);

if (deleted) {
    response.getWriter().write("{\"message\": \"Appointment deleted successfully\"}");
} else {
    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Appointment not found");
}
} else {
response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid endpoint");
}
}
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/pharmacy")
public class ApiController {
    
    // Medicine Inventory Endpoints
    private List<Map<String, Object>> medicines = new ArrayList<>();
    private List<Map<String, Object>> prescriptions = new ArrayList<>();
    private List<Map<String, Object>> suppliers = new ArrayList<>();
    
    // Initialize with some sample data
    public ApiController() {
        // Sample medicines
        Map<String, Object> med1 = new HashMap<>();
        med1.put("id", 1);
        med1.put("name", "Paracetamol");
        med1.put("generic", "Acetaminophen");
        med1.put("category", "Analgesic");
        med1.put("batch", "BATCH001");
        med1.put("quantity", 150);
        med1.put("unit", "Tablets");
        med1.put("price", 0.25);
        med1.put("expiry", "2024-12-31");
        med1.put("manufacturer", "ABC Pharma");
        med1.put("status", "In Stock");
        medicines.add(med1);
        
        // Sample prescriptions
        Map<String, Object> pres1 = new HashMap<>();
        pres1.put("id", "RX1001");
        pres1.put("patient", "John Doe");
        pres1.put("doctor", "Dr. Robert Johnson");
        pres1.put("date", "2023-06-15");
        pres1.put("status", "Dispensed");
        pres1.put("total", 12.50);
        prescriptions.add(pres1);
        
        // Sample suppliers
        Map<String, Object> sup1 = new HashMap<>();
        sup1.put("id", "SUP001");
        sup1.put("name", "ABC Pharmaceuticals");
        sup1.put("contact", "John Smith");
        sup1.put("phone", "555-1234");
        sup1.put("email", "john@abcpharma.com");
        sup1.put("medicines", 45);
        suppliers.add(sup1);
    }
    
    // Medicine Inventory Endpoints
    @GetMapping("/medicines")
    public ResponseEntity<List<Map<String, Object>>> getAllMedicines() {
        return ResponseEntity.ok(medicines);
    }
    
    @GetMapping("/medicines/{id}")
    public ResponseEntity<Map<String, Object>> getMedicineById(@PathVariable int id) {
        return medicines.stream()
            .filter(med -> (int)med.get("id") == id)
            .findFirst()
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/medicines")
    public ResponseEntity<Map<String, Object>> addMedicine(@RequestBody Map<String, Object> medicine) {
        // Generate new ID
        int newId = medicines.stream().mapToInt(m -> (int)m.get("id")).max().orElse(0) + 1;
        medicine.put("id", newId);
        medicines.add(medicine);
        return ResponseEntity.status(HttpStatus.CREATED).body(medicine);
    }
    
    @PutMapping("/medicines/{id}")
    public ResponseEntity<Map<String, Object>> updateMedicine(
            @PathVariable int id, 
            @RequestBody Map<String, Object> updatedMedicine) {
        for (int i = 0; i < medicines.size(); i++) {
            if ((int)medicines.get(i).get("id") == id) {
                updatedMedicine.put("id", id);
                medicines.set(i, updatedMedicine);
                return ResponseEntity.ok(updatedMedicine);
            }
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/medicines/{id}")
    public ResponseEntity<Void> deleteMedicine(@PathVariable int id) {
        boolean removed = medicines.removeIf(med -> (int)med.get("id") == id);
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
    
    // Prescription Endpoints
    @GetMapping("/prescriptions")
    public ResponseEntity<List<Map<String, Object>>> getAllPrescriptions() {
        return ResponseEntity.ok(prescriptions);
    }
    
    @GetMapping("/prescriptions/{id}")
    public ResponseEntity<Map<String, Object>> getPrescriptionById(@PathVariable String id) {
        return prescriptions.stream()
            .filter(pres -> pres.get("id").equals(id))
            .findFirst()
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/prescriptions")
    public ResponseEntity<Map<String, Object>> addPrescription(@RequestBody Map<String, Object> prescription) {
        // Generate new prescription ID
        String newId = "RX" + (1000 + prescriptions.size() + 1);
        prescription.put("id", newId);
        prescriptions.add(prescription);
        return ResponseEntity.status(HttpStatus.CREATED).body(prescription);
    }
    
    @PutMapping("/prescriptions/{id}")
    public ResponseEntity<Map<String, Object>> updatePrescription(
            @PathVariable String id, 
            @RequestBody Map<String, Object> updatedPrescription) {
        for (int i = 0; i < prescriptions.size(); i++) {
            if (prescriptions.get(i).get("id").equals(id)) {
                updatedPrescription.put("id", id);
                prescriptions.set(i, updatedPrescription);
                return ResponseEntity.ok(updatedPrescription);
            }
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/prescriptions/{id}")
    public ResponseEntity<Void> deletePrescription(@PathVariable String id) {
        boolean removed = prescriptions.removeIf(pres -> pres.get("id").equals(id));
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
    
    // Supplier Endpoints
    @GetMapping("/suppliers")
    public ResponseEntity<List<Map<String, Object>>> getAllSuppliers() {
        return ResponseEntity.ok(suppliers);
    }
    
    @GetMapping("/suppliers/{id}")
    public ResponseEntity<Map<String, Object>> getSupplierById(@PathVariable String id) {
        return suppliers.stream()
            .filter(sup -> sup.get("id").equals(id))
            .findFirst()
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/suppliers")
    public ResponseEntity<Map<String, Object>> addSupplier(@RequestBody Map<String, Object> supplier) {
        // Generate new supplier ID
        String newId = "SUP" + (100 + suppliers.size() + 1);
        supplier.put("id", newId);
        suppliers.add(supplier);
        return ResponseEntity.status(HttpStatus.CREATED).body(supplier);
    }
    
    @PutMapping("/suppliers/{id}")
    public ResponseEntity<Map<String, Object>> updateSupplier(
            @PathVariable String id, 
            @RequestBody Map<String, Object> updatedSupplier) {
        for (int i = 0; i < suppliers.size(); i++) {
            if (suppliers.get(i).get("id").equals(id)) {
                updatedSupplier.put("id", id);
                suppliers.set(i, updatedSupplier);
                return ResponseEntity.ok(updatedSupplier);
            }
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/suppliers/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable String id) {
        boolean removed = suppliers.removeIf(sup -> sup.get("id").equals(id));
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
    
    // Search Endpoints
    @GetMapping("/medicines/search")
    public ResponseEntity<List<Map<String, Object>>> searchMedicines(
            @RequestParam String query) {
        List<Map<String, Object>> results = new ArrayList<>();
        String lowerQuery = query.toLowerCase();
        
        for (Map<String, Object> med : medicines) {
            if (med.get("name").toString().toLowerCase().contains(lowerQuery) ||
                med.get("generic").toString().toLowerCase().contains(lowerQuery) ||
                med.get("category").toString().toLowerCase().contains(lowerQuery) ||
                med.get("batch").toString().toLowerCase().contains(lowerQuery)) {
                results.add(med);
            }
        }
        
        return ResponseEntity.ok(results);
    }
    
    // Export Endpoints
    @GetMapping("/medicines/export")
    public ResponseEntity<String> exportMedicines() {
        // In a real application, this would generate a CSV or Excel file
        return ResponseEntity.ok("Export functionality would generate a file download");
    }
    
    @GetMapping("/prescriptions/print/{id}")
    public ResponseEntity<String> printPrescription(@PathVariable String id) {
        // In a real application, this would generate a printable prescription
        return prescriptions.stream()
            .filter(pres -> pres.get("id").equals(id))
            .findFirst()
            .map(pres -> ResponseEntity.ok("Printable prescription for " + pres.get("id")))
            .orElse(ResponseEntity.notFound().build());
    }
}
}