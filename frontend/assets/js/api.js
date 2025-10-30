// API Configuration
const API_BASE_URL = 'http://localhost:8080/hospital-management/api';

// Common Headers
const headers = {
    'Content-Type': 'application/json',
    'Accept': 'application/json'
};

// Helper function for API requests
async function makeRequest(url, method, data = null, authToken = null) {
    const options = {
        method,
        headers: {
            ...headers,
            ...(authToken && { 'Authorization': `Bearer ${authToken}` })
        }
    };
    
    if (data) {
        options.body = JSON.stringify(data);
    }
    
    try {
        const response = await fetch(`${API_BASE_URL}${url}`, options);
        
        if (!response.ok) {
            const error = await response.json();
            throw new Error(error.message || 'Something went wrong');
        }
        
        return await response.json();
    } catch (error) {
        console.error('API Error:', error);
        throw error;
    }
}

// Authentication API
const authAPI = {
    login: async (username, password) => {
        return makeRequest('/auth/login', 'POST', { username, password });
    },
    
    register: async (userData) => {
        return makeRequest('/auth/register', 'POST', userData);
    },
    
    logout: async (token) => {
        return makeRequest('/auth/logout', 'POST', null, token);
    }
};

// Patients API
const patientsAPI = {
    getAll: async (token) => {
        return makeRequest('/patients', 'GET', null, token);
    },
    
    getById: async (id, token) => {
        return makeRequest(`/patients/${id}`, 'GET', null, token);
    },
    
    create: async (patientData, token) => {
        return makeRequest('/patients', 'POST', patientData, token);
    },
    
    update: async (id, patientData, token) => {
        return makeRequest(`/patients/${id}`, 'PUT', patientData, token);
    },
    
    delete: async (id, token) => {
        return makeRequest(`/patients/${id}`, 'DELETE', null, token);
    }
};

// Doctors API
const doctorsAPI = {
    getAll: async (token) => {
        return makeRequest('/doctors', 'GET', null, token);
    },
    
    getById: async (id, token) => {
        return makeRequest(`/doctors/${id}`, 'GET', null, token);
    },
    
    create: async (doctorData, token) => {
        return makeRequest('/doctors', 'POST', doctorData, token);
    },
    
    update: async (id, doctorData, token) => {
        return makeRequest(`/doctors/${id}`, 'PUT', doctorData, token);
    },
    
    delete: async (id, token) => {
        return makeRequest(`/doctors/${id}`, 'DELETE', null, token);
    }
};

// Appointments API
const appointmentsAPI = {
    getAll: async (token) => {
        return makeRequest('/appointments', 'GET', null, token);
    },
    
    getById: async (id, token) => {
        return makeRequest(`/appointments/${id}`, 'GET', null, token);
    },
    
    create: async (appointmentData, token) => {
        return makeRequest('/appointments', 'POST', appointmentData, token);
    },
    
    update: async (id, appointmentData, token) => {
        return makeRequest(`/appointments/${id}`, 'PUT', appointmentData, token);
    },
    
    delete: async (id, token) => {
        return makeRequest(`/appointments/${id}`, 'DELETE', null, token);
    },
    
    getByDate: async (date, token) => {
        return makeRequest(`/appointments/date/${date}`, 'GET', null, token);
    },
    
    getByStatus: async (status, token) => {
        return makeRequest(`/appointments/status/${status}`, 'GET', null, token);
    }
};
// Pharmacy API
const PharmacyAPI = {
    getAll: async (token) => {
        return makeRequest('/pharmacy', 'GET', null, token);
    },
    
    getById: async (id, token) => {
        return makeRequest(`/pharmacy/${id}`, 'GET', null, token);
    },
    
    create: async (doctorData, token) => {
        return makeRequest('/pharmacy', 'POST', pharmacyData, token);
    },
    
    update: async (id, doctorData, token) => {
        return makeRequest(`/pharmacy/${id}`, 'PUT', pharmacyData, token);
    },
    
    delete: async (id, token) => {
        return makeRequest(`/pharmacy/${id}`, 'DELETE', null, token);
    }
};
// Export all API modules
export { authAPI, patientsAPI, doctorsAPI, appointmentsAPI,pharmacyAPI };

async function loadPatients() {
    try {
        const patients = await patientsAPI.getAll(authToken);
        // Render the patients in the table
    } catch (error) {
        console.error('Failed to load patients:', error);
        alert('Failed to load patients. Please try again.');
    }
}

saveBtn.addEventListener('click', async function() {
    const patientData = {
        firstName: document.getElementById('firstName').value,
        lastName: document.getElementById('lastName').value,
        // ... collect all other fields
    };
    
    try {
        if (document.getElementById('patientId').value) {
            // Update existing patient
            await patientsAPI.update(patientId, patientData, authToken);
        } else {
            // Create new patient
            await patientsAPI.create(patientData, authToken);
        }
        modal.classList.remove('active');
        loadPatients();
    } catch (error) {
        console.error('Failed to save patient:', error);
        alert('Failed to save patient. Please try again.');
    }
});

document.getElementById('confirmDelete').addEventListener('click', async function() {
    try {
        await patientsAPI.delete(patientToDelete, authToken);
        confirmModal.classList.remove('active');
        loadPatients();
    } catch (error) {
        console.error('Failed to delete patient:', error);
        alert('Failed to delete patient. Please try again.');
    }
});

// Pharmacy API functions
const API_BAURL = 'http://localhost:8080/api'; // Update with your backend URL

// Medicine API
async function fetchMedicines() {
    try {
        const response = await fetch(`${API_BASE_URL}/medicines`);
        if (!response.ok) {
            throw new Error('Failed to fetch medicines');
        }
        return await response.json();
    } catch (error) {
        console.error('Error fetching medicines:', error);
        throw error;
    }
}

async function addMedicine(medicineData) {
    try {
        const response = await fetch(`${API_BASE_URL}/medicines`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(medicineData)
        });
        
        if (!response.ok) {
            throw new Error('Failed to add medicine');
        }
        
        return await response.json();
    } catch (error) {
        console.error('Error adding medicine:', error);
        throw error;
    }
}

async function updateMedicine(medicineId, medicineData) {
    try {
        const response = await fetch(`${API_BASE_URL}/medicines/${medicineId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(medicineData)
        });
        
        if (!response.ok) {
            throw new Error('Failed to update medicine');
        }
        
        return await response.json();
    } catch (error) {
        console.error('Error updating medicine:', error);
        throw error;
    }
}

async function deleteMedicine(medicineId) {
    try {
        const response = await fetch(`${API_BASE_URL}/medicines/${medicineId}`, {
            method: 'DELETE'
        });
        
        if (!response.ok) {
            throw new Error('Failed to delete medicine');
        }
        
        return true;
    } catch (error) {
        console.error('Error deleting medicine:', error);
        throw error;
    }
}

// Prescription API
async function createPrescription(prescriptionData) {
    try {
        const response = await fetch(`${API_BASE_URL}/prescriptions`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(prescriptionData)
        });
        
        if (!response.ok) {
            throw new Error('Failed to create prescription');
        }
        
        return await response.json();
    } catch (error) {
        console.error('Error creating prescription:', error);
        throw error;
    }
}

// Helper function to get patients and doctors for prescription
async function getPatients() {
    try {
        const response = await fetch(`${API_BASE_URL}/patients`);
        if (!response.ok) {
            throw new Error('Failed to fetch patients');
        }
        return await response.json();
    } catch (error) {
        console.error('Error fetching patients:', error);
        throw error;
    }
}

async function getDoctors() {
    try {
        const response = await fetch(`${API_BASE_URL}/doctors`);
        if (!response.ok) {
            throw new Error('Failed to fetch doctors');
        }
        return await response.json();
    } catch (error) {
        console.error('Error fetching doctors:', error);
        throw error;
    }
}

export {
    fetchMedicines,
    addMedicine,
    updateMedicine,
    deleteMedicine,
    createPrescription,
    getPatients,
    getDoctors
};
// API endpoints configuration
const API_BASEURL = 'https://your-medicare-api.com';

export const registerUser = async (userData) => {
    try {
        const response = await fetch(`${API_BASE_URL}/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(userData)
        });
        
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || 'Registration failed');
        }
        
        return await response.json();
    } catch (error) {
        console.error('Registration error:', error);
        throw error;
    }
};
async function fetchAdmissionsData(startDate, endDate) {
    const response = await fetch(`/api/admissions?start=${startDate}&end=${endDate}`);
    return await response.json();
}
document.querySelector('.btn-primary').addEventListener('click', async function() {
    const startDate = document.getElementById('startDate').value;
    const endDate = document.getElementById('endDate').value;
    
    const data = await fetchAdmissionsData(startDate, endDate);
    admissionsChart.data.labels = data.labels;
    admissionsChart.data.datasets[0].data = data.emergency;
    admissionsChart.data.datasets[1].data = data.scheduled;
    admissionsChart.update();
});
