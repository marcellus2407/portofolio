<?php
require_once 'connection.php'; 
header('Content-Type: application/json; charset=utf-8');
$sql = "INSERT INTO message VALUES 
(null, '".$_POST["sender"]."', '".$_POST["receiver"]."', '".$_POST["message"]."', NOW())";
if (mysqli_query($conn, $sql)) {
    echo json_encode(["response" => true]);
} else { 
    echo json_encode(["response" => false]);
}
?>