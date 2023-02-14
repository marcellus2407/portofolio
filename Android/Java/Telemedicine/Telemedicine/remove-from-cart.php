<?php
require_once 'connection.php'; 
header('Content-Type: application/json; charset=utf-8');
$sql = "DELETE FROM cart WHERE user = '".$_POST["user"]."' AND obat = ".$_POST["obat"];
if (mysqli_query($conn, $sql)) { 
    echo json_encode(["response" => true]);
}else{
    echo json_encode(["response" => false]);
}
?>