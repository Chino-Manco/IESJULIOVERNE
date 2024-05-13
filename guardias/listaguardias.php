<?php
// Iniciamos la sesion
require_once("../archivosComunes/conexion.php");
// Para acceder a esta pagina hay que iniciar sesion previamente.
require_once('../archivosComunes/loginRequerido.php');

$select = "SELECT cod_guardias,observaciones, fecha, Guardias.cod_usuario AS cod_usuario,
Usuarios.nombre AS nombre, Usuarios.apellidos AS apellidos, Usuarios.cod_delphos AS delphos, Periodos.inicio AS periodoinicio, Periodos.fin AS periodofin
            FROM Guardias
            JOIN Periodos ON Guardias.periodo = Periodos.cod_periodo
            JOIN Usuarios ON Guardias.cod_usuario = Usuarios.cod_usuario
            ORDER BY fecha DESC, periodoinicio DESC;";
$resul = $db->query($select);
$i=0;
// Utilizamos un bucle while para recorrer todas las filas que devuelve la consulta
while ($columna = $resul->fetch(PDO::FETCH_ASSOC)) {
    $usuario =  $columna['cod_usuario'];
    $delphos =  $columna['delphos'];
    $i = $i+1;
    echo "<tr id='".$i."'>";
    // Recorremos las columnas de la fila actual
    echo '<td>'.$columna['fecha'].'</td>';
    echo '<td>'.substr($columna['periodoinicio'], 0, strlen($columna['periodoinicio']) - 3)." - ".substr($columna['periodofin'], 0, strlen($columna['periodofin']) - 3).'</td>';
    $dia   = substr($columna['fecha'],8,2);
    $mes = substr($columna['fecha'],5,2);
    $anio = substr($columna['fecha'],0,4); 
    $semana = date('w',  mktime(0,0,0,$mes,$dia,$anio));  
    $semanaGuardia="";
    if($semana == 1){
        $semanaGuardia="Lunes";
    }else if($semana == 2){
        $semanaGuardia="Martes";
    }else if($semana == 3){
        $semanaGuardia="Miércoles";
    }else if($semana == 4){
        $semanaGuardia="Jueves";
    }else if($semana == 5){
        $semanaGuardia="Viernes";
       
    }else{
        echo '<td> </td>';
    }
    if($semana >=1 && $semana <=5){
        $select1 = "SELECT clase
        FROM Horarios
        WHERE (cod_usuario = ? OR cod_delphos = ?) AND inicio = ? AND fin = ? AND  dia = ?;";

        $resul1 = $db->prepare($select1);
        //Pasar a traves de un array los valores escritos en el formulario
        //Los valores se recogen por parametros en la función
        $resul1->execute(array( intval($usuario),$delphos, $columna['periodoinicio'], $columna['periodofin'], $semanaGuardia));  
        if ($columna1 = $resul1->fetch(PDO::FETCH_ASSOC)) {
            if ($columna1['clase']!="" && $columna1['clase']!=null){
                echo '<td>'.$columna1['clase'].'</td>';
                }else{
                    echo '<td></td>';
                    echo '<script>document.getElementById('.$i.').style.display="none"</script>';        
                }
        }else{
            echo '<td></td>';
            echo '<script>document.getElementById('.$i.').style.display="none"</script>';
        }
    }
    echo '<td>'.$columna['nombre'].' '.$columna['apellidos'].'</td>';
    echo '<td style="max-width:300px;">'.$columna['observaciones'].'</td>';
    echo '<td> <a href="#"  class="btn-editar" onclick="editarGuardia('.$columna['cod_guardias'].')"><i class="fa-solid fa-pencil"></i></a> </td>';
    echo '<td> <a href="#"  class="btn-borrar" onclick="eliminarGuardia('.$columna['cod_guardias'].')"><i class="fa-solid fa-trash"></i></a> </td>';
    echo "</tr>";
}
?>
