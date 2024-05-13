<?php
session_start();
require_once ('loginRequerido.php');
?>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="../css/bootstrap.min.css">
  <link rel="stylesheet" type="text/css" href="../css/admintrador.css">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
    integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">

  <title>Usuarios</title>
</head>

<body>
  <header class="header">
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
              <a class="nav-link" href="../archivosComunes/selector.php">Página Principal <i class="bi bi-back"></i></a>
            </li>

            <li class="nav-item">
              <a class="nav-link" href="../archivosComunes/logout.php">Cerrar Sesion</a>
            </li>
          </ul>

        </div>
      </div>
    </nav>
  </header>
  <section style="font-size: 16px;">
    <a href="gestionUsuariosAdmin.php" id="acceso_admin">
      Acceso Admin
      <img src="../images/admin1.webp" alt="Admin1">
      <img src="../images/admin2.webp" alt="Admin2">
    </a>

    <a href="editarPerfil.php" id="acceso_usuario">
      Acceso Usuario
      <img src="../images/usuario1.webp" alt="Usuario1">
      <img src="../images/usuario22.webp" alt="Usuario2">
    </a>

    <!-- Si el usuario que hizo login no es administrador, se le oculta el campo de Acceso admin
      ya que no podra validar, rechazar o hacer admin a cualquier otro usuario -->
    <?php
    if ($_SESSION['usuario_login']['rol'] != 0) {
      print '<script>
            document.getElementById("acceso_admin").style.display="none";
          </script>';
    }
    ?>
  </section>
  <?php
  include ("footer.php");
  ?>

</body>

</html>