package com.ies.bargas.model;

import java.util.List;

public class Asignatura {
    private int codAsignatura;
    private String nombre;
    private int horas;
    private String curso;
    private String tipo;

    public Asignatura(int codAsignatura, String nombre, int horas, String curso, String tipo) {
        this.codAsignatura = codAsignatura;
        this.nombre = nombre;
        this.horas = horas;
        this.curso = curso;
        this.tipo = tipo;
    }

    public Asignatura() {
    }

    public static String[] toStringNombre(List<Asignatura> asignaturas) {
        String [] lista = new String[asignaturas.size()];
        for(int i=0;i<asignaturas.size();i++){
            lista [i] = asignaturas.get(i).getNombre();
        }
        return lista;
    }

    public int getCodAsignatura() {
        return codAsignatura;
    }

    public void setCodAsignatura(int codAsignatura) {
        this.codAsignatura = codAsignatura;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Asignatura{" +
                "codAsignatura=" + codAsignatura +
                ", nombre='" + nombre + '\'' +
                ", horas=" + horas +
                ", curso='" + curso + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
