<?php
// Ficheros necesarios para acceder. Login y conexion a BBDD
session_start();
require_once('funciones/loginRequerido.php');
require_once('../../archivosComunes/conexion.php');
$contador = 0;
?>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" />
  <link rel="stylesheet" href="../css/bootstrap.min.css">
  <link rel="stylesheet" href="../css/styles.css">
  <title>Incidencias Admin</title>
</head>

<body>
  <?php
  require_once('funciones/header.php');
  ?>
  <article>
    <section>
      <div class="container px-4 py-5" id="Incidencias">
        <h2 class="pb-2 mb-5 border-bottom">Incidencias</h2>
        <div class="p-4 rounded-3" style="background: #F5F4F4">
          <!-- <div class="row row-cols-1 row-cols-lg-3 align-items-stretch g-4 py-5"> -->
            
          <div>
              <?php
              require_once("funciones/leyenda.php");
              ?>
               <div class="input-container mb-4 d-flex justify-content-between">

              <input type="search" placeholder="Buscar incidencia" id="filtro" />
              <a href="crearInci.php" class="btn float" type="button" style="background-color: rgba(0, 0, 0, 0.2);">Crear Incidencia</a>
              </div>
            </div>
            <?php

            try {
              $dep_usu = $_SESSION["usuario_login"]["departamento"]; //Cogemos el departamento del usuario
              $nomb_usu = $_SESSION["usuario_login"]["cod_usuario"]; //Cogemos el cod_usu del usuario
              $rolAdmin = $_SESSION["usuario_login"]["rol"];
              $emailAdmin = $_SESSION["usuario_login"]["email"];
              $consulta_admin = "SELECT * FROM Usuarios WHERE rol = '$rolAdmin'";
              $consulta_admin = $db->prepare($consulta_admin);
              $consulta_admin->execute();

              $consulta = "SELECT referencia FROM Departamentos WHERE codigo = (SELECT departamento FROM Usuarios WHERE rol = 0 AND email = '$emailAdmin')";
              $consulta = $db->prepare($consulta);
              $consulta->execute();
              foreach ($consulta as $nombre) {
                $referencia_departamento = $nombre["referencia"];
              }

              if ($consulta_admin->rowCount() > 0 && $referencia_departamento == "inf") {
                $incidencias = "SELECT * FROM Tareas where tipo_incidencia='informatica' AND cod_usuario_gestion = '$nomb_usu' ORDER BY fecha_inicio DESC";
                $incidencias = $db->query($incidencias);
                if ($consulta_dep = "informatica") {
                  echo ('<div id="tablaScript" class="tablaInci table-responsive-lg"> <table class="table table-striped table-hover text-center">');
                  echo ("<thead>
                         <tr> 
                           <th scope='col'>#</th>
                           <td>Estado Tarea</td>
                           <td>Nivel Tarea</td>
                           <td>Localización</td>
                           <td>Fecha Inicio</td>
                           <td>Fecha Fin</td>
                           <td>Imagen</td>
                           <td>Descripción</td>
                           <td>Comentario</td>
                           <td>Tarea creada por</td>
                           <td>Tarea asignada a</td>
                           <td>Tipo</td>
                           <td>Editar</td>
                         </tr>
                         </thead>");
                  foreach ($incidencias as $row) {
                    // Sacamos todos los elementos de la tabla Tareas
                    $estadoTarea = $row["estado"];
                    $cod_Tarea = $row["cod_tarea"];
                    $nivel_tarea = $row["nivel_tarea"];
                    $descripcion = $row["descripcion"];
                    $comentario = $row["comentario"];
                    $imagen = $row["imagen"];
                    $localizacion = $row["localizacion"];
                    $fecha_inicio  = $row["fecha_inicio"];
                    $fecha_fin = $row["fecha_fin"];
                    $cod_usuario_gestion = $row["cod_usuario_gestion"];
                    $cod_usuario_crea = $row["cod_usuario_crea"];
                    $tipo_inci = $row["tipo_incidencia"];
                    $contador++;
                    // Subconsulta para sacar el nombre del cod usuarui gestion de la tabla tareas.
                    $subConsultaNombre = "SELECT nombre, apellidos FROM Usuarios WHERE cod_usuario = (SELECT cod_usuario_gestion FROM Tareas WHERE cod_tarea = '$cod_Tarea')";
                    $subConsultaNombre = $db->query($subConsultaNombre);
                    foreach ($subConsultaNombre as $row2) {
                      $nombreAsignado = $row2["nombre"];
                      $apellidoAsignado = $row2["apellidos"];

                    }
                    // Subconsulta para sacar el nombre del cod usuario que ha creado la tarea
                    $subConsultaNombreCrea = "SELECT nombre, apellidos FROM Usuarios WHERE cod_usuario = (SELECT cod_usuario_crea FROM Tareas WHERE cod_tarea = '$cod_Tarea')";
                    $subConsultaNombreCrea = $db->query($subConsultaNombreCrea);
                    foreach ($subConsultaNombreCrea as $row2) {
                      $nombreCrea = $row2["nombre"];
                      $apellidoCrea = $row2["apellidos"];

                    }
                    echo " <tbody>";
                    // Condicional para pintar las segun el estado de la tarea
                    if ($estadoTarea === "Realizada") {
                      echo "<tr class='table-success fila-tabla' data-fila='$contador'>";
                    } else if ($estadoTarea === "En Proceso") {
                      echo "<tr class='table-warning fila-tabla' data-fila='$contador'>";
                    } else if ($estadoTarea === "Asignada") {
                      echo "<tr class='table-primary fila-tabla' data-fila='$contador'>";
                    } else if ($estadoTarea === "Rechazada") {
                      echo "<tr class='table-danger fila-tabla' data-fila='$contador'>";
                    } else {
                      echo "<tr class='fila-tabla' data-fila='$contador'>";
                    }
                    echo "<th scope='row'>$contador</th>
                            <td>$estadoTarea</td>
                            <td>$nivel_tarea</td>
                            <td style='width: 120px;'>$localizacion</td>
                            <td style='width: 120px;'>$fecha_inicio</td>
                            <td style='width: 120px;'>$fecha_fin</td>
                            <td>";
                    ?>
                      <img src=data:image/jpg;base64,<?php echo base64_encode($imagen) ?> class="zoom" style="width:50px; " />
            <?php
              echo "</td>
                    <td class='comentario'>$descripcion</td>
                    <td class='comentario'>$comentario</td>
                    <td>$nombreCrea $apellidoCrea</td>
                    <td>$nombreAsignado $apellidoAsignado</td>
                    <td>$tipo_inci</td>";
              // Condicional para deshabilitar el boton si el estado esta en realizada
              if($estadoTarea === "Realizada"){
                echo " 
                <form method='post' action='editarIncidencia.php'>
                 <input type='hidden' name='codigoTarea' value='$cod_Tarea'>
                 <input type='hidden' name='comentario' value='$comentario'>
                 <input type='hidden' name='descripcion' value='$descripcion'>
                 <td><button class='btn disabled' name='asignarInci'><span class='material-symbols-outlined'>edit_note</span></button></td>
               </form>
             </tr>
           </tbody>";
              }else{
                echo " 
                <form method='post' action='editarIncidencia.php'>
                 <input type='hidden' name='codigoTarea' value='$cod_Tarea'>
                 <input type='hidden' name='comentario' value='$comentario'>
                 <input type='hidden' name='descripcion' value='$descripcion'>
                 <td><button class='btn' name='asignarInci'><span class='material-symbols-outlined'>edit_note</span></button></td>
               </form>
             </tr>
           </tbody>";
              }
                       }
                       echo "</table></div>";
                     }
                   }
                 } catch (PDOException $e) {
                  echo 'Error con la base de datos ' . $e->getMessage();
              }
            ?>
           <div class="d-flex justify-content-between mt-5">
            <a href="crearInci.php" class="btn float" type="button" style="background-color: rgba(0, 0, 0, 0.2);">Crear Incidencia</a>
            <nav>
              <ul class="pagination mr-2" id="paginacion"></ul>
            </nav>
            <a href="funciones/pdf.php" type="button" class="btn float" style="background-color: rgba(0, 0, 0, 0.2);">Formato PDF</button></a>
          </div>
          </div>
          
        </div>
      </div>
    </section>
  </article>
 
  <?php
    require_once('funciones/footer.php');
  ?>
  