package com.example.demo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Scanner;

/**
 * Clase que gestiona operaciones relacionadas con archivos.
 */
public class GestorArchivos {

    private static File carpetaConfig;

    private static String nombreArchivoSeleccionado;

    /**
     * Borra el archivo de configuración de usuario.
     *
     * @return true si la operación de borrado es exitosa, false de lo contrario.
     */
    public static boolean borraArchivoUsuario() {
        File f = new File(carpetaConfig, Constantes.NOMBRE_ARCHIVO_USUARIO);

        if (f.delete()) {
            System.out.println("El fichero " + Constantes.NOMBRE_ARCHIVO_USUARIO + " ha sido borrado correctamente");
            return true;
        } else {
            System.out.println("El fichero " + Constantes.NOMBRE_ARCHIVO_USUARIO + " no se ha podido borrar");
            return false;
        }
    }

    /**
     * Borra un archivo dado su nombre.
     *
     * @param nombreArchivo Nombre del archivo a borrar.
     * @return true si la operación de borrado es exitosa, false de lo contrario.
     */
    public static boolean borrarArchivo(String nombreArchivo) {
        File f = new File(Constantes.RUTA_CARPETA_ARCHIVOS, nombreArchivo);

        if (f.delete()) {
            System.out.println("El fichero " + nombreArchivo + " ha sido borrado correctamente");
        } else {
            System.out.println("El fichero " + nombreArchivo + " no se ha podido borrar");
        }

        return UsuarioDB.borrarArchivo(nombreArchivo);
    }

    /**
     * Crea un nuevo archivo con el nombre proporcionado y lo guarda en la carpeta de archivos.
     *
     * @param nombreArchivo Nombre del nuevo archivo.
     * @return Objeto Archivo creado.
     */
    public static Archivo crearArchivo(String nombreArchivo) {
        Scanner sc = new Scanner(System.in);
        String rutaCarpeta = Constantes.RUTA_CARPETA_ARCHIVOS;
        File carpeta = new File(rutaCarpeta);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
        File archivo = new File(carpeta, nombreArchivo);

        try (FileWriter fileWriter = new FileWriter(archivo)) {
            fileWriter.write(" ");
            if (UsuarioDB.guardarEnBDD(codificarArchivo(carpeta, nombreArchivo), nombreArchivo)) {
                System.out.println("Archivo creado exitosamente.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

        return new Archivo(archivo.getName(), formatoFecha.format(new Date(System.currentTimeMillis())));
    }

    /**
     * Crea la carpeta de configuración si no existe.
     */
    public static void setCarpetaConfig() {
        carpetaConfig = new File(Constantes.RUTA_CARPETA_CONFIG);
        if (!carpetaConfig.exists()) {
            carpetaConfig.mkdirs();
        }
    }

    /**
     * Obtiene el nombre del archivo seleccionado.
     *
     * @return Nombre del archivo seleccionado.
     */
    public static String getNombreArchivoSeleccionado() {
        return nombreArchivoSeleccionado;
    }

    /**
     * Establece el nombre del archivo seleccionado.
     *
     * @param nombreArchivoSeleccionado Nombre del archivo seleccionado.
     */
    public static void setNombreArchivoSeleccionado(String nombreArchivoSeleccionado) {
        GestorArchivos.nombreArchivoSeleccionado = nombreArchivoSeleccionado;
    }

    /**
     * Guarda los datos del usuario en un archivo de configuración.
     *
     * @return true si la operación es exitosa, false de lo contrario.
     */
    public static boolean guardarUsuario() {
        File archivoUsuario = new File(carpetaConfig, Constantes.NOMBRE_ARCHIVO_USUARIO);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivoUsuario.getPath()))) {
            oos.writeObject(UsuarioDB.getUsuarioApp());
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Recupera un objeto usuario del archivo de configuración.
     *
     * @return true si la operación es exitosa, false de lo contrario.
     */
    public static boolean convertirConfEnUsuario() {
        File archivoUsuario = new File(carpetaConfig, Constantes.NOMBRE_ARCHIVO_USUARIO);
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivoUsuario.getPath()))) {
            UsuarioDB.setUsuarioApp((Usuario) ois.readObject());
            return true;
        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * Codifica el contenido de un archivo en Base64.
     *
     * @param carpeta       Carpeta que contiene el archivo.
     * @param nombreArchivo Nombre del archivo a codificar.
     * @return Contenido del archivo codificado en Base64.
     * @throws IOException Si ocurre un error de lectura del archivo.
     */
    public static String codificarArchivo(File carpeta, String nombreArchivo) throws IOException {
        File f = new File(carpeta, nombreArchivo);
        return Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(f.getAbsolutePath())));
    }

    /**
     * Codifica el contenido de una cadena en Base64.
     *
     * @param contenidoArchivo Contenido a codificar.
     * @return Contenido codificado en Base64.
     */
    public static String codificarContenido(String contenidoArchivo) {
        return Base64.getEncoder().encodeToString(contenidoArchivo.getBytes());
    }

    /**
     * Descodifica el contenido de un archivo desde Base64.
     *
     * @param archivoCodificado Contenido del archivo codificado en Base64.
     * @return Contenido del archivo descodificado.
     */
    public static String descodificarArchivo(String archivoCodificado) {
        return new String(Base64.getDecoder().decode(archivoCodificado.getBytes()));
    }
}
