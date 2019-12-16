package com.example.appmudanzas.prestador_Servicio.mudanza;

import com.example.appmudanzas.prestador_Servicio.solicitudes.cliente;

public class Mudanza {
    private int id_mudanza,id_cliente,id_prestador;
    private String origen,destino;
    private double distancia;
    private String tiempo;
    private String fecha;
    private String hora;
    private int status;
    private String estado;

    private com.example.appmudanzas.prestador_Servicio.solicitudes.cliente cliente;
    private Prestador_Servicio prestador;

    public Mudanza() {
    }

    public int getId_mudanza() {
        return id_mudanza;
    }

    public void setId_mudanza(int id_mudanza) {
        this.id_mudanza = id_mudanza;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_prestador() {
        return id_prestador;
    }

    public void setId_prestador(int id_prestador) {
        this.id_prestador = id_prestador;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEstado() {
        switch (status){
            case 1: return "En Espera";

            case 2: return "Realizada";

            case 3: return "Activa";
        }
        return "";
    }

    public void setEstado(String estado) {
        this.estado=estado;

    }

    public com.example.appmudanzas.prestador_Servicio.solicitudes.cliente getCliente() {
        return cliente;
    }

    public void setCliente(com.example.appmudanzas.prestador_Servicio.solicitudes.cliente cliente) {
        this.cliente = cliente;
    }

    public Prestador_Servicio getPrestador() {
        return prestador;
    }

    public void setPrestador(Prestador_Servicio prestador) {
        this.prestador = prestador;
    }

    @Override
    public String toString() {
        return "Mudanza{" +
                "id_mudanza=" + id_mudanza +
                ", id_cliente=" + id_cliente +
                ", id_prestador=" + id_prestador +
                ", origen='" + origen + '\'' +
                ", destino='" + destino + '\'' +
                ", distancia=" + distancia +
                ", tiempo='" + tiempo + '\'' +
                ", fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                ", status=" + status +
                ", estado='" + estado + '\'' +
                ", cliente=" + cliente +
                ", prestador=" + prestador +
                '}';
    }
}
