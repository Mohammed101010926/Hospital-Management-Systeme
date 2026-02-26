package com.hospital.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApiController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle GET requests for patients, doctors, and appointments
        String type = request.getParameter("type");
        switch (type) {
            case "patients":
                // Implement logic to fetch patient data
                response.getWriter().write("Fetching patients data...");
                break;
            case "doctors":
                // Implement logic to fetch doctor data
                response.getWriter().write("Fetching doctors data...");
                break;
            case "appointments":
                // Implement logic to fetch appointments data
                response.getWriter().write("Fetching appointments data...");
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid type specified");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle POST requests for patients, doctors, and appointments
        String type = request.getParameter("type");
        switch (type) {
            case "patients":
                // Implement logic to create a new patient
                response.getWriter().write("Creating a new patient...");
                break;
            case "doctors":
                // Implement logic to create a new doctor
                response.getWriter().write("Creating a new doctor...");
                break;
            case "appointments":
                // Implement logic to create a new appointment
                response.getWriter().write("Creating a new appointment...");
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid type specified");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle PUT requests for patients, doctors, and appointments
        String type = request.getParameter("type");
        switch (type) {
            case "patients":
                // Implement logic to update a patient
                response.getWriter().write("Updating a patient...");
                break;
            case "doctors":
                // Implement logic to update a doctor
                response.getWriter().write("Updating a doctor...");
                break;
            case "appointments":
                // Implement logic to update an appointment
                response.getWriter().write("Updating an appointment...");
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid type specified");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle DELETE requests for patients, doctors, and appointments
        String type = request.getParameter("type");
        switch (type) {
            case "patients":
                // Implement logic to delete a patient
                response.getWriter().write("Deleting a patient...");
                break;
            case "doctors":
                // Implement logic to delete a doctor
                response.getWriter().write("Deleting a doctor...");
                break;
            case "appointments":
                // Implement logic to delete an appointment
                response.getWriter().write("Deleting an appointment...");
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid type specified");
        }
    }
}