package com.example.appmudanzas.mData;

import java.sql.Date;

public class reservacion {
    private int id_reservacion;
    private int id_cliente;
    private int id_presentardor;
    private Date fecha;
    private String origen,destino;
    private Double monto;
    private String status;

    public reservacion(int id_reservacion, int id_cliente, int id_presentardor, Date fecha, String origen, String destino, Double monto, String status) {
        this.id_reservacion = id_reservacion;
        this.id_cliente = id_cliente;
        this.id_presentardor = id_presentardor;
        this.fecha = fecha;
        this.origen = origen;
        this.destino = destino;
        this.monto = monto;
        this.status = status;
    }

    public int getId_reservacion() {
        return id_reservacion;
    }

    public void setId_reservacion(int id_reservacion) {
        this.id_reservacion = id_reservacion;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_presentardor() {
        return id_presentardor;
    }

    public void setId_presentardor(int id_presentardor) {
        this.id_presentardor = id_presentardor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
