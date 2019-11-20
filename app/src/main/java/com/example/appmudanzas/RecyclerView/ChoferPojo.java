package com.example.appmudanzas.RecyclerView;

public class ChoferPojo {
    private String nombre;//prestador_servicio
    private boolean capacidad_decarga; //vehiculos
    private double precio_km;
    private int id_ranking;

    public ChoferPojo(String nombre,boolean capacidad_carga, double precio_km, int id_ranking){
        this.nombre=nombre;
        this.capacidad_decarga=capacidad_carga;
        this.precio_km=precio_km;
        this.id_ranking=id_ranking;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isCapacidad_decarga() {
        return capacidad_decarga;
    }

    public double getPrecio_km() {
        return precio_km;
    }

    public int getId_ranking() {
        return id_ranking;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCapacidad_decarga(boolean capacidad_decarga) {
        this.capacidad_decarga = capacidad_decarga;
    }

    public void setPrecio_km(double precio_km) {
        this.precio_km = precio_km;
    }

    public void setId_ranking(int id_ranking) {
        this.id_ranking = id_ranking;
    }
}
