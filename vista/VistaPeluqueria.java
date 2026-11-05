package vista;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import modelo.Cliente;
import modelo.Especialidad;
import modelo.Peluquero;
import modelo.Turno;


public class VistaPeluqueria
{
    private DateTimeFormatter formatoFecha;
    private DateTimeFormatter formatoHora;

    public VistaPeluqueria()
    {
        this.formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.formatoHora = DateTimeFormatter.ofPattern("HH:mm");
    }

   

    public void mostrarMenu()
    {
        System.out.println();
        System.out.println("===== PELUQUERÍA =====");
        System.out.println("1. Registrar cliente");
        System.out.println("2. Registrar peluquero");
        System.out.println("3. Ver horarios libres");
        System.out.println("4. Solicitar turno");
        System.out.println("5. Confirmar turno");
        System.out.println("6. Cancelar turno");
        System.out.println("7. Marcar turno como realizado");
        System.out.println("8. Ver turnos de un cliente");
        System.out.println("9. Ver turnos de un día");
        System.out.println("10. Ver peluqueros y clientes");
        System.out.println("0. Salir");
    }

    public void mostrarTitulo(String titulo)
    {
        System.out.println("--- " + titulo + " ---");
    }

    public void mostrarMensaje(String mensaje)
    {
        System.out.println(mensaje);
    }

    public void mostrarError(String mensaje)
    {
        System.out.println("Error: " + mensaje);
    }

  

    public void mostrarEspecialidades(Especialidad[] especialidades)
    {
        System.out.println("Especialidad:");
        for (int i = 0; i < especialidades.length; i++) {
            System.out.println("  " + (i + 1) + ". " + especialidades[i]);
        }
    }


    public void mostrarHorariosLibres(LocalDate fecha, ArrayList<LocalTime> horarios)
    {
        if (horarios.isEmpty()) {
            System.out.println("No hay horarios libres ese día.");
            return;
        }
        System.out.println("Horarios libres el " + fecha.format(formatoFecha) + ":");
        for (LocalTime hora : horarios) {
            System.out.println("  " + hora.format(formatoHora));
        }
    }


    public void mostrarTurno(Turno turno)
    {
        System.out.println(describirTurno(turno));
    }

    /** Lista de turnos con viñeta (para consultas). */
    public void mostrarTurnos(ArrayList<Turno> turnos)
    {
        for (Turno t : turnos) {
            System.out.println("- " + describirTurno(t));
        }
    }

    /** Lista de turnos numerados desde 1 (para elegir uno). */
    public void mostrarTurnosNumerados(ArrayList<Turno> turnos)
    {
        for (int i = 0; i < turnos.size(); i++) {
            System.out.println((i + 1) + ". " + describirTurno(turnos.get(i)));
        }
    }

    // ---------- Personas ----------

    public void mostrarPeluqueros(ArrayList<Peluquero> peluqueros)
    {
        if (peluqueros.isEmpty()) {
            System.out.println("(no hay peluqueros registrados)");
        }
        for (Peluquero p : peluqueros) {
            System.out.println("- " + p.getNombre() + " " + p.getApellido()
                + " | DNI " + p.getDni()
                + " | " + p.getEspecialidad()
                + " | " + p.getHoraInicio().format(formatoHora) + " a " + p.getHoraFin().format(formatoHora));
        }
    }

    public void mostrarClientes(ArrayList<Cliente> clientes)
    {
        if (clientes.isEmpty()) {
            System.out.println("(no hay clientes registrados)");
        }
        for (Cliente c : clientes) {
            System.out.println("- " + c.getNombre() + " " + c.getApellido()
                + " | DNI " + c.getDni()
                + " | Tel. " + c.getTelefono());
        }
    }

   
    private String describirTurno(Turno t)
    {
        return t.getFecha().format(formatoFecha) + " " + t.getHora().format(formatoHora)
            + " | Peluquero: " + t.getPeluquero()
            + " | Cliente: " + t.getCliente().getNombre() + " " + t.getCliente().getApellido()
            + " | Servicio: " + t.getServicio().getNombre()
            + " | " + String.format("$%.2f", t.calcularTotal())
            + " | " + t.getEstado();
    }
}