<?php
require_once 'connection.php'; 
header('Content-Type: application/json; charset=utf-8');
$sql = "SELECT obat.stock, cart.quantity FROM cart LEFT JOIN obat ON cart.obat = obat.id WHERE user = ".$_POST['user'];
$result = mysqli_query($conn, $sql);
$kekurangan = false;
while($data = mysqli_fetch_array($result)) {
    if($data['stock']<$data['quantity']){
        $kekurangan=true;
        break;
    }
}
if($kekurangan){
    echo json_encode(["response" => "Masih ada yang kekurangan stok, hapus yang kekurangan stok"]);
}else{
    $sql = "INSERT INTO orders VALUES (null, ".$_POST['user'].", '".$_POST['alamat']."',NOW())";
    mysqli_query($conn, $sql);
    $order_id = mysqli_insert_id($conn);
    $sql = "SELECT * FROM cart WHERE user = ".$_POST['user'];
    $result = mysqli_query($conn, $sql);
    while($data = mysqli_fetch_array($result)) {
        $sql = "INSERT INTO order_details VALUES ($order_id, ".$data['obat'].", ".$data['quantity'].")";
        mysqli_query($conn, $sql);
        $sql = "UPDATE obat SET stock = stock - ".$data['quantity']." WHERE id = ".$data['obat'];
        mysqli_query($conn, $sql);
    }
    $sql = "DELETE FROM cart WHERE user = ".$_POST['user'];
    if(mysqli_query($conn, $sql)){
        $sql = "SELECT * FROM orders WHERE id = ".$order_id;
        $result = mysqli_query($conn, $sql);
        $data = mysqli_fetch_array($result);
        echo json_encode(["response" => true, "order"=>$data]);
    }else{
        echo json_encode(["response" => false]);
    }
}
?>