package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador asociado a la vista "leer-modificar-archivo-view.fxml"
 * que gestiona la lectura y modificación de un archivo.
 */
public class LeerModificarArchivoController implements Initializable {

    @FXML
    private Button botonCancelar;

    @FXML
    private Button botonGuardar;

    @FXML
    private Label nombreArchivo;

    @FXML
    private TextArea textAreaArchivo;

    /**
     * Método invocado al cargar la vista para mostrar el contenido del archivo seleccionado.
     */
    @FXML
    public void cargarArchivo() {
        nombreArchivo.setText(GestorArchivos.getNombreArchivoSeleccionado());
        textAreaArchivo.setText(GestorArchivos.descodificarArchivo(UsuarioDB.recuperarArchivo(GestorArchivos.getNombreArchivoSeleccionado())));
    }

    /**
     * Método invocado al hacer clic en el botón "Guardar".
     * Guarda los cambios realizados en el archivo y vuelve al menú de archivos.
     */
    public void guardarArchivo() {
        UsuarioDB.actualizarArchivo(GestorArchivos.codificarContenido(textAreaArchivo.getText()));
        try {
            HelloApplication.mostrarVentana("MenuArchivos");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método invocado al hacer clic en el botón "Cancelar".
     * Cancela la operación y vuelve al menú de archivos.
     */
    public void cancelar() {
        try {
            GestorArchivos.setNombreArchivoSeleccionado(null);
            HelloApplication.mostrarVentana("MenuArchivos");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método de inicialización que se llama después de cargar la vista.
     * Carga el contenido del archivo al inicio.
     *
     * @param url            URL no utilizada.
     * @param resourceBundle ResourceBundle no utilizado.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarArchivo();
    }
}