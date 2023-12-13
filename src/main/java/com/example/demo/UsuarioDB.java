package com.example.demo;


import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
/**
 * Clase que maneja las operaciones relacionadas con la base de datos para la entidad Usuario y Archivo.
 */
public class UsuarioDB {
    public static Connection connection;

    private static Usuario usuarioApp;


    /**
     * Establece la conexión con la base de datos.
     *
     * @return True si la conexión fue exitosa, false en caso contrario.
     */
    public static boolean connection() {
        try {
            connection = DriverManager.getConnection(Constantes.RUTA_BBDD, Constantes.USUARIO_BBDD, Constantes.CONTRASEÑA_BBDD);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * Comprueba si un usuario existe en la base de datos.
     *
     * @param usuario   Nombre de usuario.
     * @param contrasena Contraseña del usuario.
     * @return True si el usuario existe, false en caso contrario.
     */
    public static boolean comprobarUsuario(String usuario, String contrasena) {
        String sentenciaSQL = "SELECT * FROM usuarios WHERE nombreUsuario=? AND contrasena=?";

        if (connection != null) {
            try {
                PreparedStatement sentencia = connection.prepareStatement(sentenciaSQL);
                sentencia.setString(1, usuario);
                sentencia.setString(2, contrasena);

                ResultSet resultado = sentencia.executeQuery();
                if (resultado.next()) {
                    usuarioApp = new Usuario(resultado.getInt("id"), resultado.getString("nombreUsuario"),
                            resultado.getString("nombre"), resultado.getString("apellidos"),
                            resultado.getString("email"));
                    GestorArchivos.guardarUsuario();
                    return true;
                } else {
                    System.out.println(resultado.toString());
                }
            } catch (SQLException e) {
                return false;
            }
        }
        return false;
    }


    /**
     * Registra un nuevo usuario en la base de datos.
     *
     * @param nombreUsuario Nombre de usuario.
     * @param contrasena    Contraseña del usuario.
     * @param nombre        Nombre del usuario.
     * @param apellidos     Apellidos del usuario.
     * @param email         Dirección de correo electrónico del usuario.
     * @return True si el registro fue exitoso, false en caso contrario.
     */
    public static boolean ingresarUsuario(String nombreUsuario, String contrasena, String nombre, String apellidos, String email) {
        String sql = "insert into usuarios(nombreUsuario, contrasena, nombre, apellidos, email) values (?,?,?,?,?)";
        try {
            PreparedStatement sentencia = connection.prepareStatement(sql);
            sentencia.setString(1, nombreUsuario);
            sentencia.setString(2, contrasena);
            sentencia.setString(3, nombre);
            sentencia.setString(4, apellidos);
            sentencia.setString(5, email);


            sentencia.execute();
            usuarioApp= new Usuario(0, nombreUsuario,nombre, apellidos,email);
            GestorArchivos.guardarUsuario();
        } catch (SQLException e) {
            return false;
        }

        return true;
    }



    /**
     * Obtiene los datos de un usuario especificado por nombre de usuario y contraseña.
     *
     * @param nombreUsuario Nombre de usuario.
     * @param contrasena    Contraseña del usuario.
     * @return Cadena que contiene los datos del usuario.
     */
    public static String getDatos(String nombreUsuario, String contrasena) {
        String sentenciaSQL = "SELECT * FROM  usuarios WHERE nombreUsuario=? AND contrasena=?";

        String datos = "";
        if (connection != null) {
            try {
                PreparedStatement sentencia = connection.prepareStatement(sentenciaSQL);
                sentencia.setString(1, nombreUsuario);
                sentencia.setString(2, contrasena);

                ResultSet resultado = sentencia.executeQuery();
                if (resultado.next()) {
                    datos += resultado.getString("nombreUsuario") + "\n";
                    datos += resultado.getString("id") + "\n";
                    datos += resultado.getString("nombre") + "\n";
                    datos += resultado.getString("apellidos") + "\n";
                    datos += resultado.getString("email") + "\n";
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return datos;
    }
    /**
     * Obtiene la lista de archivos asociados al usuario actual.
     *
     * @return Lista de archivos del usuario actual o null si hay un error de conexión o SQL.
     */
    public static List<Archivo> getArchivos() {
        String sentenciaSQL = "SELECT * FROM  archivos WHERE nombreUsuario=?";
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        List<Archivo> datos = new ArrayList<>();
        if (connection != null) {
            try {
                PreparedStatement sentencia = connection.prepareStatement(sentenciaSQL);
                sentencia.setString(1, usuarioApp.getNombreUsuario());

                ResultSet resultado = sentencia.executeQuery();
                while (resultado.next()) {
                    Archivo archivo = new Archivo(resultado.getString("nombreArchivo"),
                            formatoFecha.format(resultado.getDate("fechaCreacion")));
                    datos.add(archivo);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return datos;
    }
    /**
     * Elimina un usuario de la base de datos.
     *
     * @param id Identificador del usuario a eliminar.
     * @return True si la eliminación fue exitosa, false en caso contrario.
     */
    public static boolean eliminarUsuario(String id){
        String sentenciaSQL = "delete from usuarios where id=?";
        if (connection != null) {
            try {
                PreparedStatement sentencia = connection.prepareStatement(sentenciaSQL);
                sentencia.setInt(1, Integer.parseInt(id));
                int resultado = sentencia.executeUpdate();
                if (resultado>0) {
                    return true;
                }
            } catch (SQLException e) {
                return false;
            }
        }
        return false;
    }

    /**
     * Obtiene los datos del usuario actual.
     *
     * @return Cadena que contiene los datos del usuario actual.
     */
    public static String getDatos() {
        return usuarioApp.toString();
    }

    /**
     * Guarda un archivo en la base de datos.
     *
     * @param archivoBLOB     Contenido del archivo codificado en formato de objeto binario grande.
     * @param nombreArchivo   Nombre del archivo.
     * @return True si la operación fue exitosa, false en caso contrario.
     * @throws SQLException Si ocurre un error de SQL.
     */
    public static boolean guardarEnBDD(String archivoBLOB, String nombreArchivo) throws SQLException {
        connection();
        String sql = "INSERT INTO ARCHIVOS (NOMBREUSUARIO, NOMBREARCHIVO, CONTENIDO) VALUES (?,?,?)";
        try(PreparedStatement stm = UsuarioDB.connection.prepareStatement(sql)) {
            stm.setString(1, UsuarioDB.getUsuarioApp().getNombreUsuario());
            stm.setString(2, nombreArchivo);
            stm.setBytes(3, archivoBLOB.getBytes());
            stm.executeUpdate();
        }

        return true;
    }
    /**
     * Actualiza el contenido de un archivo en la base de datos.
     *
     * @param contenidoCodificado Contenido del archivo codificado.
     * @throws RuntimeException Si ocurre un error durante la ejecución de la actualización.
     */
    public static void actualizarArchivo(String contenidoCodificado) {
        connection();
        String sql = "UPDATE ARCHIVOS SET CONTENIDO = ? WHERE NOMBREARCHIVO = ?";
        try(PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setBytes(1, contenidoCodificado.getBytes());
            stm.setString(2, GestorArchivos.getNombreArchivoSeleccionado());

            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Recupera el contenido de un archivo desde la base de datos.
     *
     * @param nombreArchivo Nombre del archivo a recuperar.
     * @return Contenido del archivo o una cadena vacía si no se encuentra.
     * @throws RuntimeException Si ocurre un error durante la ejecución de la recuperación.
     */
    public static String recuperarArchivo(String nombreArchivo) {
        connection();
        String sql = "SELECT CONTENIDO FROM ARCHIVOS WHERE nombreArchivo = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, nombreArchivo);
            try (ResultSet rs = stm.executeQuery()){
                if (rs.next()) {
                    byte[] contenidoBytes = rs.getBytes("CONTENIDO");
                    return new String(contenidoBytes);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "";
    }
    /**
     * Establece el usuario actual de la aplicación.
     *
     * @param usuarioApp Objeto Usuario que representa al usuario actual.
     */
    public static void setUsuarioApp(Usuario usuarioApp) {
        UsuarioDB.usuarioApp = usuarioApp;
    }
    /**
     * Obtiene el usuario actual de la aplicación.
     *
     * @return Objeto Usuario que representa al usuario actual.
     */
    public static Usuario getUsuarioApp() {
        return usuarioApp;
    }


    /**
     * Cierra la conexión con la base de datos.
     *
     * @return True si la conexión se cerró correctamente, false en caso contrario.
     */
    public static boolean close(){
        try {
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Borra un archivo de la base de datos.
     *
     * @param nombreArchivo Nombre del archivo a borrar.
     * @return True si la operación fue exitosa, false en caso contrario.
     */
    public static boolean borrarArchivo(String nombreArchivo) {
        connection();
        String sql = "DELETE FROM ARCHIVOS WHERE NOMBREUSUARIO = ? AND NOMBREARCHIVO = ?";
        try(PreparedStatement stm = UsuarioDB.connection.prepareStatement(sql)) {
            stm.setString(1, UsuarioDB.getUsuarioApp().getNombreUsuario());
            stm.setString(2, nombreArchivo);
            stm.executeUpdate();
        } catch (SQLException e) {
            return false;
        }

        return true;
    }
}
