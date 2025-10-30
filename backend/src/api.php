<?php
header("Content-Type: application/json");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: GET, POST");
header("Access-Control-Allow-Headers: Content-Type");

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "hospital_management";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Get dashboard statistics
if ($_SERVER['REQUEST_METHOD'] === 'GET' && isset($_GET['action'])) {
    $action = $_GET['action'];
    
    switch ($action) {
        case 'get_stats':
            $stats = [];
            
            // Total patients
            $result = $conn->query("SELECT COUNT(*) as total FROM patients");
            $stats['total_patients'] = $result->fetch_assoc()['total'];
            
            // Current admissions
            $result = $conn->query("SELECT COUNT(*) as total FROM admissions WHERE status = 'Admitted'");
            $stats['current_admissions'] = $result->fetch_assoc()['total'];
            
            // Bed occupancy
            $result = $conn->query("SELECT COUNT(*) as total FROM beds WHERE status = 'Occupied'");
            $occupied = $result->fetch_assoc()['total'];
            $result = $conn->query("SELECT COUNT(*) as total FROM beds");
            $total_beds = $result->fetch_assoc()['total'];
            $stats['bed_occupancy'] = round(($occupied / $total_beds) * 100, 2);
            
            echo json_encode($stats);
            break;
            
        case 'get_admissions':
            $timeframe = $_GET['timeframe'] ?? 'weekly';
            $data = [];
            $labels = [];
            
            if ($timeframe === 'weekly') {
                $result = $conn->query("
                    SELECT DAYNAME(admission_date) as day, COUNT(*) as count 
                    FROM admissions 
                    WHERE admission_date >= DATE_SUB(NOW(), INTERVAL 1 WEEK)
                    GROUP BY DAYNAME(admission_date)
                    ORDER BY admission_date
                ");
                
                while ($row = $result->fetch_assoc()) {
                    $labels[] = $row['day'];
                    $data[] = $row['count'];
                }
            } elseif ($timeframe === 'monthly') {
                // Similar query for monthly data
            }
            
            echo json_encode(['labels' => $labels, 'data' => $data]);
            break;
            
        case 'get_departments':
            $result = $conn->query("SELECT dept_name, dept_id FROM departments");
            $departments = [];
            while ($row = $result->fetch_assoc()) {
                $departments[] = $row;
            }
            echo json_encode($departments);
            break;
            
        case 'get_bed_status':
            $result = $conn->query("
                SELECT d.dept_name, b.status, COUNT(*) as count 
                FROM beds b
                JOIN departments d ON b.dept_id = d.dept_id
                GROUP BY d.dept_name, b.status
            ");
            $bedStatus = [];
            while ($row = $result->fetch_assoc()) {
                $bedStatus[] = $row;
            }
            echo json_encode($bedStatus);
            break;
            
        default:
            echo json_encode(['error' => 'Invalid action']);
    }
}

$conn->close();
?>