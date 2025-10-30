// Preloader
window.addEventListener('load', function() {
    const preloader = document.querySelector('.preloader');
    setTimeout(() => {
        preloader.classList.add('fade-out');
        setTimeout(() => {
            preloader.style.display = 'none';
        }, 500);
    }, 1000);
});

// Mobile Menu Toggle
const menuToggle = document.querySelector('.menu-toggle');
const navbar = document.querySelector('.navbar');
const sidebarToggle = document.querySelector('.dashboard .menu-toggle');
const sidebar = document.querySelector('.sidebar');

if (menuToggle) {
    menuToggle.addEventListener('click', function() {
        navbar.classList.toggle('active');
        this.classList.toggle('fa-times');
    });
}

if (sidebarToggle) {
    sidebarToggle.addEventListener('click', function() {
        sidebar.classList.toggle('active');
    });
}

// Header Scroll Effect
window.addEventListener('scroll', function() {
    const header = document.querySelector('.header');
    if (window.scrollY > 50) {
        header.classList.add('scrolled');
    } else {
        header.classList.remove('scrolled');
    }
});

// Toggle Password Visibility
const togglePasswordButtons = document.querySelectorAll('.toggle-password');
togglePasswordButtons.forEach(button => {
    button.addEventListener('click', function() {
        const input = this.previousElementSibling;
        const type = input.getAttribute('type') === 'password' ? 'text' : 'password';
        input.setAttribute('type', type);
        this.classList.toggle('fa-eye-slash');
    });
});

// Toggle Login/Register Forms
const showRegister = document.getElementById('showRegister');
const showLogin = document.getElementById('showLogin');
const loginForm = document.getElementById('loginForm');
const registerForm = document.getElementById('registerForm');

if (showRegister && showLogin) {
    showRegister.addEventListener('click', function(e) {
        e.preventDefault();
        loginForm.style.display = 'none';
        registerForm.style.display = 'block';
    });

    showLogin.addEventListener('click', function(e) {
        e.preventDefault();
        registerForm.style.display = 'none';
        loginForm.style.display = 'block';
    });
}

// Form Submissions
if (loginForm) {
    loginForm.addEventListener('submit', function(e) {
        e.preventDefault();
        const username = this.username.value;
        const password = this.password.value;
        
        // Simulate login
        console.log('Login attempt:', { username, password });
        
        // In a real app, you would make an API call here
        // For demo, redirect to dashboard after 1 second
        setTimeout(() => {
            window.location.href = 'dashboard.html';
        }, 1000);
    });
}

if (registerForm) {
    registerForm.addEventListener('submit', function(e) {
        e.preventDefault();
        const username = this.username.value;
        const email = this.email.value;
        const password = this.password.value;
        const confirmPassword = this.confirm_password.value;
        const role = this.role.value;
        
        // Validate passwords match
        if (password !== confirmPassword) {
            alert('Passwords do not match!');
            return;
        }
        
        // Simulate registration
        console.log('Registration:', { username, email, password, role });
        
        // Show success message and switch to login
        alert('Registration successful! Please login.');
        registerForm.style.display = 'none';
        loginForm.style.display = 'block';
        registerForm.reset();
    });
}

// Contact Form Submission
const contactForm = document.getElementById('contactForm');
if (contactForm) {
    contactForm.addEventListener('submit', function(e) {
        e.preventDefault();
        const name = this.name.value;
        const email = this.email.value;
        const subject = this.subject.value;
        const message = this.message.value;
        
        // Simulate form submission
        console.log('Contact form submitted:', { name, email, subject, message });
        
        // Show success message
        alert('Thank you for your message! We will get back to you soon.');
        this.reset();
    });
}

// Dashboard Functions
document.addEventListener('DOMContentLoaded', function() {
    // Load dashboard stats
    if (document.querySelector('.dashboard')) {
        // Simulate loading data
        setTimeout(() => {
            document.getElementById('totalPatients').textContent = '142';
            document.getElementById('totalDoctors').textContent = '28';
            document.getElementById('todayAppointments').textContent = '15';
            document.getElementById('pendingAppointments').textContent = '8';
            
            // Load recent appointments
            const appointments = [
                { patient: 'John Doe', doctor: 'Dr. Robert Johnson', date: '2023-06-15', time: '10:00 AM', status: 'Scheduled' },
                { patient: 'Jane Smith', doctor: 'Dr. Emily Williams', date: '2023-06-15', time: '11:30 AM', status: 'Completed' },
                { patient: 'Michael Brown', doctor: 'Dr. Sarah Lee', date: '2023-06-16', time: '09:00 AM', status: 'Scheduled' },
                { patient: 'Emily Davis', doctor: 'Dr. Robert Johnson', date: '2023-06-16', time: '02:15 PM', status: 'Cancelled' }
            ];
            
            const appointmentsTable = document.querySelector('#recentAppointmentsTable tbody');
            appointments.forEach(appointment => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${appointment.patient}</td>
                    <td>${appointment.doctor}</td>
                    <td>${appointment.date}</td>
                    <td>${appointment.time}</td>
                    <td><span class="status-badge status-${appointment.status.toLowerCase()}">${appointment.status}</span></td>
                    <td>
                        <button class="action-btn view">View</button>
                        <button class="action-btn edit">Edit</button>
                    </td>
                `;
                appointmentsTable.appendChild(row);
            });
            
            // Load recent patients
            const patients = [
                { name: 'John Doe', gender: 'Male', age: '38', phone: '555-1234' },
                { name: 'Jane Smith', gender: 'Female', age: '32', phone: '555-5678' },
                { name: 'Michael Brown', gender: 'Male', age: '45', phone: '555-9012' },
                { name: 'Emily Davis', gender: 'Female', age: '29', phone: '555-3456' }
            ];
            
            const patientsTable = document.querySelector('#recentPatientsTable tbody');
            patients.forEach(patient => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${patient.name}</td>
                    <td>${patient.gender}</td>
                    <td>${patient.age}</td>
                    <td>${patient.phone}</td>
                    <td>
                        <button class="action-btn view">View</button>
                        <button class="action-btn edit">Edit</button>
                    </td>
                `;
                patientsTable.appendChild(row);
            });
            
            // Initialize chart
            const ctx = document.getElementById('appointmentsChart').getContext('2d');
            const chart = new Chart(ctx, {
                type: 'doughnut',
                data: {
                    labels: ['Cardiology', 'Pediatrics', 'Neurology', 'Orthopedics', 'General'],
                    datasets: [{
                        data: [25, 20, 15, 10, 30],
                        backgroundColor: [
                            '#3a7bd5',
                            '#00d2ff',
                            '#ff6b6b',
                            '#28a745',
                            '#ffc107'
                        ],
                        borderWidth: 0
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    cutout: '70%',
                    plugins: {
                        legend: {
                            position: 'right'
                        }
                    }
                }
            });
        }, 500);
    }
    
    // Logout functionality
    const logoutButtons = document.querySelectorAll('#logout');
    logoutButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            // In a real app, you would make an API call to logout
            // For demo, redirect to login page
            window.location.href = 'login.html';
        });
    });
    
    // Initialize all tooltips
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
    // Preloader
window.addEventListener('load', function() {
    const preloader = document.querySelector('.preloader');
    setTimeout(() => {
      preloader.classList.add('fade-out');
      setTimeout(() => {
        preloader.style.display = 'none';
      }, 500);
    }, 1000);
  });
  
  // Mobile Menu Toggle
  const menuToggle = document.querySelector('.menu-toggle');
  const navbar = document.querySelector('.navbar');
  
  if (menuToggle) {
    menuToggle.addEventListener('click', function() {
      navbar.classList.toggle('active');
      this.classList.toggle('fa-times');
    });
  }
  
  // Form Submission
  const loginForm = document.getElementById('loginForm');
  if (loginForm) {
    loginForm.addEventListener('submit', async function(e) {
      e.preventDefault();
      const username = this.username.value;
      const password = this.password.value;
      
      try {
        const response = await authAPI.login(username, password);
        localStorage.setItem('token', response.token);
        window.location.href = 'dashboard.html';
      } catch (error) {
        alert('Login failed: ' + error.message);
      }
    });
  }
  async function fetchAdmissionsData(startDate, endDate) {
    const response = await fetch(`/api/admissions?start=${startDate}&end=${endDate}`);
    return await response.json();
}
});
document.querySelector('.btn-primary').addEventListener('click', async function() {
    const startDate = document.getElementById('startDate').value;
    const endDate = document.getElementById('endDate').value;
    
    const data = await fetchAdmissionsData(startDate, endDate);
    admissionsChart.data.labels = data.labels;
    admissionsChart.data.datasets[0].data = data.emergency;
    admissionsChart.data.datasets[1].data = data.scheduled;
    admissionsChart.update();
});