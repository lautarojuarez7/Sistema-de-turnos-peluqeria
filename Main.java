import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import controlador.ControladorPeluqueria;
import controlador.GestorPeluqueria;
import modelo.Especialidad;
import vista.VistaPeluqueria;


public class Main
{
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");

    private static VistaPeluqueria vista;
    private static ControladorPeluqueria controlador;

    public static void main(String[] args)
    {
        GestorPeluqueria gestor = new GestorPeluqueria();
        vista = new VistaPeluqueria();
        controlador = new ControladorPeluqueria(gestor, vista);

        int opcion;
        do {
            vista.mostrarMenu();
            opcion = leerEntero("Elegí una opción: ");

            switch (opcion) {
                case 1:  registrarCliente();       break;
                case 2:  registrarPeluquero();     break;
                case 3:  verHorariosLibres();      break;
                case 4:  solicitarTurno();         break;
                case 5:  confirmarTurno();         break;
                case 6:  cancelarTurno();          break;
                case 7:  marcarTurnoRealizado();   break;
                case 8:  verTurnosDeCliente();     break;
                case 9:  verTurnosDelDia();        break;
                case 10: controlador.mostrarPeluquerosYClientes(); break;
                case 0:                            break;
                default: vista.mostrarMensaje("Opción inválida.");
            }
        } while (opcion != 0);

        vista.mostrarMensaje("¡Hasta luego!");
        scanner.close();
    }

    
    private static void registrarCliente()
    {
        vista.mostrarTitulo("Registrar cliente");
        String nombre = leerTexto("Nombre: ");
        String apellido = leerTexto("Apellido: ");
        String dni = leerTexto("DNI: ");
        String telefono = leerTexto("Teléfono: ");

        controlador.registrarCliente(nombre, apellido, dni, telefono);
    }

    private static void registrarPeluquero()
    {
        vista.mostrarTitulo("Registrar peluquero");
        String nombre = leerTexto("Nombre: ");
        String apellido = leerTexto("Apellido: ");
        String dni = leerTexto("DNI: ");
        String telefono = leerTexto("Teléfono: ");
        Especialidad especialidad = leerEspecialidad();
        LocalTime horaInicio = leerHora("Hora de inicio de trabajo (HH:mm): ");
        LocalTime horaFin = leerHora("Hora de fin de trabajo (HH:mm): ");

        controlador.registrarPeluquero(nombre, apellido, dni, telefono, especialidad, horaInicio, horaFin);
    }

    private static void verHorariosLibres()
    {
        vista.mostrarTitulo("Horarios libres");
        String dniPeluquero = leerTexto("DNI del peluquero: ");
        LocalDate fecha = leerFecha("Fecha (dd/MM/yyyy): ");

        controlador.mostrarHorariosLibres(dniPeluquero, fecha);
    }

    private static void solicitarTurno()
    {
        vista.mostrarTitulo("Solicitar turno");
        String dniCliente = leerTexto("DNI del cliente: ");
        if (!controlador.existeCliente(dniCliente)) {
            vista.mostrarError("No existe un cliente con ese DNI. Registralo primero (opción 1).");
            return;
        }

        String dniPeluquero = leerTexto("DNI del peluquero: ");
        if (!controlador.existePeluquero(dniPeluquero)) {
            vista.mostrarError("No existe un peluquero con ese DNI. Registralo primero (opción 2).");
            return;
        }

        LocalDate fecha = leerFecha("Fecha (dd/MM/yyyy): ");
        boolean hayHorarios = controlador.mostrarHorariosLibres(dniPeluquero, fecha);
        if (!hayHorarios) {
            return;
        }

        LocalTime hora = leerHora("Hora elegida (HH:mm): ");
        String servicio = leerTexto("Servicio (ej: Corte): ");
        double precio = leerDecimal("Precio: $");

        controlador.solicitarTurno(dniCliente, dniPeluquero, fecha, hora, servicio, precio);
    }

    private static void confirmarTurno()
    {
        vista.mostrarTitulo("Confirmar turno");
        String dni = leerTexto("DNI del cliente: ");
        int cantidad = controlador.mostrarTurnosActivos(dni);
        if (cantidad > 0) {
            int numero = leerEntero("Número de turno (0 para volver): ");
            if (numero != 0) {
                controlador.confirmarTurno(dni, numero);
            }
        }
    }

    private static void cancelarTurno()
    {
        vista.mostrarTitulo("Cancelar turno");
        String dni = leerTexto("DNI del cliente: ");
        int cantidad = controlador.mostrarTurnosActivos(dni);
        if (cantidad > 0) {
            int numero = leerEntero("Número de turno (0 para volver): ");
            if (numero != 0) {
                controlador.cancelarTurno(dni, numero);
            }
        }
    }

    private static void marcarTurnoRealizado()
    {
        vista.mostrarTitulo("Marcar turno como realizado");
        String dni = leerTexto("DNI del cliente: ");
        int cantidad = controlador.mostrarTurnosActivos(dni);
        if (cantidad > 0) {
            int numero = leerEntero("Número de turno (0 para volver): ");
            if (numero != 0) {
                controlador.marcarTurnoRealizado(dni, numero);
            }
        }
    }

    private static void verTurnosDeCliente()
    {
        vista.mostrarTitulo("Turnos de un cliente");
        String dni = leerTexto("DNI del cliente: ");

        controlador.mostrarTurnosDeCliente(dni);
    }

    private static void verTurnosDelDia()
    {
        vista.mostrarTitulo("Turnos de un día");
        LocalDate fecha = leerFecha("Fecha (dd/MM/yyyy): ");

        controlador.mostrarTurnosDelDia(fecha);
    }

    
    private static String leerTexto(String mensaje)
    {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private static int leerEntero(String mensaje)
    {
        while (true) {
            String texto = leerTexto(mensaje);
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                vista.mostrarMensaje("Ingresá un número entero.");
            }
        }
    }

    private static double leerDecimal(String mensaje)
    {
        while (true) {
            String texto = leerTexto(mensaje).replace(',', '.');
            try {
                return Double.parseDouble(texto);
            } catch (NumberFormatException e) {
                vista.mostrarMensaje("Ingresá un número (ej: 5000 o 5000.50).");
            }
        }
    }

    private static LocalDate leerFecha(String mensaje)
    {
        while (true) {
            String texto = leerTexto(mensaje);
            try {
                return LocalDate.parse(texto, FORMATO_FECHA);
            } catch (DateTimeParseException e) {
                vista.mostrarMensaje("Fecha inválida. Usá el formato dd/MM/yyyy (ej: 15/11/2026).");
            }
        }
    }

    private static LocalTime leerHora(String mensaje)
    {
        while (true) {
            String texto = leerTexto(mensaje);
            try {
                return LocalTime.parse(texto, FORMATO_HORA);
            } catch (DateTimeParseException e) {
                vista.mostrarMensaje("Hora inválida. Usá el formato HH:mm (ej: 09:30).");
            }
        }
    }

    private static Especialidad leerEspecialidad()
    {
        Especialidad[] especialidades = Especialidad.values();
        vista.mostrarEspecialidades(especialidades);
        while (true) {
            int numero = leerEntero("Elegí una especialidad: ");
            if (numero >= 1 && numero <= especialidades.length) {
                return especialidades[numero - 1];
            }
            vista.mostrarMensaje("Opción inválida.");
        }
    }
}