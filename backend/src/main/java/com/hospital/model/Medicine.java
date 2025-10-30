package com.hospital.model;

import java.util.Date;

public class Medicine {
    private int medicineId;
    private String name;
    private String genericName;
    private String category;
    private String batchNumber;
    private int quantity;
    private String unit;
    private double price;
    private Date expiryDate;
    private String manufacturer;
    private String description;
    private Date createdAt;

    // Constructors
    public Medicine() {}

    public Medicine(int medicineId, String name, String genericName, String category, 
                   String batchNumber, int quantity, String unit, double price, 
                   Date expiryDate, String manufacturer, String description, Date createdAt) {
        this.medicineId = medicineId;
        this.name = name;
        this.genericName = genericName;
        this.category = category;
        this.batchNumber = batchNumber;
        this.quantity = quantity;
        this.unit = unit;
        this.price = price;
        this.expiryDate = expiryDate;
        this.manufacturer = manufacturer;
        this.description = description;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenericName() {
        return genericName;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}