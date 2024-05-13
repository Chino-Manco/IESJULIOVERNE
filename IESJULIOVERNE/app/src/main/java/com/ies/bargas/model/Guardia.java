package com.ies.bargas.model;

import com.ies.bargas.model.Periodo;
import com.ies.bargas.model.User;

import java.time.LocalDate;

public class Guardia {
    private int cod_guardia;
    private String observaciones;
    private User usuario;
    private LocalDate fecha;
    private Periodo periodo;

    public Guardia(int cod_guardia, String observaciones, User usuario, LocalDate fecha, Periodo periodo) {
        this.cod_guardia = cod_guardia;
        this.observaciones = observaciones;
        this.usuario = usuario;
        this.fecha = fecha;
        this.periodo = periodo;
    }

    public Guardia(String observaciones, User usuario, LocalDate fecha, Periodo periodo) {
        this.observaciones = observaciones;
        this.usuario = usuario;
        this.fecha = fecha;
        this.periodo = periodo;
    }

    public int getCod_guardia() {
        return cod_guardia;
    }

    public void setCod_guardia(int cod_guardia) {
        this.cod_guardia = cod_guardia;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    @Override
    public String toString() {
        return "Guardia{" +
                "cod_guardia=" + cod_guardia +
                ", observaciones='" + observaciones + '\'' +
                ", usuario=" + usuario +
                ", fecha=" + fecha +
                ", periodo=" + periodo +
                '}';
    }
}
