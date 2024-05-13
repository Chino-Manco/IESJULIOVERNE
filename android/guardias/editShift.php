<?php
    include 'conexion.php';
    $db = conexion();
    $cod_guardias = $_POST['cod_guardias'];
    $fecha = $_POST['fecha'];
    $observaciones = $_POST['observaciones'];
    $periodo = $_POST['periodo'];
    $cod_usuario = $_POST['cod_usuario'];
    
    //Consulta
    $sql = "UPDATE guardias SET fecha = ?, observaciones = ?, periodo = ?, cod_usuario = ? WHERE cod_guardias = ?";
    $stmt = $db->prepare($sql);
    
    //Metodo execute para reemplazar valores 
    $stmt->execute([$fecha, $observaciones, $periodo, $cod_usuario, $cod_guardias]);
    echo "Guardia actualizada correctamente";
?>
