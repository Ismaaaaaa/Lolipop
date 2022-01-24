package com.ismael.lolipop20.entidades;

import java.io.Serializable;
import java.util.Arrays;

public class Anuncio implements Serializable {

    private String titulo, link, estado, descripcion;
    private double precio;
    private int id, idUsuario;
    private boolean aceptaEnvios, externo;
    private byte[] imagen;

    public Anuncio() {
    }

    public Anuncio(int id, int idUsuario, String titulo, String link, String estado, String descripcion, double precio, boolean aceptaEnvios, boolean externo, byte[] imagen) {
        this.titulo = titulo;
        this.link = link;
        this.estado = estado;
        this.descripcion = descripcion;
        this.precio = precio;
        this.id = id;
        this.idUsuario = idUsuario;
        this.aceptaEnvios = aceptaEnvios;
        this.externo = externo;
        this.imagen = imagen;
    }

    public Anuncio(int idUsuario,String titulo, String link, String estado, String descripcion,  double precio, boolean aceptaEnvios, boolean externo, byte[] imagen) {
        this.titulo = titulo;
        this.link = link;
        this.estado = estado;
        this.descripcion = descripcion;
        this.precio = precio;
        this.aceptaEnvios = aceptaEnvios;
        this.externo = externo;
        this.imagen = imagen;
        this.idUsuario = idUsuario;
    }



    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAceptaEnvios() {
        return aceptaEnvios;
    }

    public void setAceptaEnvios(boolean aceptaEnvios) {
        this.aceptaEnvios = aceptaEnvios;
    }

    public boolean isExterno() {
        return externo;
    }

    public void setExterno(boolean externo) {
        this.externo = externo;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return "Anuncio{" +
                "titulo='" + titulo + '\'' +
                ", link='" + link + '\'' +
                ", estado='" + estado + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", id=" + id +
                ", aceptaEnvios=" + aceptaEnvios +
                ", externo=" + externo +
                ", imagen=" + Arrays.toString(imagen) +
                '}';
    }
}
