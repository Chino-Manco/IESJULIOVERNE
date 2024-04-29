package com.ies.bargas.model;

import java.util.List;

public class Alumno {
    private String matricula;
    private String nombre;
    private String apellidos;
    private String grupo;

    public Alumno(String matricula, String nombre, String apellidos, String grupo) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.grupo = grupo;
    }

    public Alumno() {
    }
    public static String[] toStringNombre(List<Alumno> alumnos) {
        String [] lista = new String[alumnos.size()];
        for(int i=0;i<alumnos.size();i++){
            lista [i] = alumnos.get(i).getNombre()+ " - " +alumnos.get(i).getApellidos();
        }
        return lista;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "matricula='" + matricula + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", grupo='" + grupo + '\'' +
                '}';
    }
}
