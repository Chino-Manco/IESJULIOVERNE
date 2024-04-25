<?php

    //Recojo todos los datos de la base de datos para hacer la conexion
    // TENGO DE CONTRASEÑA ROOT  POR SI TIENES QUE PROBAR
    $conexion = "mysql:dbname=proyectodaw;host:127.0.0.1";
    $usuario = "root";
    $password = "";


    try {
        //Hago la conexion a la base de datos
        $db= new PDO($conexion,$usuario, $password);

        return $db;

    } catch (PDOException $e) {
        echo "Error en la base de datos ".$e->getMessage();
    }

?>