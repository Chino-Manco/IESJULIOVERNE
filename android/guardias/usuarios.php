<?php
include '../conexion.php';

try {

    // Preparo la consulta 
    $sql = "SELECT  u.cod_usuario, u.dni, u.nombre, u.apellidos, u.email, u.clave, u.rol, u.cod_delphos, u.validar, d.codigo AS departamento_codigo, d.nombre AS departamento_nombre, u.tutor_grupo
    FROM Usuarios u
    LEFT JOIN Departamentos d ON u.departamento = d.codigo
    WHERE rol IN('0','1')";

    $stmt = $db->prepare($sql);

    // Ejecuto la consulta 
    $stmt->execute();

    // Buscar todos los cursos
    $Usuarios = $stmt->fetchAll(PDO::FETCH_ASSOC);

    // Preparar el response
    $response = $Usuarios;

    // encabezado en json
    header('Content-Type: application/json');

    // Preparar el response
    echo json_encode($response);

} catch (PDOException $e) {
    echo "Error: " . $e->getMessage();
}
?>