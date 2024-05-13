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


if ($consulta->rowCount() == 1){

    // Consulta SQL con marcadores de posición (?)
    $sql = "UPDATE Alumnos
        SET nombre = ?, apellidos = ?, grupo = ?
        WHERE matricula = ?;";

    // Preparar la consulta
    $consulta = $db->prepare($sql);

    // Ejecutar la consulta con los valores proporcionados
    $consulta->execute(array( $nombre, $apellidos, $grupo, $matricula));

    $sql = "SELECT * FROM Alumnos WHERE matricula=? AND nombre=? AND apellidos=? AND grupo=?;";
    $consulta = $db->prepare($sql);
    $consulta->execute(array($matricula, $nombre, $apellidos, $grupo));

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