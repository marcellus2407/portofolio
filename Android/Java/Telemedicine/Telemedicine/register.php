<?php
require_once 'connection.php'; 
header('Content-Type: application/json; charset=utf-8');
$sql = "SELECT email FROM user WHERE email = '".$_POST["email"]."'";
$result = mysqli_query($conn, $sql);
if(mysqli_num_rows($result)>0){
    echo json_encode(["response" => "Email telah dipakai"]);
}else{
    $sql = "INSERT INTO user VALUES 
        (null, '".$_POST["email"]."', md5('".$_POST["password"]."'), '".$_POST["name"]."', '".$_POST["role"]."')";
    if (mysqli_query($conn, $sql)) {
        if(isset($_POST['specialist'])){
            $sql = "INSERT INTO dokter VALUES (".mysqli_insert_id($conn).",'".$_POST['specialist']."')";
            if (mysqli_query($conn, $sql)) {
                echo json_encode(["response" => "Register berhasil"]);
            }else{
                echo json_encode(["response" => "Register gagal"]);
            }
        }else{
            echo json_encode(["response" => "Register berhasil"]);
        }
    } else { 
        echo json_encode(["response" => "Register gagal"]);
    }
}
?>