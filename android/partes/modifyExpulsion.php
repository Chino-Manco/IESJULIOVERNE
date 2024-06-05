<?php
include '../conexion.php';

// Obtener los valores de los atributos de la clase Partes a través de la URL
$cod_expulsion = $_GET['cod_expulsion'];
$fecha_Inicio = $_GET['fecha_Inicio'];
$fecha_Fin = $_GET['fecha_Fin'];
$tipo_expulsion = $_GET['tipo_expulsion'];

$sql = "SELECT * FROM Expulsiones WHERE cod_expulsion=?;";

$consulta = $db->prepare($sql);
$consulta->execute(array($cod_expulsion));


if ($consulta->rowCount() == 1){

    // Consulta SQL con marcadores de posición (?)
    $sql = "UPDATE Expulsiones
        SET fecha_Inicio = ?, fecha_Fin = ?, tipo_expulsion = ?
        WHERE cod_expulsion = ?;";

    // Preparar la consulta
    $consulta = $db->prepare($sql);

    // Ejecutar la consulta con los valores proporcionados
    $consulta->execute(array( $fecha_Inicio, $fecha_Fin, $tipo_expulsion, $cod_expulsion));

    $sql = "SELECT * FROM Expulsiones WHERE cod_expulsion=? AND fecha_Inicio=? AND fecha_Fin=? AND tipo_expulsion=?;";
    $consulta = $db->prepare($sql);
    $consulta->execute(array($cod_expulsion, $fecha_Inicio, $fecha_Fin, $tipo_expulsion));

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