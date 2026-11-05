package controlador;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import modelo.Especialidad;
import modelo.Peluquero;
import modelo.Turno;
import vista.VistaPeluqueria;


public class ControladorPeluqueria
{
    private GestorPeluqueria gestor;
    private VistaPeluqueria vista;

    public ControladorPeluqueria(GestorPeluqueria gestor, VistaPeluqueria vista)
    {
        this.gestor = gestor;
        this.vista = vista;
    }


    public void registrarCliente(String nombre, String apellido, String dni, String telefono)
    {
        try {
            gestor.registrarCliente(nombre, apellido, dni, telefono);
            vista.mostrarMensaje("Cliente registrado correctamente.");
        } catch (IllegalArgumentException e) {
            vista.mostrarError(e.getMessage());
        }
    }

    public void registrarPeluquero(String nombre, String apellido, String dni, String telefono,
                                   Especialidad especialidad, LocalTime horaInicio, LocalTime horaFin)
    {
        try {
            gestor.registrarPeluquero(nombre, apellido, dni, telefono, especialidad, horaInicio, horaFin);
            vista.mostrarMensaje("Peluquero registrado correctamente.");
        } catch (IllegalArgumentException e) {
            vista.mostrarError(e.getMessage());
        }
    }

  

    public boolean existeCliente(String dni)
    {
        return gestor.buscarCliente(dni) != null;
    }

    public boolean existePeluquero(String dni)
    {
        return gestor.buscarPeluquero(dni) != null;
    }

 

  
    public boolean mostrarHorariosLibres(String dniPeluquero, LocalDate fecha)
    {
        Peluquero peluquero = gestor.buscarPeluquero(dniPeluquero);
        if (peluquero == null) {
            vista.mostrarError("No existe un peluquero con ese DNI");
            return false;
        }
        ArrayList<LocalTime> libres = gestor.horariosLibres(peluquero, fecha);
        vista.mostrarHorariosLibres(fecha, libres);
        return !libres.isEmpty();
    }


    public void solicitarTurno(String dniCliente, String dniPeluquero, LocalDate fecha,
                               LocalTime hora, String servicio, double precio)
    {
        try {
            Turno turno = gestor.solicitarTurno(gestor.buscarCliente(dniCliente),
                                                gestor.buscarPeluquero(dniPeluquero),
                                                fecha, hora, servicio, precio);
            vista.mostrarMensaje("Turno solicitado. Queda PENDIENTE hasta que se confirme:");
            vista.mostrarTurno(turno);
        } catch (IllegalArgumentException e) {
            vista.mostrarError(e.getMessage());
        }
    }

  
    public int mostrarTurnosActivos(String dniCliente)
    {
        if (!existeCliente(dniCliente)) {
            vista.mostrarError("No existe un cliente con ese DNI");
            return 0;
        }
        ArrayList<Turno> turnos = gestor.getTurnosActivosDeCliente(dniCliente);
        if (turnos.isEmpty()) {
            vista.mostrarMensaje("Ese cliente no tiene turnos pendientes ni confirmados.");
            return 0;
        }
        vista.mostrarTurnosNumerados(turnos);
        return turnos.size();
    }

   
    public void confirmarTurno(String dniCliente, int numero)
    {
        try {
            gestor.confirmarTurno(turnoActivo(dniCliente, numero));
            vista.mostrarMensaje("Turno confirmado.");
        } catch (IllegalArgumentException e) {
            vista.mostrarError(e.getMessage());
        }
    }

    public void cancelarTurno(String dniCliente, int numero)
    {
        try {
            gestor.cancelarTurno(turnoActivo(dniCliente, numero));
            vista.mostrarMensaje("Turno cancelado. El horario quedó libre.");
        } catch (IllegalArgumentException e) {
            vista.mostrarError(e.getMessage());
        }
    }

    public void marcarTurnoRealizado(String dniCliente, int numero)
    {
        try {
            gestor.marcarRealizado(turnoActivo(dniCliente, numero));
            vista.mostrarMensaje("Turno marcado como realizado.");
        } catch (IllegalArgumentException e) {
            vista.mostrarError(e.getMessage());
        }
    }


    public void mostrarTurnosDeCliente(String dniCliente)
    {
        if (!existeCliente(dniCliente)) {
            vista.mostrarError("No existe un cliente con ese DNI");
            return;
        }
        ArrayList<Turno> turnos = gestor.getTurnosDeCliente(dniCliente);
        if (turnos.isEmpty()) {
            vista.mostrarMensaje("Ese cliente no tiene turnos.");
        } else {
            vista.mostrarTurnos(turnos);
        }
    }

    public void mostrarTurnosDelDia(LocalDate fecha)
    {
        ArrayList<Turno> turnos = gestor.getTurnosDelDia(fecha);
        if (turnos.isEmpty()) {
            vista.mostrarMensaje("No hay turnos ese día.");
        } else {
            vista.mostrarTurnos(turnos);
        }
    }

    public void mostrarPeluquerosYClientes()
    {
        vista.mostrarTitulo("Peluqueros");
        vista.mostrarPeluqueros(gestor.getPeluqueros());
        vista.mostrarTitulo("Clientes");
        vista.mostrarClientes(gestor.getClientes());
    }


    private Turno turnoActivo(String dniCliente, int numero)
    {
        ArrayList<Turno> turnos = gestor.getTurnosActivosDeCliente(dniCliente);
        if (numero < 1 || numero > turnos.size()) {
            throw new IllegalArgumentException("Número de turno inválido");
        }
        return turnos.get(numero - 1);
    }
} 