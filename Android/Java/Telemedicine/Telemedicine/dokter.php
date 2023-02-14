<?php
require_once 'connection.php'; 
header('Content-Type: application/json; charset=utf-8');
if(isset($_GET['keyword'])){
    $sql = "SELECT * FROM user LEFT JOIN dokter ON user.id = dokter.id WHERE role = 'Dokter' AND (name LIKE '%".$_GET['keyword']."%' OR email LIKE '%".$_GET['keyword']."%')";
}else{
    $sql = "SELECT * FROM user LEFT JOIN dokter ON user.id = dokter.id WHERE role = 'Dokter'";
}
$result = mysqli_query($conn, $sql);
$list = array();
while($data = mysqli_fetch_array($result)) {
    array_push($list, $data);
}
echo json_encode($list);
?>