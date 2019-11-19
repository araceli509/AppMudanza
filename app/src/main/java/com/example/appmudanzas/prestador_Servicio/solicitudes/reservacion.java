package com.example.appmudanzas.prestador_Servicio.solicitudes;
import com.example.appmudanzas.prestador_Servicio.solicitudes.*;
import java.sql.Date;

public class reservacion {
    private int id_reservacion;
    private int id_cliente;
    private int id_presentardor;
    private Date fecha;
    private String origen,destino;
    private String origenLatLong,destinoLatLong;
    private int seguro;
    private int numero_pisos;
    private Double monto;
    private int status;

    public reservacion(int id_reservacion, int id_cliente, int id_presentardor, Date fecha, String origen, String destino, String origenLatLong, String destinoLatLong, int seguro, int numero_pisos, Double monto, int status) {
        this.id_reservacion = id_reservacion;
        this.id_cliente = id_cliente;
        this.id_presentardor = id_presentardor;
        this.fecha = fecha;
        this.origen = origen;
        this.destino = destino;
        this.origenLatLong = origenLatLong;
        this.destinoLatLong = destinoLatLong;
        this.seguro = seguro;
        this.numero_pisos = numero_pisos;
        this.monto = monto;
        this.status = status;
    }

    private  cliente cliente;

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

    public String getOrigenLatLong() {
        return origenLatLong;
    }

    public void setOrigenLatLong(String origenLatLong) {
        this.origenLatLong = origenLatLong;
    }

    public String getDestinoLatLong() {
        return destinoLatLong;
    }

    public void setDestinoLatLong(String destinoLatLong) {
        this.destinoLatLong = destinoLatLong;
    }

    public int getSeguro() {
        return seguro;
    }

    public void setSeguro(int seguro) {
        this.seguro = seguro;
    }

    public int getNumero_pisos() {
        return numero_pisos;
    }

    public void setNumero_pisos(int numero_pisos) {
        this.numero_pisos = numero_pisos;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public cliente getCliente() {
        return cliente;
    }

    public void setCliente(cliente cliente) {
        this.cliente = cliente;
    }
}
