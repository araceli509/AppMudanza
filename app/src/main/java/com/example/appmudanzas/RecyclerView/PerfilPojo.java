package com.example.appmudanzas.RecyclerView;

public class PerfilPojo {
    private int id_cliente;
    private String nombre;
    private String apellidos;
    private String correo;
    private String direccion;
    private String telefono;
    private String codigo_postal;
    private String fecha_registro;

    public PerfilPojo() {
    }

    public PerfilPojo(int id_cliente, String nombre, String apellidos, String correo, String direccion, String telefono, String codigo_postal, String fecha_registro) {
        this.id_cliente = id_cliente;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.direccion = direccion;
        this.telefono = telefono;
        this.codigo_postal = codigo_postal;
        this.fecha_registro = fecha_registro;
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

    public String getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(String codigo_postal) {
        this.codigo_postal = codigo_postal;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }
}
