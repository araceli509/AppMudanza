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
    private String foto_frontal;
    private String foto_lateral;
    private String foto_trasera;
    private int largo;
    private String ancho;
    private String alto;
    private String placas;
    private int id_cliente;
    private String descripcion;
    private String fecha_comentario;

    public PrestadorServicioIDPojo() {
    }

    public PrestadorServicioIDPojo(String nombre, String apellidos, String direccion, String telefono, String correo, String codigo_postal, String foto_perfil, float valoracion, double precio, String hora_inicio, String hora_salida, String foto_frontal, String foto_lateral, String foto_trasera, int largo, String ancho, String alto, String placas, int id_cliente, String descripcion, String fecha_comentario) {
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
        this.foto_frontal = foto_frontal;
        this.foto_lateral = foto_lateral;
        this.foto_trasera = foto_trasera;
        this.largo = largo;
        this.ancho = ancho;
        this.alto = alto;
        this.placas = placas;
        this.id_cliente=id_cliente;
        this.descripcion = descripcion;
        this.fecha_comentario = fecha_comentario;
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

    public String getFoto_frontal() {
        return foto_frontal;
    }

    public void setFoto_frontal(String foto_frontal) {
        this.foto_frontal = foto_frontal;
    }

    public String getFoto_lateral() {
        return foto_lateral;
    }

    public void setFoto_lateral(String foto_lateral) {
        this.foto_lateral = foto_lateral;
    }

    public String getFoto_trasera() {
        return foto_trasera;
    }

    public void setFoto_trasera(String foto_trasera) {
        this.foto_trasera = foto_trasera;
    }

    public int getLargo() {
        return largo;
    }

    public void setLargo(int largo) {
        this.largo = largo;
    }

    public String getAncho() {
        return ancho;
    }

    public void setAncho(String ancho) {
        this.ancho = ancho;
    }

    public String getAlto() {
        return alto;
    }

    public void setAlto(String alto) {
        this.alto = alto;
    }

    public String getPlacas() {
        return placas;
    }

    public void setPlacas(String placas) {
        this.placas = placas;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha_comentario() {
        return fecha_comentario;
    }

    public void setFecha_comentario(String fecha_comentario) {
        this.fecha_comentario = fecha_comentario;
    }

    public String horario(){
        return getHora_inicio()+"-"+getHora_salida();
    }
}
