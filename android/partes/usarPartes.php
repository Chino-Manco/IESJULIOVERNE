<?php
include '../conexion.php';

try{
    // Obtener los valores de los atributos de la clase Partes a través de la URL
    $cod_parte = $_GET['cod_parte'];

        $sql = "UPDATE Partes 
        SET caducado=2
        WHERE cod_parte=?;";

        // Preparar la consulta
        $consulta = $db->prepare($sql);

            // Recorrer el array de códigos
        foreach ($cod_parte as $codigo) {
            // Ejecutar la consulta para cada código
            $consulta->execute([$codigo]);
        }

} catch (PDOException $e) {
    echo "Error: " . $e->getMessage();
}

?>