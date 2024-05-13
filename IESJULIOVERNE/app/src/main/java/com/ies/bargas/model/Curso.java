package com.ies.bargas.model;

import java.util.ArrayList;
import java.util.List;

public class Curso {
    private String grupo;
    private String aula;
    private String curso;

    public Curso(String grupo, String aula, String curso) {
        this.grupo = grupo;
        this.aula = aula;
        this.curso= curso;
    }

    public Curso(String grupo) {
        this.grupo = grupo;
    }

    public Curso() {
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    @Override
    public String toString() {
        return "Curso{" +
                "grupo='" + grupo + '\'' +
                ", aula='" + aula + '\'' +
                ", curso='" + curso + '\'' +
                '}';
    }
    public static String [] toStringNombre(List<Curso> cursos) {
        String [] lista = new String [cursos.size()];
        for(int i=0;i<cursos.size();i++){
            lista [i] = cursos.get(i).getGrupo();
        }
        return lista;
    }
}
