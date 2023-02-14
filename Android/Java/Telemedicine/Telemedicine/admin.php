<?php
require_once 'connection.php'; 
header('Content-Type: application/json; charset=utf-8');
$sql = "SELECT * FROM user WHERE role = 'Admin'";
$result = mysqli_query($conn, $sql);
$list = array();
while($data = mysqli_fetch_array($result)) {
    array_push($list, $data);
}
echo json_encode($list);
?>