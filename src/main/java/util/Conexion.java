package util;

/*
Descripción:
Esta clase administra la conexión a la base de datos MySQL.
Proporciona un método estático que retorna una conexión válida
 utilizando la configuración definida (URL, usuario y contraseña).
Autor: Dilan Salazar
Fecha: 2025/11/19
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    // URL de conexión hacia la base de datos MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/ejercicioclase";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    // Devuelve una conexión activa hacia la base de datos
    public static Connection getConnection() throws SQLException {
        // Se obtiene la conexión directamente desde el DriverManager
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        System.out.println("Base de datos conectada de forma exitosa");
        return conn;
    }
}
