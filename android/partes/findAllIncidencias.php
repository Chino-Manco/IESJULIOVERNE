<?php
include '../conexion.php';

try {
    // Prepara la consulta
    $sql = "SELECT * FROM Incidencias";
    $stmt = $db->prepare($sql);

    // Ejecuta la consulta 
    $stmt->execute();

    // Buscar todos los departamentos
    $incidencias = $stmt->fetchAll(PDO::FETCH_ASSOC);

    // Preparar el response
    $response = $incidencias;

    // encabezado en json
    header('Content-Type: application/json');

    // Preparar el response
    echo json_encode($response);

} catch (PDOException $e) {
    echo "Error: " . $e->getMessage();
}
?>