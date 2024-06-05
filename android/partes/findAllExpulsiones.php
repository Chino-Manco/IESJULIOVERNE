<?php
include '../conexion.php';

try {
    // Preparo la consulta 
    $sql = "SELECT     
    e.cod_expulsion, 
    e.cod_usuario, 
    e.matricula_del_Alumno, 
    e.fecha_Inicio, 
    e.Fecha_Fin, 
    e.tipo_expulsion, 
    e.fecha_Insercion,
    a.matricula, a.nombre, a.apellidos, a.grupo 
    FROM Expulsiones e
    JOIN Alumnos a ON e.matricula_del_Alumno = a.matricula
    ORDER BY e.fecha_Insercion DESC";



    $stmt = $db->prepare($sql);

    // Ejecuto la consulta 
    $stmt->execute();

    // Buscar todos
    $respuesta = $stmt->fetchAll(PDO::FETCH_ASSOC);
    // Preparar el response
    $response = $respuesta;

    // encabezado en json
    header('Content-Type: application/json');

    // Preparar el response
    echo json_encode($response);

} catch (PDOException $e) {
    echo "Error: " . $e->getMessage();
}
?>