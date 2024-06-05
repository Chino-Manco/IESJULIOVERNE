<?php
include '../conexion.php';

// Obtener los valores de los atributos de la clase Partes a través de la URL
$cod_parte = $_GET['cod_parte'];
$cod_usuario = $_GET['cod_usuario'];

$matricula_Alumno = $_GET['matricula_Alumno'];
$incidencia = $_GET['incidencia'];
$materia = $_GET['materia'];
if ($materia==="0"){
    $materia=null;
}
$fecha = $_GET['fecha'];
$hora = $_GET['hora'];
$descripcion = $_GET['descripcion'];
$fecha_Comunicacion = $_GET['fecha_Comunicacion'];
$via_Comunicacion = $_GET['via_Comunicacion'];
$tipo_Parte = $_GET['tipo_Parte'];
$caducado = $_GET['caducado'];

$sql = "SELECT * FROM Partes WHERE cod_parte=?;";

$consulta = $db->prepare($sql);
$consulta->execute(array($cod_parte));

if ($consulta->rowCount() == 1){
    $sql = "UPDATE Partes
        SET cod_usuario = ?, matricula_Alumno = ?, incidencia = ?, materia = ?, fecha = ?, hora = ?, descripcion = ?, fecha_Comunicacion = ?, via_Comunicacion = ?, tipo_Parte = ?, caducado = ?
        WHERE cod_parte = ?;";

    // Preparar la consulta
    $consulta = $db->prepare($sql);

    // Ejecutar la consulta con los valores proporcionados
    $consulta->execute(array($cod_usuario, $matricula_Alumno, $incidencia, $materia, $fecha, $hora, $descripcion, $fecha_Comunicacion, $via_Comunicacion, $tipo_Parte, $caducado, $cod_parte));


    if ($materia===null){
        $sql = "SELECT * FROM Partes WHERE cod_parte=? AND cod_usuario=? AND matricula_Alumno=? AND incidencia=? AND materia IS ? AND fecha=? AND hora=? AND descripcion=? AND fecha_Comunicacion=? AND via_Comunicacion=? AND tipo_Parte=? AND caducado=?;";
    } else {
        $sql = "SELECT * FROM Partes WHERE cod_parte=? AND cod_usuario=? AND matricula_Alumno=? AND incidencia=? AND materia=? AND fecha=? AND hora=? AND descripcion=? AND fecha_Comunicacion=? AND via_Comunicacion=? AND tipo_Parte=? AND caducado=?;";
    }
    $consulta = $db->prepare($sql);
    $consulta->execute(array($cod_parte, $cod_usuario, $matricula_Alumno, $incidencia, $materia, $fecha, $hora, $descripcion, $fecha_Comunicacion, $via_Comunicacion, $tipo_Parte, $caducado));

    if ($consulta->rowCount() == 0){ 
        $respuesta = 2; // No se actualizaron los datos
    } else {
        $respuesta = 1; // Datos actualizados correctamente
    }
} else {
    $respuesta = 0; // No se encontró el registro
}

header('Content-Type: application/json');
echo json_encode($respuesta);
?>
