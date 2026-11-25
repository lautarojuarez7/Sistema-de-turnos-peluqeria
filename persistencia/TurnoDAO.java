package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import modelo.Cliente;
import modelo.Estado;
import modelo.Peluquero;
import modelo.Servicio;
import modelo.Turno;

public class TurnoDAO
{
    public void insertar(Turno turno, String dniPeluquero)
    {
        String sql = "INSERT INTO turno (fecha, hora, peluquero_dni, cliente_dni, servicio, precio, estado) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, turno.getFecha());
            ps.setObject(2, turno.getHora());
            ps.setString(3, dniPeluquero);
            ps.setString(4, turno.getCliente().getDni());
            ps.setString(5, turno.getServicio().getNombre());
            ps.setDouble(6, turno.getPrecio());
            ps.setString(7, turno.getEstado().name());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo guardar el turno en la base de datos: " + e.getMessage());
        }
    }

    public void actualizarEstado(Turno turno, String dniPeluquero, Estado nuevoEstado)
    {
        String sql = "UPDATE turno SET estado = ? "
                   + "WHERE peluquero_dni = ? AND fecha = ? AND hora = ? "
                   + "AND estado IN ('PENDIENTE', 'CONFIRMADO')";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado.name());
            ps.setString(2, dniPeluquero);
            ps.setObject(3, turno.getFecha());
            ps.setObject(4, turno.getHora());
            int filas = ps.executeUpdate();
            if (filas == 0) {
                throw new IllegalStateException("No se encontró ese turno activo en la base de datos");
            }
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo actualizar el turno en la base de datos: " + e.getMessage());
        }
    }

    public ArrayList<Turno> listar(ArrayList<Cliente> clientes, ArrayList<Peluquero> peluqueros)
    {
        ArrayList<Turno> turnos = new ArrayList<>();
        String sql = "SELECT fecha, hora, peluquero_dni, cliente_dni, servicio, precio, estado "
                   + "FROM turno ORDER BY id";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Cliente cliente = buscarCliente(clientes, rs.getString("cliente_dni"));
                Peluquero peluquero = buscarPeluquero(peluqueros, rs.getString("peluquero_dni"));
                if (cliente == null || peluquero == null) {
                    continue;
                }
                turnos.add(new Turno(
                    rs.getObject("fecha", LocalDate.class),
                    rs.getObject("hora", LocalTime.class),
                    peluquero.getNombre() + " " + peluquero.getApellido(),
                    Estado.valueOf(rs.getString("estado")),
                    cliente,
                    new Servicio(rs.getString("servicio")),
                    rs.getDouble("precio")));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudieron cargar los turnos de la base de datos: " + e.getMessage());
        }
        return turnos;
    }

    private Cliente buscarCliente(ArrayList<Cliente> clientes, String dni)
    {
        for (Cliente c : clientes) {
            if (c.getDni().equals(dni)) {
                return c;
            }
        }
        return null;
    }

    private Peluquero buscarPeluquero(ArrayList<Peluquero> peluqueros, String dni)
    {
        for (Peluquero p : peluqueros) {
            if (p.getDni().equals(dni)) {
                return p;
            }
        }
        return null;
    }
}
