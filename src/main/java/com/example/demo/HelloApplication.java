package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.demo.UsuarioDB.connection;

/**
 * Clase principal que inicia la aplicación JavaFX y gestiona la transición entre las diferentes escenas.
 * Extiende la clase {@link Application} de JavaFX.
 *
 * <p>Esta aplicación muestra una ventana de bienvenida y, si hay un usuario configurado,
 * redirige a la pantalla de menú de archivos; de lo contrario, muestra la vista predeterminada.</p>
 *
 * <p>La aplicación utiliza la clase {@link GestorArchivos} para gestionar operaciones relacionadas con archivos
 * y {@link UsuarioDB} para manejar la información del usuario.</p>
 *
 * <p>El método principal es {@link #start(Stage)}.</p>
 *
 * @author David Guijo Muñoz
 */
public class HelloApplication extends Application {

    private static Scene scene;

    /**
     * Método principal que inicia la aplicación JavaFX.
     *
     * @param stage El objeto {@link Stage} principal de la aplicación.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        connection();
        FXMLLoader fxmlLoader;

        // Configuración de la carpeta de configuración
        GestorArchivos.setCarpetaConfig();

        // Intenta cargar la configuración de usuario existente
        if (GestorArchivos.convertirConfEnUsuario()) {
            fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MenuArchivos.fxml"));
            System.out.println("Hola, " + UsuarioDB.getUsuarioApp().getNombreUsuario());
        } else {
            fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        }

        // Configuración de la escena
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("Bienvenid@!");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Cambia la escena actual a una nueva cargada desde un archivo FXML.
     *
     * @param fxml Nombre del archivo FXML sin la extensión.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    public static void mostrarVentana(String fxml) throws IOException {
        scene.setRoot(cargarFXML(fxml));
    }

    /**
     * Carga un archivo FXML y devuelve su representación como un objeto {@link Parent}.
     *
     * @param fxml Nombre del archivo FXML sin la extensión.
     * @return Objeto {@link Parent} que representa la vista cargada desde el archivo FXML.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    private static Parent cargarFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Método principal que inicia la aplicación.
     *
     * @param args Argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        launch();
    }
}