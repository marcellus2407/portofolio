<?php 
    $host = 'localhost';
    $username = 'root';
    $password = '';
    $database = 'telemedicine';
    $conn = mysqli_connect($host, $username, $password, $database);
    if (!$conn) {
        echo json_encode(["response" => "Database Error"]);
        http_response_code(500);
    }
 ?>