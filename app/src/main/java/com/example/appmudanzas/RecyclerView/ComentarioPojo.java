package com.example.appmudanzas.RecyclerView;

public class ComentarioPojo {
    private String nombre;
    private String descripcion;
    private String fecha_comentario;

    public ComentarioPojo() {
    }


    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getFecha_comentario() {
        return fecha_comentario;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFecha_comentario(String fecha_comentario) {
        this.fecha_comentario = fecha_comentario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
