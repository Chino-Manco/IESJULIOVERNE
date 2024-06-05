package com.ies.bargas.model;

import java.util.List;

public class Incidencia {

    private int codigo;
    private String nombre;
    private int puntos;
    private String descripcion;

    public Incidencia(int codigo, String nombre, int puntos, String descripcion) {
        this.codigo= codigo;
        this.nombre = nombre;
        this.puntos = puntos;
        this.descripcion = descripcion;
    }

    public Incidencia() {

    }

    public static String[] toStringNombre(List<Incidencia> incidencias) {
        String [] lista = new String[incidencias.size()];
        for(int i=0;i<incidencias.size();i++){
            lista [i] = incidencias.get(i).getPuntos()+ " - " +incidencias.get(i).getNombre();
        }
        return lista;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Incidencia{" +
                "codigo=" + codigo +
                ", nombre='" + nombre + '\'' +
                ", puntos=" + puntos +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
