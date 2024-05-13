<?php
include '../conexion.php';

try {
    // Prepara la consulta
    $sql = "SELECT codigo, nombre FROM Departamentos";
    $stmt = $db->prepare($sql);

    // Ejecuta la consulta 
    $stmt->execute();

    // Buscar todos los departamentos
    $departamentos = $stmt->fetchAll(PDO::FETCH_ASSOC);

    // Preparar el response
    $response =$departamentos;

    // encabezado en json
    header('Content-Type: application/json');

    // Preparar el response
    echo json_encode($response);

} catch (PDOException $e) {
    echo "Error: " . $e->getMessage();
}
?>