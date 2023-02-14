<?php
require_once 'connection.php'; 
header('Content-Type: application/json; charset=utf-8');
$sql = "SELECT id FROM user WHERE email = '".$_POST['email']."' AND password = md5('".$_POST['password']."')";
$result = mysqli_query($conn, $sql);
if(mysqli_num_rows($result)>0){
    $row = mysqli_fetch_array($result);
    echo json_encode(["response" => $row['id']]);
} else {
    echo json_encode(["response" => -1]);
}
?>