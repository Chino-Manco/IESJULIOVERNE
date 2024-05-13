<?php
    include 'conexion.php';
    $db = conexion();
    $cod_guardias = $_POST['cod_guardias'];
    $sql = "DELETE FROM guardias WHERE cod_guardias = ?";
    $stmt = $db->prepare($sql);
    $stmt->execute([$cod_guardias]);
    echo "Guardia eliminada correctamente";
?>
