<?php
require_once 'connection.php'; 
header('Content-Type: application/json; charset=utf-8');
$sql = "SELECT * FROM cart LEFT JOIN obat ON cart.obat = obat.id WHERE user = ".$_GET['user'];
$result = mysqli_query($conn, $sql);
$list = array();
while($data = mysqli_fetch_array($result)) {
    array_push($list, $data);
}
echo json_encode($list);
?>