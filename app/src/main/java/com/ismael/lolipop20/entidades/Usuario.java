package com.ismael.lolipop20.entidades;

import java.io.Serializable;

public class Usuario implements Serializable {
    private int id;
    private String nombre, password;
    private double puntuacion;

    public Usuario(){}

    public Usuario(int id, String nombre, String password, double puntuacion) {
        this.id = id;
        this.nombre = nombre;
        this.password = password;
        this.puntuacion = puntuacion;
    }

    public Usuario(String nombre, String password, double puntuacion){
        this.nombre = nombre;
        this.password = password;
        this.puntuacion = puntuacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(double puntuacion) {
        this.puntuacion = puntuacion;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", puntuacion=" + puntuacion +
                '}';
    }
}
