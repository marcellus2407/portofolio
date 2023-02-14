<?php
require_once 'connection.php'; 
header('Content-Type: application/json; charset=utf-8');
$sql = "SELECT * FROM message WHERE (sender = ".$_GET['sender']." AND receiver = ".$_GET['receiver'].") OR (sender = ".$_GET['receiver']." AND receiver = ".$_GET['sender'].") ORDER BY time ASC";
$result = mysqli_query($conn, $sql);
$list = array();
while($data = mysqli_fetch_array($result)) {
    array_push($list, $data);
}
echo json_encode($list);
?>