package com.ies.bargas.model;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Expulsion {
    private int cod_expulsion;
    private int cod_usuario;
    private Alumno matricula_del_Alumno;
    private LocalDate fecha_Inicio;
    private LocalDate Fecha_Fin;
    private String tipo_expulsion;
    private Timestamp fecha_Insercion;

    public Expulsion(int cod_expulsion, int cod_usuario, Alumno matricula_del_Alumno, LocalDate fecha_Inicio, LocalDate fecha_Fin, String tipo_expulsion, Timestamp fecha_Insercion) {
        this.cod_expulsion = cod_expulsion;
        this.cod_usuario = cod_usuario;
        this.matricula_del_Alumno = matricula_del_Alumno;
        this.fecha_Inicio = fecha_Inicio;
        this.Fecha_Fin = fecha_Fin;
        this.tipo_expulsion= tipo_expulsion;
        this.fecha_Insercion = fecha_Insercion;
    }

    public Expulsion(int cod_expulsion, int cod_usuario, Alumno matricula_del_Alumno, String tipo_expulsion, Timestamp fecha_Insercion) {
        this.cod_expulsion = cod_expulsion;
        this.cod_usuario = cod_usuario;
        this.matricula_del_Alumno = matricula_del_Alumno;
        this.tipo_expulsion= tipo_expulsion;
        this.fecha_Insercion = fecha_Insercion;
    }

    public Expulsion(int cod_usuario, Alumno matricula_del_Alumno, LocalDate fecha_Inicio, LocalDate fecha_Fin, Timestamp fecha_Insercion) {
        this.cod_usuario = cod_usuario;
        this.matricula_del_Alumno = matricula_del_Alumno;
        this.fecha_Inicio = fecha_Inicio;
        Fecha_Fin = fecha_Fin;
        this.fecha_Insercion = fecha_Insercion;
    }

    public Expulsion(int cod_usuario, Alumno matricula_del_Alumno,String tipo_expulsion, Timestamp fecha_Insercion) {
        this.cod_usuario = cod_usuario;
        this.matricula_del_Alumno = matricula_del_Alumno;
        this.tipo_expulsion=tipo_expulsion;
        this.fecha_Insercion = fecha_Insercion;
    }

    public Expulsion() {
    }

    public int getCod_expulsion() {
        return cod_expulsion;
    }

    public void setCod_expulsion(int cod_expulsion) {
        this.cod_expulsion = cod_expulsion;
    }

    public int getCod_usuario() {
        return cod_usuario;
    }

    public void setCod_usuario(int cod_usuario) {
        this.cod_usuario = cod_usuario;
    }

    public Alumno getMatricula_del_Alumno() {
        return matricula_del_Alumno;
    }

    public void setMatricula_del_Alumno(Alumno matricula_del_Alumno) {
        this.matricula_del_Alumno = matricula_del_Alumno;
    }

    public LocalDate getFecha_Inicio() {
        return fecha_Inicio;
    }

    public void setFecha_Inicio(LocalDate fecha_Inicio) {
        this.fecha_Inicio = fecha_Inicio;
    }

    public LocalDate getFecha_Fin() {
        return Fecha_Fin;
    }

    public void setFecha_Fin(LocalDate fecha_Fin) {
        Fecha_Fin = fecha_Fin;
    }

    public Timestamp getFecha_Insercion() {
        return fecha_Insercion;
    }

    public void setFecha_Insercion(Timestamp fecha_Insercion) {
        this.fecha_Insercion = fecha_Insercion;
    }

    public String getTipo_expulsion() {
        return tipo_expulsion;
    }

    public void setTipo_expulsion(String tipo_expulsion) {
        this.tipo_expulsion = tipo_expulsion;
    }

    @Override
    public String toString() {
        return "Expulsion{" +
                "cod_expulsion=" + cod_expulsion +
                ", cod_usuario=" + cod_usuario +
                ", matricula_del_Alumno='" + matricula_del_Alumno + '\'' +
                ", fecha_Inicio=" + fecha_Inicio +
                ", Fecha_Fin=" + Fecha_Fin +
                ", tipo_expulsion=" + tipo_expulsion +
                ", fecha_Insercion=" + fecha_Insercion +
                '}';
    }
}
