<?php
require_once 'connection.php'; 
header('Content-Type: application/json; charset=utf-8');
$sql = "SELECT * FROM cart WHERE user = '".$_POST["user"]."' AND obat = ".$_POST["obat"];
$result = mysqli_query($conn, $sql);
if(mysqli_num_rows($result)>0){
    $sql = "UPDATE cart SET quantity = quantity + ".$_POST["quantity"]." WHERE user = '".$_POST["user"]."' AND obat = ".$_POST["obat"];
}else{
    $sql = "INSERT INTO cart VALUES 
        (".$_POST["user"].", ".$_POST["obat"].", ".$_POST["quantity"].")";
}
if (mysqli_query($conn, $sql)) { 
    echo json_encode(["response" => "Menambah ke keranjang berhasil"]);
}else{
    echo json_encode(["response" => "Menambah ke keranjang gagal"]);
}
?>