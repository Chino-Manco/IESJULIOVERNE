<?php
include '../conexion.php';

try {
    $matricula = $_GET['matricula'];
    $encontrado = 0;
    // Preparo la consulta 
    $sql = "SELECT * 
    FROM Expulsiones
    WHERE matricula_del_Alumno=?";

    $stmt = $db->prepare($sql);

    // Ejecuto la consulta 
    $stmt->execute([$matricula]);

    if ($stmt->rowCount() > 0){
        $encontrado=1;
    }


    // encabezado en json
    header('Content-Type: application/json');

    // Preparar el response
    echo ($encontrado);

} catch (PDOException $e) {
    echo "Error: " . $e->getMessage();
}
?>