package com.example.appmudanzas.RecyclerView;

public class ChoferPojo {
    private int id_prestador;
    private String nombre;//prestador_servicio
    private double capacidad_carga; //vehiculos
    private double precio;
    private float valoracion;

    public ChoferPojo() {

    }

    public ChoferPojo(int id_prestador, String nombre, double capacidad_carga, double precio, float valoracion) {
        this.id_prestador = id_prestador;
        this.nombre = nombre;
        this.capacidad_carga = capacidad_carga;
        this.precio = precio;
        this.valoracion = valoracion;
    }

    public int getId() {
        return id_prestador;
    }

    public void setId(int id_prestador) {
        this.id_prestador = id_prestador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double isCapacidad_carga() {
        return capacidad_carga;
    }

    public void setCapacidad_carga(double capacidad_carga) {
        this.capacidad_carga = capacidad_carga;
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
