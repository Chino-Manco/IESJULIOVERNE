<?php
include '../conexion.php';

// Obtener los valores de los atributos de la clase Partes a través de la URL
$cod_usuario = $_GET['cod_usuario'];
$matricula_Alumno = $_GET['matricula_Alumno'];
$incidencia = $_GET['incidencia'];
$materia = $_GET['materia'];
$fecha = $_GET['fecha'];
$hora = $_GET['hora'];
$descripcion = $_GET['descripcion'];
$fecha_Comunicacion = $_GET['fecha_Comunicacion'];
$via_Comunicacion = $_GET['via_Comunicacion'];
$tipo_Parte = $_GET['tipo_Parte'];
$caducado = $_GET['caducado'];

if ($materia==="0"){
        $sql = "INSERT INTO Partes (cod_usuario, matricula_Alumno, incidencia, fecha, hora, descripcion, fecha_Comunicacion, via_Comunicacion, tipo_Parte, caducado) 
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        // Preparar la consulta
        $consulta = $db->prepare($sql);

        // Ejecutar la consulta con los valores proporcionados
        $consulta->execute(array($cod_usuario, $matricula_Alumno, $incidencia, $fecha, $hora, $descripcion, $fecha_Comunicacion, $via_Comunicacion, $tipo_Parte, $caducado));

} else {

        // Consulta SQL con marcadores de posición (?)
        $sql = "INSERT INTO Partes (cod_usuario, matricula_Alumno, incidencia, materia, fecha, hora, descripcion, fecha_Comunicacion, via_Comunicacion, tipo_Parte, caducado) 
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Preparar la consulta
        $consulta = $db->prepare($sql);

        // Ejecutar la consulta con los valores proporcionados
        $consulta->execute(array($cod_usuario, $matricula_Alumno, $incidencia, $materia, $fecha, $hora, $descripcion, $fecha_Comunicacion, $via_Comunicacion, $tipo_Parte, $caducado));
}

echo $materia;
  
header ('Content-Type: application/json');

?>