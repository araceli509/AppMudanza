package com.example.appmudanzas.Cotizacion;

public class SolicitudPojo {
    private String fecha_hora;
    private String origen;
    private String destino;
    private String monto;
    private String status;

    public SolicitudPojo(){}
    public SolicitudPojo(String fecha_hora, String origen, String destino, String monto, String status) {
        this.fecha_hora = fecha_hora;
        this.origen = origen;
        this.destino = destino;
        this.monto = monto;
        this.status = status;
    }

    public String getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(String fecha_hora) {
        this.fecha_hora = fecha_hora;
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

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
