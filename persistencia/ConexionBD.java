package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD
{
    private static final String URL =
        "jdbc:mysql://localhost:3306/peluqueria?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USUARIO = "root";
    private static final String CLAVE = "dagatti46590483";

    public static Connection obtenerConexion() throws SQLException
    {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
}
