package com.example.appmudanzas.prestador_Servicio.solicitudes;

import java.io.Serializable;

public class cliente implements Serializable {
    int id_cliente;
    String nombre,apellidos,correo,direccion,telefono,codigopostal;
    String fecha_regristro;
    int status;

    public cliente(){


    }

    public cliente(int id_cliente, String nombre, String apellidos, String correo, String direccion, String telefono, String codigopostal, String fecha_regristro, int status) {
        this.id_cliente = id_cliente;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.direccion = direccion;
        this.telefono = telefono;
        this.codigopostal = codigopostal;
        this.fecha_regristro = fecha_regristro;
        this.status = status;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCodigopostal() {
        return codigopostal;
    }

    public void setCodigopostal(String codigopostal) {
        this.codigopostal = codigopostal;
    }

    public String getFecha_regristro() {
        return fecha_regristro;
    }

    public void setFecha_regristro(String fecha_regristro) {
        this.fecha_regristro = fecha_regristro;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
