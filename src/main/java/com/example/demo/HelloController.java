package com.example.demo;

import com.example.demo.HelloApplication;
import com.example.demo.IniciarSesion;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

import static com.example.demo.UsuarioDB.connection;

/**
 * Controlador asociado a la vista "hello-view.fxml" que maneja la lógica de la interfaz
 * de inicio de sesión y registro de usuarios.
 */
public class HelloController {

    @FXML
    private TextField campoNombreUsuario;

    @FXML
    private TextField campoPassword;

    @FXML
    private Label labelError;

    /**
     * Método invocado cuando se realiza un intento de inicio de sesión.
     * Verifica las credenciales ingresadas y muestra la ventana del menú de archivos si es exitoso.
     *
     * @throws IOException Si hay un error al mostrar la ventana del menú de archivos.
     */
    @FXML
    public void inicioSesion() throws IOException {
        if (IniciarSesion.inicioSesion(campoNombreUsuario.getText(), campoPassword.getText())) {
            HelloApplication.mostrarVentana("MenuArchivos");
        } else {
            labelError.setVisible(true);
        }
    }

    /**
     * Método invocado cuando se hace clic en la opción de registro.
     * Muestra la ventana de registro.
     *
     * @throws IOException Si hay un error al mostrar la ventana de registro.
     */
    public void registro() throws IOException {
        HelloApplication.mostrarVentana("registro");
    }
}