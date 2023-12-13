package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Controlador asociado a la vista "registro-view.fxml" que gestiona el registro de nuevos usuarios.
 */
public class RegistroController {

    @FXML
    private TextField apellidoText;

    @FXML
    private Button crearButton;

    @FXML
    private Label labelError;

    @FXML
    private TextField emailText;

    @FXML
    private TextField nombreText;

    @FXML
    private TextField campoContraseña;

    @FXML
    private TextField usuarioText;

    /**
     * Método invocado al hacer clic en el botón "Crear".
     * Registra un nuevo usuario con la información proporcionada.
     */
    @FXML
    public void registrarUsuario() {
        // Verifica si algún campo obligatorio está vacío
        if (usuarioText.getText().isEmpty() || nombreText.getText().isEmpty() || campoContraseña.getText().isEmpty()
                || emailText.getText().isEmpty() || apellidoText.getText().isEmpty()) {
            labelError.setVisible(true);
        } else {
            // Intenta registrar el usuario en la base de datos
            if (UsuarioDB.ingresarUsuario(usuarioText.getText(), campoContraseña.getText(), nombreText.getText(), apellidoText.getText(), emailText.getText())) {
                try {
                    // Si el registro es exitoso, muestra la ventana de archivos
                    HelloApplication.mostrarVentana("MenuArchivos");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Método invocado al hacer clic en el botón "Inicio Sesión".
     * Redirige a la vista de inicio de sesión.
     *
     * @throws IOException Si hay un error al mostrar la vista de inicio de sesión.
     */
    @FXML
    public void inicioSesion() throws IOException {
        HelloApplication.mostrarVentana("hello-view");
    }
}
