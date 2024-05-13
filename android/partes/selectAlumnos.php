<?php
include '../conexion.php';

try {

    // Preparo la consulta 
    $sql = "SELECT * FROM Alumnos";

    $stmt = $db->prepare($sql);

    // Ejecuto la consulta 
    $stmt->execute();

    // Buscar todos los alumnos
    $alumnos = $stmt->fetchAll(PDO::FETCH_ASSOC);
    // Preparar el response
    $response = $alumnos;

    // encabezado en json
    header('Content-Type: application/json');

    // Preparar el response
    echo json_encode($response);

} catch (PDOException $e) {
    echo "Error: " . $e->getMessage();
}
?>