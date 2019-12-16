package com.example.appmudanzas.RecyclerView;

public class ChoferPojo {
    private int id_prestador;
    private String nombre;
    private double largo;
    private double ancho;
    private double alto;
    private double precio;
    private float valoracion;
    private String imagenfrontal;

    public ChoferPojo() {

    }

    public ChoferPojo(int id_prestador, String nombre, double largo, double ancho, double alto, double precio, float valoracion,String imagenfrontal) {
        this.id_prestador = id_prestador;
        this.nombre = nombre;
        this.largo = largo;
        this.ancho = ancho;
        this.alto = alto;
        this.precio = precio;
        this.valoracion = valoracion;
        this.imagenfrontal=imagenfrontal;
    }

    public String getImagenfrontal() {
        return imagenfrontal;
    }

    public void setImagenfrontal(String imagenfrontal) {
        this.imagenfrontal = imagenfrontal;
    }

    public double volumen(){
        return largo*ancho*alto;
    }

    public int getId_prestador() {
        return id_prestador;
    }

    public void setId_prestador(int id_prestador) {
        this.id_prestador = id_prestador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getLargo() {
        return largo;
    }

    public void setLargo(double largo) {
        this.largo = largo;
    }

    public double getAncho() {
        return ancho;
    }

    public void setAncho(double ancho) {
        this.ancho = ancho;
    }

    public double getAlto() {
        return alto;
    }

    public void setAlto(double alto) {
        this.alto = alto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public float getValoracion() {
        return valoracion;
    }

    public void setValoracion(float valoracion) {
        this.valoracion = valoracion;
    }
}
