<?php
include '../conexion.php';

// Obtener los valores de los atributos de la clase Partes a través de la URL
$matricula = $_GET['matricula'];
$nombre = $_GET['nombre'];
$apellidos = $_GET['apellidos'];
$grupo = $_GET['grupo'];


$sql = "SELECT * FROM Alumnos WHERE matricula=?;";
$consulta = $db->prepare($sql);
$consulta->execute(array($matricula));

if ($consulta->rowCount() == 0){

    // Consulta SQL con marcadores de posición (?)
    $sql = "INSERT INTO Alumnos (matricula, nombre, apellidos, grupo) 
        VALUES ( ?, ?, ?, ?);";

    // Preparar la consulta
    $consulta = $db->prepare($sql);

    // Ejecutar la consulta con los valores proporcionados
    $consulta->execute(array($matricula, $nombre, $apellidos, $grupo));

    $sql = "SELECT matricula FROM Alumnos WHERE matricula=?;";
    $consulta = $db->prepare($sql);
    $consulta->execute(array($matricula));

    if ($consulta->rowCount() == 0){
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