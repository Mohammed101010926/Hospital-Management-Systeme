package com.hospital.model;

import java.util.Date;
import java.util.List;

public class Prescription {
    private String prescriptionId;
    private int patientId;
    private int doctorId;
    private Date date;
    private String status;
    private String notes;
    private double total;
    private Date createdAt;
    private List<PrescriptionItem> items;

    // Constructors
    public Prescription() {}

    public Prescription(String prescriptionId, int patientId, int doctorId, Date date, 
                       String status, String notes, double total, Date createdAt, 
                       List<PrescriptionItem> items) {
        this.prescriptionId = prescriptionId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.status = status;
        this.notes = notes;
        this.total = total;
        this.createdAt = createdAt;
        this.items = items;
    }

    // Getters and Setters
    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<PrescriptionItem> getItems() {
        return items;
    }

    public void setItems(List<PrescriptionItem> items) {
        this.items = items;
    }
}