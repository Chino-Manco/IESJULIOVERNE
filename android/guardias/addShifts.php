<?php
include '../conexion.php';

// Obtener los valores de los atributos de la clase Partes a través de la URL
$cod_usuario = $_GET['cod_usuario'];
$cod_periodo = $_GET['cod_periodo'];
$observaciones = $_GET['observaciones'];
$fecha = $_GET['fecha'];

// Consulta SQL con marcadores de posición (?)
$sql = "INSERT INTO Guardias (cod_usuario, periodo, observaciones, fecha) 
        VALUES (?, ?, ?, ?)";

// Preparar la consulta
$consulta = $db->prepare($sql);

// Ejecutar la consulta con los valores proporcionados
$consulta->execute(array($cod_usuario, $cod_periodo, $observaciones, $fecha));
  
header ('Content-Type: application/json');

?>