<?php
include '../conexion.php';

// Obtener los valores de los atributos de la clase Partes a través de la URL
$cod_usuario = $_GET['cod_usuario'];
$matricula_del_Alumno = $_GET['matricula_del_Alumno'];
$tipo_expulsion = $_GET['tipo_expulsion'];
$fecha_Insercion = $_GET['fecha_Insercion'];

// Consulta SQL con marcadores de posición (?)
$sql = "INSERT INTO Expulsiones (cod_usuario, matricula_del_Alumno, tipo_expulsion, fecha_Insercion) 
        VALUES ( ?, ?, ?, ?)";

// Preparar la consulta
$consulta = $db->prepare($sql);

// Ejecutar la consulta con los valores proporcionados
$consulta->execute(array($cod_usuario, $matricula_del_Alumno, $tipo_expulsion, $fecha_Insercion));
  
header ('Content-Type: application/json');

?>