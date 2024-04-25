<?php
$password = $_GET['clave'];
$clave = password_hash($password, PASSWORD_DEFAULT);
echo $clave
?>