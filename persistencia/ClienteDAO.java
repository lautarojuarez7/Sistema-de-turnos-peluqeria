package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modelo.Cliente;

public class ClienteDAO
{
    public void insertar(Cliente cliente)
    {
        String sql = "INSERT INTO cliente (dni, nombre, apellido, telefono) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cliente.getDni());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getApellido());
            ps.setString(4, cliente.getTelefono());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo guardar el cliente en la base de datos: " + e.getMessage());
        }
    }

    public ArrayList<Cliente> listar()
    {
        ArrayList<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT dni, nombre, apellido, telefono FROM cliente ORDER BY id";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                clientes.add(new Cliente(rs.getString("nombre"), rs.getString("apellido"),
                                         rs.getString("dni"), rs.getString("telefono")));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudieron cargar los clientes de la base de datos: " + e.getMessage());
        }
        return clientes;
    }
}
