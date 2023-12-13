package com.example.demo;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controlador asociado a la vista "menu-archivos-view.fxml" que gestiona la interfaz
 * de usuario para manejar archivos.
 */
public class MenuArchivosController implements Initializable {

    @FXML
    private Button botonAgregar;

    @FXML
    private TableView<Archivo> tablaArchivos;

    @FXML
    private TableColumn<Archivo, String> nombreArchivo;

    @FXML
    private TableColumn<Archivo, String> fechaCreacion;

    @FXML
    private Button botonEliminar;

    @FXML
    private Button botonCerrarSesion;

    @FXML
    private Button botonAbrir;

    // Lista observable para almacenar los archivos
    ObservableList<Archivo> lista = FXCollections.<Archivo>observableArrayList();

    /**
     * Carga la tabla de archivos con la información obtenida de la base de datos.
     */
    public void cargarTabla() {
        lista = FXCollections.<Archivo>observableArrayList(Objects.requireNonNull(UsuarioDB.getArchivos()));
    }

    /**
     * Método de inicialización que se llama después de cargar la vista.
     * Configura las columnas de la tabla y carga la lista de archivos.
     *
     * @param url            URL no utilizada.
     * @param resourceBundle ResourceBundle no utilizado.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarTabla();
        nombreArchivo.setCellValueFactory(new PropertyValueFactory<Archivo, String>("nombreArchivo"));
        fechaCreacion.setCellValueFactory(new PropertyValueFactory<Archivo, String>("fechaCreacion"));

        tablaArchivos.setItems(lista);
    }

    /**
     * Método invocado al hacer clic en el botón "Agregar".
     * Crea un nuevo archivo a partir de un nombre proporcionado por el usuario.
     */
    @FXML
    public void crearArchivo() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Nuevo Archivo");
        dialog.setHeaderText(null);
        dialog.setContentText("Indica el nombre del archivo:");

        dialog.showAndWait().ifPresent(nombreArchivo -> {
            Archivo archivoNuevo = GestorArchivos.crearArchivo(nombreArchivo + ".txt");
            tablaArchivos.getItems().add(archivoNuevo);
        });
    }

    /**
     * Método invocado al hacer clic en el botón "Eliminar".
     * Borra el archivo seleccionado de la tabla y de la base de datos.
     */
    @FXML
    public void borrarArchivo() {
        Archivo archivo = tablaArchivos.getSelectionModel().getSelectedItem();
        if (archivo != null) {
            GestorArchivos.borrarArchivo(archivo.getNombreArchivo());
            tablaArchivos.getItems().remove(archivo);
        }
    }

    /**
     * Método invocado al hacer clic en el botón "Cerrar Sesión".
     * Borra la información del usuario y vuelve a la vista de inicio de sesión.
     *
     * @throws IOException Si hay un error al mostrar la vista de inicio de sesión.
     */
    @FXML
    public void cerrarSesion() throws IOException {
        GestorArchivos.borraArchivoUsuario();
        HelloApplication.mostrarVentana("hello-view");
    }

    /**
     * Método invocado al hacer clic en el botón "Abrir".
     * Abre la vista de lectura y modificación de archivos para el archivo seleccionado.
     */
    @FXML
    public void abrirArchivo() {
        if (tablaArchivos.getSelectionModel().getSelectedItem() != null) {
            GestorArchivos.setNombreArchivoSeleccionado(tablaArchivos.getSelectionModel().getSelectedItem().getNombreArchivo());
            try {
                HelloApplication.mostrarVentana("LeerModificarArchivo");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
