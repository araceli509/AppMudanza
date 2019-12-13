package com.example.appmudanzas.Cotizacion;

public class Servicio_ExtraPojo {
    private String dias;
    private String hora_inicio;
    private double costoXcargador;
    private double costoUnitarioCajaG;
    private double costoUnitarioCajaM;
    private double costoUnitarioCajaC;
    private double precio;

    public Servicio_ExtraPojo() {
    }

    public Servicio_ExtraPojo(String dias, String hora_inicio, double costoXcargador, double costoUnitarioCajaG, double costoUnitarioCajaM, double costoUnitarioCajaC, double precio) {
        this.dias = dias;
        this.hora_inicio = hora_inicio;
        this.costoXcargador = costoXcargador;
        this.costoUnitarioCajaG = costoUnitarioCajaG;
        this.costoUnitarioCajaM = costoUnitarioCajaM;
        this.costoUnitarioCajaC = costoUnitarioCajaC;
        this.precio = precio;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public double getCostoXcargador() {
        return costoXcargador;
    }

    public void setCostoXcargador(double costoXcargador) {
        this.costoXcargador = costoXcargador;
    }

    public double getCostoUnitarioCajaG() {
        return costoUnitarioCajaG;
    }

    public void setCostoUnitarioCajaG(double costoUnitarioCajaG) {
        this.costoUnitarioCajaG = costoUnitarioCajaG;
    }

    public double getCostoUnitarioCajaM() {
        return costoUnitarioCajaM;
    }

    public void setCostoUnitarioCajaM(double costoUnitarioCajaM) {
        this.costoUnitarioCajaM = costoUnitarioCajaM;
    }

    public double getCostoUnitarioCajaC() {
        return costoUnitarioCajaC;
    }

    public void setCostoUnitarioCajaC(double costoUnitarioCajaC) {
        this.costoUnitarioCajaC = costoUnitarioCajaC;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
