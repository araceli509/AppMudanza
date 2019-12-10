package com.example.appmudanzas.RecyclerView;

public class PrestadorServicioIDPojo {
    private String nombre;
    private String apellidos;
    private String direccion;
    private String telefono;
    private String correo;
    private String codigo_postal;
    private String foto_perfil;
    private float valoracion;
    private double precio;
    private String hora_inicio;
    private String hora_salida;

    public PrestadorServicioIDPojo() {
    }

    public PrestadorServicioIDPojo(String nombre, String apellidos, String direccion, String telefono, String correo,
                                   String codigo_postal, String foto_perfil, float valoracion,  double precio,String hora_inicio,String hora_salida) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.codigo_postal = codigo_postal;
        this.foto_perfil = foto_perfil;
        this.valoracion = valoracion;
        this.precio = precio;
        this.hora_inicio = hora_inicio;
        this.hora_salida = hora_salida;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(String codigo_postal) {
        this.codigo_postal = codigo_postal;
    }

    public String getFoto_perfil() {
        return foto_perfil;
    }

    public void setFoto_perfil(String foto_perfil) {
        this.foto_perfil = foto_perfil;
    }

    public float getValoracion() {
        return valoracion;
    }

    public void setValoracion(float valoracion) {
        this.valoracion = valoracion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHora_salida() {
        return hora_salida;
    }

    public void setHora_salida(String hora_salida) {
        this.hora_salida = hora_salida;
    }

    public String horario(){
        return hora_inicio+"-"+hora_salida;
    }
}
