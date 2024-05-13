<?php
    include 'conexion.php';
    $db = conexion();
    $sql = "SELECT * FROM guardias";
    $stmt = $db->prepare($sql);
    $stmt->execute();
    $guardias = $stmt->fetchAll(PDO::FETCH_ASSOC);
    echo json_encode($guardias);
?>
