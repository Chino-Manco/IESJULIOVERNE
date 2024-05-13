<?php
include '../conexion.php';

// Obtener los valores de los atributos de la clase Partes a través de la URL
$matricula = $_GET['matricula'];

$sql = "SELECT * FROM Alumnos WHERE matricula=?;";
$consulta = $db->prepare($sql);
$consulta->execute(array($matricula));

if ($consulta->rowCount() == 1){

    // Consulta SQL con marcadores de posición (?)
    $sql = "DELETE FROM Alumnos WHERE matricula=?;";

    // Preparar la consulta
    $consulta = $db->prepare($sql);

    // Ejecutar la consulta con los valores proporcionados
    $consulta->execute(array($matricula));

    $sql = "SELECT matricula FROM Alumnos WHERE matricula=?;";
    $consulta = $db->prepare($sql);
    $consulta->execute(array($matricula));

    if ($consulta->rowCount() == 1){
        $respuesta=2;
    } else {
        $respuesta=1;
    }

} else {
    $respuesta=0;
}
  
header ('Content-Type: application/json');

echo $respuesta;

?>