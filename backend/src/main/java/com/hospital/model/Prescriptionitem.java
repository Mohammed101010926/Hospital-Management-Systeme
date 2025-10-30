package com.hospital.model;

public class PrescriptionItem {
    private int itemId;
    private String prescriptionId;
    private int medicineId;
    private String dosage;
    private String duration;
    private int quantity;
    private double price;

    // Constructors
    public PrescriptionItem() {}

    public PrescriptionItem(int itemId, String prescriptionId, int medicineId, 
                           String dosage, String duration, int quantity, double price) {
        this.itemId = itemId;
        this.prescriptionId = prescriptionId;
        this.medicineId = medicineId;
        this.dosage = dosage;
        this.duration = duration;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and Setters
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}