<?php
include '../conexion.php';

try {

    $grupo = $_GET['grupo'];

    // Preparo la consulta 
    $sql = "SELECT * FROM Alumnos where grupo = ?";
    $stmt = $db->prepare($sql);

    // Ejecuto la consulta 
    $stmt->execute(array($grupo));

    // Buscar todos los cursos
    $Alumnos = $stmt->fetchAll(PDO::FETCH_ASSOC);

    // Preparar el response
    $response = $Alumnos;

    // encabezado en json
    header('Content-Type: application/json');

    // Preparar el response
    echo json_encode($response);

} catch (PDOException $e) {
    echo "Error: " . $e->getMessage();
}
?>