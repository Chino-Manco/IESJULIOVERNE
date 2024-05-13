<?php
include_once 'app/RepositorioSesion.inc.php';
RepositorioSesion::iniciarSesion();
?>

<nav class="navbar navbar-default navbar-inverse">
    <!-- boton desplegable para responsive -->
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                aria-expanded="false">
                <span class="sr-only">Botón desplegable</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <span class="navbar-brand">
                <span class="glyphicon glyphicon-education micolor" aria-hidden="true"></span>
                IES Julio Verne
            </span>
        </div>

        <!-- parte desplegable (colapsable) del menú-->
        <div id="navbar" class="navbar-collapse collapse">
            <?php
            if (isset($_SESSION["usuario_login"]) && $_SESSION["usuario_login"]["rol"] == 0) {
                ?>
                <ul class="nav navbar-nav">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                            aria-expanded="false">
                            <span class="glyphicon glyphicon-bullhorn" aria-hidden="true"></span>
                            Partes <?php if (!empty($_SESSION["usuario_login"]["tutor_grupo"])) {
                                echo $_SESSION["usuario_login"]["tutor_grupo"];
                            } ?>
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="ver_partes_jefatura_alumno.php">Alumno</a></li>
                            <li><a href="ver_partes_jefatura_grupo.php">Grupo</a></li>
                            <li><a href="ver_partes_jefatura_fecha.php">Fecha</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="parte_grupo.php">Nuevo Parte</a></li>
                            <!--<li role="separator" class="divider"></li>-->
                            <!--<li><a href="enviar_partes_tutores.php">Enviar Partes Tutores</a></li>-->

                        </ul>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                            aria-expanded="false">
                            <span class="glyphicon  glyphicon-thumbs-down" aria-hidden="true"></span>
                            Expulsiones <?php if (!empty($_SESSION["usuario_login"]["tutor_grupo"])) {
                                echo $_SESSION["usuario_login"]["tutor_grupo"];
                            } ?>
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="index_admin.php">Pendientes</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="ver_expulsiones_jefatura_alumno.php">Alumno</a></li>
                            <li><a href="ver_expulsiones_jefatura_grupo.php">Grupo</a></li>
                        </ul>
                    </li>
                  
                    <li class="nav-item">
                        <a class="text-light btn btn-outline-secondary me-2"
                            href="exportarAlumnos.php">
                            Exportar Alumnos
                        </a>
                    </li>
             
                </ul>
                <?php
            } else {
                if (isset($_SESSION["usuario_login"]) && $_SESSION["usuario_login"]["rol"] == 1 && $_SESSION["usuario_login"]["tutor_grupo"] != "No.") {
                    ?>
                    <ul class="nav navbar-nav">
                        <li>
                            <a href="ver_mis_partes.php">
                                <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
                                Mis Partes
                            </a>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                                aria-expanded="false">
                                <span class="glyphicon glyphicon-bullhorn" aria-hidden="true"></span>
                                Partes <?php if (!empty($_SESSION["usuario_login"]["tutor_grupo"])) {
                                    echo $_SESSION["usuario_login"]["tutor_grupo"];
                                } ?>
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a href="ver_partes_grupo_actuales.php"> Actuales</a></li>
                                <li><a href="ver_partes_grupo_todos.php"> Todos</a></li>
                                <li><a href="ver_partes_alumno.php"> Alumno</a></li>
                                <li role="separator" class="divider"></li>
                                <li><a href="parte_tipo.php">Nuevo Parte</a></li>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                                aria-expanded="false">
                                <span class="glyphicon  glyphicon-thumbs-down" aria-hidden="true"></span>
                                Expulsiones <?php if (!empty($_SESSION["usuario_login"]["tutor_grupo"])) {
                                    echo $_SESSION["usuario_login"]["tutor_grupo"];
                                } ?>
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a href="ver_expulsiones_grupo.php">Todas</a></li>
                                <li><a href="ver_expulsiones_alumno.php"> Alumno</a></li>
                            </ul>

                        </li>
                        
                    </ul>

                    <?php
                } else {
                    if (isset($_SESSION["usuario_login"]) && $_SESSION["usuario_login"]["rol"] == 1 && $_SESSION["usuario_login"]["tutor_grupo"] == "No.") {
                        ?>
                        <ul class="nav navbar-nav">
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                                    aria-expanded="false">
                                    <span class="glyphicon glyphicon-bullhorn" aria-hidden="true"></span>
                                    Partes
                                    <span class="caret"></span>
                                </a>
                                <ul class="dropdown-menu">
                                    <li>
                                        <a href="ver_mis_partes.php">
                                            <span aria-hidden="true"></span>
                                            Mis Partes
                                        </a>
                                    </li>
                                    <li role="separator" class="divider"></li>
                                    <li><a href="parte_tipo.php">Nuevo Parte</a></li>
                                </ul>
                            </li>
                            
                        </ul>
                        <?php
                    }
                }
            }
            ?>


            <ul class="nav navbar-nav navbar-right">
                <li class="nav-item">
                    <a class="text-light btn btn-outline-secondary me-2"
                        href="../archivosComunes/actualizarUsuario.php?idusuario=<?php echo $_SESSION['usuario_login']['cod_usuario'] ?>">
                        <span class="d-flex">
                            <svg xmlns="http://www.w3.org/2000/svg" style="height: 12px;" fill="none"
                                viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                                <path stroke-linecap="round" stroke-linejoin="round"
                                    d="M5.121 17.804A13.937 13.937 0 0112 16c2.5 0 4.847.655 6.879 1.804M15 10a3 3 0 11-6 0 3 3 0 016 0zm6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
                            </svg>&nbsp;
                            <?php echo $_SESSION['usuario_login']['nombre'] . ' ' . $_SESSION['usuario_login']['apellidos'] ?>
                        </span>
                    </a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle text-light" id="submenu" role="button" data-bs-toggle="dropdown"
                        href="#" style="margin-right: 100px;"><span class="material-symbols-outlined">
                            Opciones
                        </span></a>
                    <ul class="dropdown-menu" style="background-color: #ECEFF1">
                        <li><a class="dropdown-item" href="../archivosComunes/selector.php">Selector de Apps</a></li>
                        <hr>
                        <li><a class="dropdown-item" href="../archivosComunes/logout.php">Cerrar Sesión</a></li>
                    </ul>
                </li>


        </div>
    </div>
</nav>