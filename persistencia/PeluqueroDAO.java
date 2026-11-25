package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;

import modelo.Especialidad;
import modelo.Peluquero;

public class PeluqueroDAO
{
    public void insertar(Peluquero peluquero)
    {
        String sql = "INSERT INTO peluquero (dni, nombre, apellido, telefono, especialidad, hora_inicio, hora_fin) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, peluquero.getDni());
            ps.setString(2, peluquero.getNombre());
            ps.setString(3, peluquero.getApellido());
            ps.setString(4, peluquero.getTelefono());
            ps.setString(5, peluquero.getEspecialidad().name());
            ps.setObject(6, peluquero.getHoraInicio());
            ps.setObject(7, peluquero.getHoraFin());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo guardar el peluquero en la base de datos: " + e.getMessage());
        }
    }

    public ArrayList<Peluquero> listar()
    {
        ArrayList<Peluquero> peluqueros = new ArrayList<>();
        String sql = "SELECT dni, nombre, apellido, telefono, especialidad, hora_inicio, hora_fin "
                   + "FROM peluquero ORDER BY id";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                peluqueros.add(new Peluquero(
                    rs.getString("nombre"), rs.getString("apellido"),
                    rs.getString("dni"), rs.getString("telefono"),
                    Especialidad.valueOf(rs.getString("especialidad")),
                    rs.getObject("hora_inicio", LocalTime.class),
                    rs.getObject("hora_fin", LocalTime.class)));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudieron cargar los peluqueros de la base de datos: " + e.getMessage());
        }
        return peluqueros;
    }
}
