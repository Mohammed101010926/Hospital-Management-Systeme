package com.hospital.model;

import java.util.Date;

public class Appointment {
    private int appointmentId;
    private int patientId;
    private int doctorId;
    private Date appointmentDate;
    private Date appointmentTime;
    private String status;
    private String notes;
    private Date createdAt;
    
    // Constructors
    public Appointment() {}
    
    public Appointment(int appointmentId, int patientId, int doctorId, Date appointmentDate, 
                      Date appointmentTime, String status, String notes, Date createdAt) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = status;
        this.notes = notes;
        this.createdAt = createdAt;
    }
    
    // G