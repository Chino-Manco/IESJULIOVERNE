<?php
include '../conexion.php';

try {

    $curso = $_GET['curso'];

    // Preparo la consulta 
    $sql = "SELECT * FROM Asignaturas where curso = ?";
    $stmt = $db->prepare($sql);

    // Ejecuto la consulta 
    $stmt->execute(array($curso));

    // Buscar todos los cursos
    $Asignaturas = $stmt->fetchAll(PDO::FETCH_ASSOC);

    // Preparar el response
    $response = $Asignaturas;

    // encabezado en json
    header('Content-Type: application/json');

    // Preparar el response
    echo json_encode($response);

} catch (PDOException $e) {
    echo "Error: " . $e->getMessage();
}
?>