<?php
// Iniciamos la sesion
session_start();
// Para acceder a esta pagina hay que iniciar sesion previamente.
require_once ('loginRequerido.php');

// Verificamos que el usuario haya iniciado sesión
if (!isset($_SESSION['usuario_login']['email'])) {
  // Si el usuario no ha iniciado sesión, lo redirigimos al login
  header("Location: logout.php");
  exit();
}
// Si ha iniciado sesión verificamos el rol para entrar en esta página
if ($_SESSION['usuario_login']['rol'] != 0) {
  header("Location: selector.php");
  exit();
}
?>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="../css/bootstrap.min.css">
  <link rel="stylesheet" type="text/css" href="../css/selector.css">

  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
    integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">

  <title>Gestión de Usuarios</title>
  <style>
    .gradient-custom {
      /* fallback for old browsers */
      background: #6a11cb;

      /* Chrome 10-25, Safari 5.1-6 */
      background: -webkit-linear-gradient(to right, rgba(106, 17, 203, 1), rgba(37, 117, 252, 1));

      /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
      background: linear-gradient(to right, rgba(106, 17, 203, 1), rgba(37, 117, 252, 1))
    }
  </style>
</head>

<body class="gradient-custom">

  <header>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">

      <div class="container-fluid">
        <div class="contenedor">
          <a href="selector.php"><img src="../images/logoJulioVerneNuevo.png" alt="" class="logo me-2">
          </a>
          <h1 class="titulo"><a href="selector.php">IES JULIO VERNE</a></h1>

        </div>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"
          data-bs-toggle="dropdown">
          <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse  text-center" id="navbarSupportedContent">
          <ul class="navbar-nav me-auto">
          </ul>
          <ul class="navbar-nav navbar-right">
            <li class="nav-item">
              <?php
              echo '
<a class="text-light btn btn-outline-secondary me-2" href="../archivosComunes/actualizarUsuario.php?idusuario=' . $_SESSION['usuario_login']['cod_usuario'] . '">' ?>
              <span class="d-flex">
                <svg xmlns="http://www.w3.org/2000/svg" style="height: 24px;" fill="none" viewBox="0 0 24 24"
                  stroke="currentColor" stroke-width="2">
                  <path stroke-linecap="round" stroke-linejoin="round"
                    d="M5.121 17.804A13.937 13.937 0 0112 16c2.5 0 4.847.655 6.879 1.804M15 10a3 3 0 11-6 0 3 3 0 016 0zm6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>&nbsp;
                <?php echo $_SESSION['usuario_login']['nombre'] . ' ' . $_SESSION['usuario_login']['apellidos']; ?>
              </span>
              </a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="../archivosComunes/selector.php">Página Principal<i class="bi bi-back"></i></a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="../archivosComunes/adminUsuario.php">Gestión Usuarios <i
                  class="bi bi-back"></i></a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="../archivosComunes/logout.php">Cerrar Sesion</a>
            </li>
          </ul>

        </div>
      </div>
    </nav>
  </header>
  <div class="container-fluid">

    <h1 class="text-light mb-5">Gestión de Usuarios</h1>
    <hr class="text-white-50">
    <?php
    require_once ('conexion.php');
    $contador = 0;
    try {
      // Mostramos los usuarios que no estan autorizados en la app
      $usuarios = 'SELECT nombre, apellidos, email, cod_usuario,rol FROM Usuarios WHERE validar = "no" and rol != 0 ORDER BY nombre, apellidos';

      $usuarios = $db->query($usuarios)->fetchAll();
      echo ('<table class=" table text-light">');

      echo ("<thead>
    <tr>");
      if (count($usuarios) != 0) {
        echo "<td colspan='5' class='text-center td-title'>Usuarios aún sin autorizar</td>";
      } else {
        echo "<td colspan='5' class='text-center td-title'>Usuarios autorizados</td>";

      }
      echo ("</tr>

        <tr>
          <th scope='col'>#</th>
          <td>Nombre</td>
          <td>Apellidos</td>
          <td>Email</td>
          <td>Acción</td>
        </tr>
      </thead>");

      if (count($usuarios) != 0) {



        foreach ($usuarios as $row) {
          $Cod_Usuario = $row['cod_usuario'];
          $rol = $row['rol'];
          $nombre = $row['nombre'];
          $apellidos = $row['apellidos'];
          $email = $row['email'];
          $contador++;

          // Ocultamos en los input las variables obtenidas de los usuarios seleccionados para poder autorizarles denegarles o hacer administradores
          echo "
        <tbody>
          <tr>
            <th scope='row'>$contador</th>
              <td>$nombre</td>
              <td>$apellidos</td>
              <td>$email</td>
              <td>
              <form method='POST' action='autorizar.php'>
                <input type='hidden' name='nombre' value='$nombre'>
                <input type='hidden' name='apellidos' value='$apellidos'>
                <input type='hidden' name='idUsuario' value='$Cod_Usuario'>
                <input type='hidden' name='email' value='$email'>
                <input type='hidden' name='rolUsuario' value='$rol'>
                <button class='btn btn-secondary' name='autorizar'>Autorizar</button> 
                <button class='btn btn-danger' name='denegar'>Denegar</button>
                </form>
              </td>
          </tr>";

        }

      }

      //ahora muestra los usuarios que ya están registrados, para poder hacerlos admin o no
      if (count($usuarios) != 0) {

        echo "<hr class='text-white'>
          <tr><td colspan='5'></td></tr>
          <tr></tr>
          <tr>
          <td colspan='5' class='text-center td-title'>Usuarios autorizados</td></tr>";

      }
      $usuarios = "SELECT nombre, apellidos, email, cod_usuario, rol FROM Usuarios WHERE validar = 'si' and rol != '2' and email != '" . $_SESSION["usuario_login"]["email"] . "' ORDER BY nombre, apellidos";
      $usuarios = $db->query($usuarios)->fetchAll();

      $contador = 0;

      foreach ($usuarios as $row) {
        $Cod_Usuario = $row['cod_usuario'];
        $rol = $row['rol'];
        $nombre = $row['nombre'];
        $apellidos = $row['apellidos'];
        $email = $row['email'];
        $contador++;

        $boton = $rol == 1 ? "<button class='btn btn-success' name='admin'>Hacer administrador</button>" :
          "<button class='btn btn-secondary' name='usuario' >Quitar Administrador</button>";

        // Ocultamos en los input las variables obtenidas de los usuarios seleccionados para poder autorizarles denegarles o hacer administradores
        echo "
        <tr>
          <th scope='row'>$contador</th>
            <td>$nombre</td>
            <td>$apellidos</td>
            <td>$email</td>
            <td>
              <form method='POST' action='autorizar.php'>
                <input type='hidden' name='nombre' value='$nombre'>
                <input type='hidden' name='apellidos' value='$apellidos'>
                <input type='hidden' name='idUsuario' value='$Cod_Usuario'>
                <input type='hidden' name='email' value='$email'>
                <input type='hidden' name='rolUsuario' value='$rol'>
                $boton
                
              </form>
            </td>
        </tr>
      ";
      }

      echo "</tbody>
          </table>";
    } catch (PDOException $e) {
      echo 'Error con la base de datos ' . $e->getMessage();
    }

    ?>
  </div>
  <?php
  include ("footer.php");
  ?>

</body>

</html>