<?php
// Iniciamos la sesion
session_start();
// Para acceder a esta pagina hay que iniciar sesion previamente.
require_once('loginRequerido.php');
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../css/editarUsuario.css">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">

    <title>Editar Perfil</title>
</head>
<body class="gradient-custom">
    <header >
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">

<div class="container-fluid">
<div class="contenedor">
<a href="selector.php"><img src="../images/logoJulioVerneNuevo.png" alt="" class="logo me-2">
      </a>
      <h1 class="titulo"><a href="selector.php">IES JULIO VERNE</a></h1>

</div>

<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"
data-bs-toggle="dropdown">
<span class="navbar-toggler-icon"></span>
</button>

<div class="collapse navbar-collapse  text-center" id="navbarSupportedContent">
<ul class="navbar-nav me-auto">
</ul>
  <ul class="navbar-nav navbar-right">
<li class="nav-item">
<?php 
echo'
<a class="text-light btn btn-outline-secondary me-2" href="../archivosComunes/actualizarUsuario.php?idusuario='.$_SESSION['usuario_login']['cod_usuario'].'">'?>
<span class="d-flex">
    <svg xmlns="http://www.w3.org/2000/svg" style="height: 24px;" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
        <path stroke-linecap="round" stroke-linejoin="round" d="M5.121 17.804A13.937 13.937 0 0112 16c2.5 0 4.847.655 6.879 1.804M15 10a3 3 0 11-6 0 3 3 0 016 0zm6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
    </svg>&nbsp;
   <?php echo $_SESSION['usuario_login']['nombre'].' '.$_SESSION['usuario_login']['apellidos'];?>
</span>                 
</a>
</li> 
<li class="nav-item">
 <a class="nav-link"  href="../archivosComunes/selector.php">Página Principal <i class="bi bi-back"></i></a>
</li>
<li class="nav-item">
 <a class="nav-link"  href="../archivosComunes/adminUsuario.php">Gestión Usuarios<i class="bi bi-back"></i></a>
</li>
<li class="nav-item">
   <a class="nav-link"  href="../archivosComunes/logout.php">Cerrar Sesion</a>
   </li>
</ul>
</nav>
    </header>
    <section>
    <article>

    <!-- TODOS LOS USUARIOS -->
    <?php
      if($_SESSION['usuario_login']['rol'] == 0){

        require_once('conexion.php');
        $contador = 0;
        try {
          // Mostramos los usuarios que no estan autorizados en la app
          $usuarios = 'SELECT * FROM Usuarios ORDER BY nombre, apellidos';
          $usuarios = $db->query($usuarios);
          echo ('<div class="container-fluid">');
          echo ('<table class="table text-light">');
          echo ("<thead>
              <tr>
                <th scope='col'>#</th>
                <td>Nombre</td>
                <td>Apellidos</td>
                <td>Email</td>
                <td>Acción</td>
              </tr>
            </thead>");

          foreach ($usuarios as $row) {
            $Cod_Usuario = $row['cod_usuario'];
            $rol = $row['rol'];
            $nombre = $row['nombre'];
            $apellidos = $row['apellidos'];
            $email = $row['email'];
            $contador++;

            // Ocultamos en los input las variables obtenidas de los usuarios seleccionados para poder autorizarles denegarles o hacer administradores
            echo "
            <form method='POST' action='actualizarUsuario.php?idusuario=$Cod_Usuario'>
              <tbody>
                <tr>
                  <th scope='row'>$contador</th>
                    <td>$nombre</td>
                    <td>$apellidos</td>
                    <td>$email</td>
                    <td>
                      <input type='hidden' name='idUsuario' value='$Cod_Usuario'>
                      <input type='hidden' name='rolUsuario' value='$rol'> 
                      <button class='btn btn-secondary m-1' name='editar'>Editar</button>
                      <a href='borrarUsuario.php?cod=$Cod_Usuario' class='btn btn-danger m-1' name='eliminar' id='eliminar'>Eliminar</a>
                    </td>
                </tr>
                </tbody>
                </form>";
          }
          echo "</table></div>";
        } catch (PDOException $e) {
          echo 'Error con la base de datos ' . $e->getMessage();
      }

      } else {
        header ("Location: actualizarUsuario.php?idusuario=".$_SESSION['usuario_login']['cod_usuario']."");
      }

      if(isset($_GET['admin'])){
        print('<script>
                  alert("Este usuario no se puede eliminar");
              </script>');
      }
    ?>
    </article>
    </section>
    <!-- FIN TODOS LOS USUARIOS -->
    <?php
        include("footer.php");
      ?>
