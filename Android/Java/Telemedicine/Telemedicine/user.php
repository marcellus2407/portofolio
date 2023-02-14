<?php
require_once 'connection.php'; 
header('Content-Type: application/json; charset=utf-8');
$sql = "SELECT * FROM user WHERE id = ".$_GET["id"];
$result = mysqli_query($conn, $sql);
$data = mysqli_fetch_array($result);
echo json_encode($data);
?>