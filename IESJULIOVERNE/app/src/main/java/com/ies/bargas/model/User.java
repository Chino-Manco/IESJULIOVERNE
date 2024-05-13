package com.ies.bargas.model;

import java.util.List;

public class User {
    private int cod_usuario;
    private String email;
    private String clave;
    private String nombre;
    private String apellidos;
    private String dni;
    private int cod_delphos;
    private String validar;
    private Departamento departamento;
    private String tutor_grupo;
    private String rol;

    public User(int cod_usuario, String email, String clave, String nombre, String apellidos, String dni, int cod_delphos, String validar, Departamento departamento, String tutor_grupo, String rol) {
        this.cod_usuario = cod_usuario;
        this.email = email;
        this.clave = clave;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.cod_delphos = cod_delphos;
        this.validar = validar;
        this.departamento = departamento;
        this.tutor_grupo = tutor_grupo;
        this.rol = rol;
    }

    public User(String email, String clave, String nombre, String apellidos, String dni, int cod_delphos, String validar, Departamento departamento, String tutor_grupo, String rol) {
        this.email = email;
        this.clave = clave;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.cod_delphos = cod_delphos;
        this.validar = validar;
        this.departamento = departamento;
        this.tutor_grupo = tutor_grupo;
        this.rol = rol;
    }
    public User(){

    }

    public int getCod_usuario() {
        return cod_usuario;
    }

    public void setCod_usuario(int cod_usuario) {
        this.cod_usuario = cod_usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getCod_delphos() {
        return cod_delphos;
    }

    public void setCod_delphos(int cod_delphos) {
        this.cod_delphos = cod_delphos;
    }

    public String getValidar() {
        return validar;
    }

    public void setValidar(String validar) {
        this.validar = validar;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public String getTutor_grupo() {
        return tutor_grupo;
    }

    public void setTutor_grupo(String tutor_grupo) {
        this.tutor_grupo = tutor_grupo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "User{" +
                "cod_usuario=" + cod_usuario +
                ", email='" + email + '\'' +
                ", clave='" + clave + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", dni='" + dni + '\'' +
                ", cod_delphos=" + cod_delphos +
                ", validar='" + validar + '\'' +
                ", departamento=" + departamento +
                ", tutor_grupo='" + tutor_grupo + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }

    public static String[] toStringNombre(List<User> usuarios) {
        String [] lista = new String[usuarios.size()];
        for(int i=0;i<usuarios.size();i++){
            lista [i] = usuarios.get(i).getNombre()+ " - " +usuarios.get(i).getApellidos();
        }
        return lista;
    }
}
