<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Prueba</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <script src="../js/bootstrap.bundle.min.js"></script>
    <script src="https://kit.fontawesome.com/d7bc41fc30.js" crossorigin="anonymous"></script>
    <script src="../js/multiselect-dropdown.js"></script>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            padding: 0;
            background-color: #f8f8f8;
            text-align: justify;

        }

        header {
            text-align: center;
            background-color: #333;
            color: #fff;
            padding: 10px;
        }

        section {
            margin: 20px 50px;
        }

        .negrita {
            font-weight: bold;
        }

        h1 {
            color: white;
        }

        h2 {
            color: #333;
        }

        ol {
            font-size: 14px;
        }

        footer {
            padding: 10px;
            background-color: #333;
            color: #fff;
            bottom: 0;
            width: 100%;
            display: flex;
            justify-content: space-around;
            flex-wrap: wrap;
            align-items: center;
        }

        /* Estilos para el icono de Instagram */
        .instagram-icon {
            width: 30px;
            /* Tamaño del icono */
            height: 30px;
            /* Otros estilos, como color, margen, etc. */
        }
    </style>
</head>

<body>
    <?php
    include ('navegacion.php');
    ?>
    <header>
        <h1>¿En qué consiste la prueba?</h1>
    </header>

    <section>

        <p>La prueba estará compuesta por <span class="negrita">50 preguntas de opción múltiple y tendrá una duración de
                1 hora y 30 minutos.</span></p>
        <p>La jornada será de <span class="negrita">9:00 a 13:30 h</span> y las actividades que se realizarán son:
        </p>
        <ul>
            <li>Prueba con una duración de 1 hora y 30 minutos.</li>
            <li>Conferencias invitadas.</li>
            <li>Entrega de premios para los alumnos que consigan los mejores resultados.</li>
            <li>Entrega de premios para todos los alumnos participantes.</li>
            <li><a target="_blank" href="pdfs/Ejemplo_de_prueba.pdf">En este enlace pueden descargar la prueba que se
                    realizó en las MiniOlimpiadas de Física y Química de 2023 que tuvieron lugar en Albacete.</a></li>
        </ul>

        <p><span class="negrita">Programación de la jornada</span></p>

        <ul>
            <li>9:00 Recepción de visitantes.</li>
            <li>9:30 Prueba de Física y Química.</li>
            <li>11:00 Descanso.</li>
            <li>11:45 Ponencia invitada de la Facultad de Bioquímica y Ciencias Ambientales.</li>
            <li>12:30 Bingo de elementos químicos.</li>
            <li>13:00 Entrega de premios.</li>
            <li>13:30 Finalización de la jornada.</li>
        </ul>
    </section>
    <footer>
        <p>© 2024 MiniOlimpiadas de Física y Química - APFyQ-CLM. Todos los derechos reservados.</p>
        <a href="https://www.instagram.com/miniolimpiadasclm/" target="_blank" rel="noopener noreferrer">
            <img src="img/instagram.png" alt="Instagram" class="instagram-icon">
        </a>
    </footer>
</body>
<html>