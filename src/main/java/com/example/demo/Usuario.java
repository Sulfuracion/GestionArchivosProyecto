package com.example.demo;

import java.io.Serializable;
/**
 * Clase que representa un usuario en el sistema.
 */
public class Usuario implements Serializable {

    private int id;
    private String nombreUsuario;
    private String nombre;
    private String apellidos;
    private String email;

    /**
     * Constructor para la creación de un nuevo usuario.
     *
     * @param id            Identificador único del usuario.
     * @param nombreUsuario Nombre de usuario del usuario.
     * @param nombre        Nombre del usuario.
     * @param apellidos     Apellidos del usuario.
     * @param email         Dirección de correo electrónico del usuario.
     */
    public Usuario(int id, String nombreUsuario, String nombre, String apellidos, String email) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Método toString para obtener una representación en cadena del objeto Usuario.
     *
     * @return Cadena que representa al objeto Usuario.
     */
    @Override
    public String toString() {
        return "\nId: " + id +
                "\nNombreUsuario: " + nombreUsuario +
                "\nNombre: " + nombre +
                "\nApellidos: " + apellidos +
                "\nEmail: " + email + "\n";
    }
}