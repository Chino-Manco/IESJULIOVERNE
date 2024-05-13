<?php
// Iniciamos la sesion
session_start();
// Para acceder a esta pagina hay que iniciar sesion previamente.
require_once ('loginRequerido.php');
?>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Usuarios</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../css/editarUsuario.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">

    <style>
        .gradient-custom {
            /* fallback for old browsers */
            background: #6a11cb;

            /* Chrome 10-25, Safari 5.1-6 */
            background: -webkit-linear-gradient(to right, rgba(106, 17, 203, 1), rgba(37, 117, 252, 1));

            /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
            background: linear-gradient(to right, rgba(106, 17, 203, 1), rgba(37, 117, 252, 1))
        }

        label {
            margin-top: 10px;
        }
    </style>
</head>

<body class="gradient-custom">
    <?php
    // Recuperamos los datos del usuario logueado de la base de datos
    require_once ('conexion.php');

    // Seleccionamos todo del usuario que se envia en input del formulario como tipo hidden
    $select = "SELECT * FROM Usuarios WHERE cod_usuario = " . $_GET['idusuario'] . "";
    $resul = $db->query($select);
    // Guardamos los datos de la consulta anterior en una session.
    $_SESSION["usu_editar"] = $resul->fetch();
    ?>
    <header>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">

            <div class="container-fluid">
                <div class="contenedor">
                    <a href="selector.php"><img src="../images/logoJulioVerneNuevo.png" alt="" class="logo me-2">
                    </a>
                    <h1 class="titulo"><a href="selector.php">IES JULIO VERNE</a></h1>

                </div>

                <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                    aria-expanded="false" aria-label="Toggle navigation" data-bs-toggle="dropdown">
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
                                <svg xmlns="http://www.w3.org/2000/svg" style="height: 24px;" fill="none"
                                    viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                                    <path stroke-linecap="round" stroke-linejoin="round"
                                        d="M5.121 17.804A13.937 13.937 0 0112 16c2.5 0 4.847.655 6.879 1.804M15 10a3 3 0 11-6 0 3 3 0 016 0zm6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
                                </svg>&nbsp;
                                <?php echo $_SESSION['usuario_login']['nombre'] . ' ' . $_SESSION['usuario_login']['apellidos']; ?>
                            </span>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="../archivosComunes/selector.php">Página Principal <i
                                    class="bi bi-back"></i></a>
                        </li>
                        <?php
                        if ($_SESSION['usuario_login']['rol'] == "0") {
                            ?>
                            <li class="nav-item">
                                <a class="nav-link" href="../archivosComunes/editarPerfil.php">Gestión Usuarios <i
                                        class="bi bi-back"></i></a>
                            </li>
                            <?php
                        }
                        ?>


                        <li class="nav-item">
                            <a class="nav-link" href="../archivosComunes/logout.php">Cerrar Sesion</a>
                        </li>
                    </ul>

                </div>
            </div>
        </nav>
    </header>
    <section>
        <article>
            <div class="container px-4 py-5" id="editarPerfil">
                <h2 class="pb-2 border-bottom text-light">Editar Perfil</h2>
                <!-- Este formulario al dar a enviar lleva al archivo actualizar.php pasando por el metodo GET
                el id del usuario -->
                <form method="POST" class="text-light"
                    action="actualizar.php?idusuario=<?php echo $_SESSION['usu_editar']['cod_usuario'] ?>">
                    <div class="row">
                        <div class="col-md-6">
                            <label for="validacionEmail" class="form-label">Email</label>
                            <input type="email" class="form-control" id="validacionEmail" placeholder="Introduce email "
                                name="validacionEmail" value='<?php echo $_SESSION["usu_editar"]["email"] ?>'>
                        </div>

                        <div class="col-md-6">
                            <label for="password" class="form-label">Contraseña</label>
                            <input type="password" class="form-control" id="password"
                                placeholder=" Introduce contraseña" name="password"
                                value='<?php echo $_SESSION["usu_editar"]["clave"] ?>'>
                        </div>
                        <!-- El administrador no debe cambiar ni ver la contraseña de otros usuarios -->
                        <?php
                        /* Cuando el email del usuario que se va a editar sea distinto al email con el que se hizo login
                        el input de contraseña se vuelve de solo lectura, al ser input de tipo password no se ve ni deja copiar
                        el valor que tiene el campo */
                        if ($_SESSION["usu_editar"]["email"] != $_SESSION["usuario_login"]["email"]) {
                            print "<script>
                                    document.getElementById('password').readOnly = true;
                                </script>";
                        }
                        ?>
                    </div>

                    <div class="row">
                        <div class="col-md-6">
                            <label for="validacionNombre" class="form-label">Nombre</label>
                            <input type="text" class="form-control" id="validacionNombre" placeholder="Introduce Nombre"
                                name="validacionNombre" value='<?php echo $_SESSION["usu_editar"]["nombre"] ?>'>
                        </div>

                        <div class="col-md-6">
                            <label for="validacionApellidos" class="form-label">Apellidos</label>
                            <input type="text" class="form-control" id="validacionApellidos"
                                placeholder="Introduce Apellidos" name="validacionApellidos"
                                value='<?php echo $_SESSION["usu_editar"]["apellidos"] ?>'>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6">
                            <label for="validacionDni" class="form-label">DNI</label>
                            <input type="text" class="form-control" id="validacionDni" placeholder="Introduce DNI"
                                name="validacionDni" value='<?php echo $_SESSION["usu_editar"]["dni"] ?>'>
                        </div>
                        <?php
                        if ($_SESSION["usuario_login"]["rol"] == "0" || $_SESSION["usuario_login"]["rol"] == "1") {
                            ?>
                            <div class="col-md-6">
                                <label for="validacionDelphos" class="form-label">Código Delphos</label>
                                <input type="text" class="form-control" id="validacionDelphos"
                                    placeholder="Introduce el código de Delphos" name="validacionDelphos"
                                    value='<?php echo $_SESSION["usu_editar"]["cod_delphos"] ?>'>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <label for="validacionDepartamento" class="form-label">Departamento</label>
                                <?php
                                require_once ('conexion.php');
                                if ($_SESSION["usu_editar"]["departamento"] != null) {
                                    $departamento = $_SESSION["usu_editar"]["departamento"];
                                    $consulta = "SELECT * FROM Departamentos WHERE codigo = ?";
                                    $consulta = $db->prepare($consulta);
                                    $consulta->execute(array($departamento));
                                    $consulta_departamento = $consulta->fetch();
                                    $dpto_usuario = $consulta_departamento['nombre'];
                                }
                                ?>

                                <select name="validacionDepartamento" id="validacionDepartamento"
                                    class="form-control form-control-lg">
                                    <option name="dpto_no_valido" id="dpto_no_valido">- Seleccione su departamento -
                                    </option>
                                    <?php
                                    require_once ('conexion.php');
                                    $consulta = "SELECT codigo, nombre FROM Departamentos";
                                    $consulta = $db->query($consulta);
                                    foreach ($consulta as $row) {
                                        if ($_SESSION["usu_editar"]["departamento"] == $row['codigo']) {
                                            echo '<option value=' . $row['codigo'] . ' selected>' . $row['nombre'] . '</option>';

                                        } else {
                                            echo '<option value=' . $row['codigo'] . '>' . $row['nombre'] . '</option>';
                                        }
                                    }
                                    ?>
                                </select>

                                <script>
                                    let selector = document.getElementById('validacionDepartamento');
                                    for (let i = 0; i < selector.length; i++) {
                                        if (selector[i].value == "<?php echo $dpto_usuario ?>") {
                                            selector[i].selected = true;
                                        }
                                    }
                                </script>
                            </div>
                            <div class="col-md-6">
                                <label for="validacionTutor" class="form-label">Tutor</label>
                                <select name="validacionTutor" id="validacionTutor"
                                    class="selectPersonal form-control form-control-lg">
                                    <option value='No.' selected>No.</option>';
                                    <?php
                                    require_once ('conexion.php');
                                    $consulta = "SELECT grupo FROM Cursos";
                                    $consulta = $db->query($consulta);
                                    foreach ($consulta as $row) {
                                        if ($_SESSION["usu_editar"]["tutor_grupo"] == $row['grupo']) {
                                            echo '<option value=' . $row['grupo'] . ' selected>' . $row['grupo'] . '</option>';

                                        } else {
                                            echo '<option value=' . $row['grupo'] . '>' . $row['grupo'] . '</option>';
                                        }
                                    }
                                    ?>
                                </select>
                            </div>
                        <?php
                        }
                        ?>
                    </div>
                    <?php
                    /* Si el usuario no es  ni administrador no tiene departamento entonces ese campo es de solo lectura */
                    if ($_SESSION["usu_editar"]["rol"] == 2 || $_SESSION["usu_editar"]["rol"] == 3) {
                        print "<script>
                                    document.getElementById('validacionDepartamento').disabled = true;
                                    document.getElementById('validacionTutor').disabled = true;
                                    document.getElementById('validacionTutor').disabled = true;

                                </script>";
                    }
                    ?>
            </div>

            <div class="row">
                <div class="col-md-12 p-3 d-flex justify-content-center">
                    <button class="btn btn-success" name="actualizar" type="submit">Actualizar Perfil</button>
                </div>
            </div>


            </form>
            </div>
        </article>
    </section>
    <?php
    include ("footer.php");
    ?>

</body>

</html>