package com.ies.bargas.model;

import java.util.List;

public class Periodo {
    private int cod_periodo;
    private String inicio;
    private String fin;

    public Periodo(int cod_periodo, String inicio, String fin) {
        this.cod_periodo = cod_periodo;
        this.inicio = inicio;
        this.fin = fin;
    }

    public int getCod_periodo() {
        return cod_periodo;
    }

    public void setCod_periodo(int cod_periodo) {
        this.cod_periodo = cod_periodo;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    @Override
    public String toString() {
        return "Periodo{" +
                "cod_periodo=" + cod_periodo +
                ", inicio=" + inicio +
                ", fin=" + fin +
                '}';
    }

    public static String[] toStringNombre(List<Periodo> periodos) {
        String [] lista = new String[periodos.size()];
        for(int i=0;i<periodos.size();i++){
            lista [i] = periodos.get(i).getCod_periodo()+ " - " +periodos.get(i).getInicio()+ " - " +periodos.get(i).getFin();
        }
        return lista;
    }
}
