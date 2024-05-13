<?php
//include database configuration file
$db = require_once ('../archivosComunes/conexion.php');
;

//get records from database
$query = $db->query("SELECT a.grupo, a.matricula, a.nombre, a.apellidos, sum(p.puntos) AS puntos
 FROM Alumnos a, Partes p 
 WHERE a.matricula = p.matricula_alumno
 GROUP BY a.grupo, a.matricula, a.nombre, a.apellidos 
 ORDER BY a.grupo, a.matricula DESC");

if ($query->rowCount() > 0) {
    $delimiter = ",";
    $filename = "partes_" . date('Y-m-d') . ".csv";

    //create a file pointer
    $f = fopen('php://memory', 'w');

    //set column headers
    $fields = array('Grupo', 'Matricula', 'Nombre', 'Apellidos', 'Puntos');
    fputcsv($f, $fields, $delimiter);

    //output each row of the data, format line as csv and write to file pointer
    while ($row = $query->fetch(PDO::FETCH_ASSOC)) {
        $lineData = array($row['grupo'], $row['matricula'], $row['nombre'], $row['apellidos'], $row['puntos']);
        fputcsv($f, $lineData, $delimiter);
    }

    //move back to beginning of file
    fseek($f, 0);

    //set headers to download file rather than displayed
    header('Content-Encoding: UTF-8');
    header('Content-Type: application/csv; charset=UTF-8');
    header('Content-Disposition: attachment; filename="' . $filename . '";');

    //output all remaining data on a file pointer
    fpassthru($f);
}
exit;

?>