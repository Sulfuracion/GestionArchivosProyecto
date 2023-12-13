package com.example.demo;

/**
 * Clase que representa un archivo en el sistema.
 */
public class Archivo {
    int id;
    String nombreUsuario;
    String nombreArchivo;
    String contenido;
    String fechaCreacion;

    /**
     * Constructor para la creación de un nuevo archivo.
     *
     * @param nombreArchivo Nombre del archivo.
     * @param fechaCreacion Fecha de creación del archivo.
     */
    public Archivo(String nombreArchivo, String fechaCreacion) {
        this.nombreArchivo = nombreArchivo;
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * Constructor para la creación de un archivo existente con identificación de usuario.
     *
     * @param id            Identificador único del archivo.
     * @param nombreUsuario Nombre del usuario asociado al archivo.
     * @param nombreArchivo Nombre del archivo.
     * @param contenido     Contenido del archivo.
     * @param fechaCreacion Fecha de creación del archivo.
     */
    public Archivo(int id, String nombreUsuario, String nombreArchivo, String contenido, String fechaCreacion) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.nombreArchivo = nombreArchivo;
        this.contenido = contenido;
        this.fechaCreacion = fechaCreacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
