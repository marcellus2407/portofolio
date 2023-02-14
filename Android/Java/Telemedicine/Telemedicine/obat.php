<?php
require_once 'connection.php'; 
header('Content-Type: application/json; charset=utf-8');
if(isset($_GET['keyword'])){
    $sql = "SELECT * FROM obat WHERE (name LIKE '%".$_GET['keyword']."%' OR description LIKE '%".$_GET['keyword']."%') AND stock > 0";
}else{
    $sql = "SELECT * FROM obat WHERE stock > 0";
}
$result = mysqli_query($conn, $sql);
$list = array();
while($data = mysqli_fetch_array($result)) {
    array_push($list, $data);
}
echo json_encode($list);
?>