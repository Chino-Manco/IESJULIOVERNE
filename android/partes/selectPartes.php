<?php
include '../conexion.php';

try {
    $matricula = $_GET['matricula'];

    // Preparo la consulta 
    $sql = "SELECT i.puntos 
    FROM Partes p 
    LEFT JOIN Incidencias i 
    ON i.cod_incidencia=p.incidencia 
    WHERE p.caducado=0 AND p.matricula_Alumno=?";

    $stmt = $db->prepare($sql);

    // Ejecuto la consulta 
    $stmt->execute([$matricula]);

    // Buscar todos los cursos
    $cursos = $stmt->fetchAll(PDO::FETCH_ASSOC);
    // Preparar el response
    $response = $cursos;

    // encabezado en json
    header('Content-Type: application/json');

    // Preparar el response
    echo json_encode($response);

} catch (PDOException $e) {
    echo "Error: " . $e->getMessage();
}
?>