<?php
require_once 'connection.php'; 
header('Content-Type: application/json; charset=utf-8');
$sql = "SELECT user.* FROM user 
JOIN message ON user.id = message.receiver
WHERE message.sender = ".$_GET['id']."
UNION
SELECT user.* FROM user
JOIN message ON user.id = message.sender
WHERE message.receiver = ".$_GET['id'];
$result = mysqli_query($conn, $sql);
$list = array();
while($data = mysqli_fetch_array($result)) {
    array_push($list, $data);
}
echo json_encode($list);
?>