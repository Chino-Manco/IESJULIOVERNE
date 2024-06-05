<?php
include '../conexion.php';

// Obtener los valores de los atributos de la clase Partes a través de la URL
$cod_parte = $_GET['cod_parte'];

$sql = "SELECT * FROM Partes WHERE cod_parte=?;";
$consulta = $db->prepare($sql);
$consulta->execute(array($cod_parte));

if ($consulta->rowCount() == 1){

    // Consulta SQL con marcadores de posición (?)
    $sql = "DELETE FROM Partes WHERE cod_parte=?;";

    // Preparar la consulta
    $consulta = $db->prepare($sql);

    // Ejecutar la consulta con los valores proporcionados
    $consulta->execute(array($cod_parte));

    $sql = "SELECT * FROM Partes WHERE cod_parte=?;";
    $consulta = $db->prepare($sql);
    $consulta->execute(array($cod_parte));

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