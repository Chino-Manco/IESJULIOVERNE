package com.ies.bargas.model;

import java.sql.Time;
import java.util.Date;

public class Parte {
    private int cod_parte;
    private int cod_usuario;
    private String matriculaAlumno;
    private int incidencia;
    private int puntos;
    private String materia;
    private Date fecha;
    private Time hora;
    private String descripcion;
    private Date fechaComunicacion;
    private String viaComunicacion;
    private String tipoParte;
    private boolean caducado;


    public Parte(int cod_parte, int cod_usuario, String matriculaAlumno, int incidencia, int puntos, String materia, Date fecha, Time hora, String descripcion, Date fechaComunicacion, String viaComunicacion, String tipoParte, boolean caducado) {
        this.cod_parte = cod_parte;
        this.cod_usuario = cod_usuario;
        this.matriculaAlumno = matriculaAlumno;
        this.incidencia = incidencia;
        this.puntos = puntos;
        this.materia = materia;
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
        this.fechaComunicacion = fechaComunicacion;
        this.viaComunicacion = viaComunicacion;
        this.tipoParte = tipoParte;
        this.caducado = caducado;
    }

    public Parte(int cod_usuario, String matriculaAlumno, int incidencia, int puntos, String materia, Date fecha, Time hora, String descripcion, Date fechaComunicacion, String viaComunicacion, String tipoParte, boolean caducado) {
        this.cod_usuario = cod_usuario;
        this.matriculaAlumno = matriculaAlumno;
        this.incidencia = incidencia;
        this.puntos = puntos;
        this.materia = materia;
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
        this.fechaComunicacion = fechaComunicacion;
        this.viaComunicacion = viaComunicacion;
        this.tipoParte = tipoParte;
        this.caducado = caducado;
    }

    public Parte() {
    }

    public int getCod_parte() {
        return cod_parte;
    }

    public void setCod_parte(int cod_parte) {
        this.cod_parte = cod_parte;
    }

    public int getCod_usuario() {
        return cod_usuario;
    }

    public void setCod_usuario(int cod_usuario) {
        this.cod_usuario = cod_usuario;
    }

    public String getMatriculaAlumno() {
        return matriculaAlumno;
    }

    public void setMatriculaAlumno(String matriculaAlumno) {
        this.matriculaAlumno = matriculaAlumno;
    }

    public int getIncidencia() {
        return incidencia;
    }

    public void setIncidencia(int incidencia) {
        this.incidencia = incidencia;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaComunicacion() {
        return fechaComunicacion;
    }

    public void setFechaComunicacion(Date fechaComunicacion) {
        this.fechaComunicacion = fechaComunicacion;
    }

    public String getViaComunicacion() {
        return viaComunicacion;
    }

    public void setViaComunicacion(String viaComunicacion) {
        this.viaComunicacion = viaComunicacion;
    }

    public String getTipoParte() {
        return tipoParte;
    }

    public void setTipoParte(String tipoParte) {
        this.tipoParte = tipoParte;
    }

    public boolean isCaducado() {
        return caducado;
    }

    public void setCaducado(boolean caducado) {
        this.caducado = caducado;
    }

    @Override
    public String toString() {
        return "Parte{" +
                "cod_parte=" + cod_parte +
                ", cod_usuario=" + cod_usuario +
                ", matriculaAlumno='" + matriculaAlumno + '\'' +
                ", incidencia='" + incidencia + '\'' +
                ", puntos=" + puntos +
                ", materia='" + materia + '\'' +
                ", fecha=" + fecha +
                ", hora=" + hora +
                ", descripcion='" + descripcion + '\'' +
                ", fechaComunicacion=" + fechaComunicacion +
                ", viaComunicacion='" + viaComunicacion + '\'' +
                ", tipoParte='" + tipoParte + '\'' +
                ", caducado=" + caducado +
                '}';
    }
}
