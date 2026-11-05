package controlador;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import modelo.Calendario;
import modelo.Cliente;
import modelo.Especialidad;
import modelo.Estado;
import modelo.Peluqueria;
import modelo.Peluquero;
import modelo.Persona;
import modelo.Servicio;
import modelo.Turno;


public class GestorPeluqueria
{
    public static final int DURACION_TURNO_MINUTOS = 30;

    private Peluqueria peluqueria;


    private HashMap<String, Calendario> calendarios;

    public GestorPeluqueria()
    {
        peluqueria = new Peluqueria();
        calendarios = new HashMap<>();
    }

  

    public Cliente registrarCliente(String nombre, String apellido, String dni, String telefono)
    {
        validarDatosPersona(nombre, apellido, dni, telefono);
        if (buscarCliente(dni) != null) {
            throw new IllegalArgumentException("Ya existe un cliente con el DNI " + dni.trim());
        }
        Cliente cliente = new Cliente(nombre.trim(), apellido.trim(), dni.trim(), telefono.trim());
        peluqueria.registrarCliente(cliente);
        return cliente;
    }

    public Peluquero registrarPeluquero(String nombre, String apellido, String dni, String telefono,
                                        Especialidad especialidad, LocalTime horaInicio, LocalTime horaFin)
    {
        validarDatosPersona(nombre, apellido, dni, telefono);
        if (especialidad == null) {
            throw new IllegalArgumentException("Tenés que elegir una especialidad");
        }
        if (horaInicio == null || horaFin == null) {
            throw new IllegalArgumentException("Tenés que indicar el horario de trabajo");
        }
        if (!horaInicio.isBefore(horaFin)) {
            throw new IllegalArgumentException("La hora de inicio tiene que ser anterior a la hora de fin");
        }
        if (buscarPeluquero(dni) != null) {
            throw new IllegalArgumentException("Ya existe un peluquero con el DNI " + dni.trim());
        }

        Peluquero peluquero = new Peluquero(nombre.trim(), apellido.trim(), dni.trim(), telefono.trim(),
                                            especialidad, horaInicio, horaFin);
        String clave = nombreCompleto(peluquero);
        if (calendarios.containsKey(clave)) {
            throw new IllegalArgumentException("Ya hay un peluquero que se llama " + clave);
        }

        peluqueria.registrarPeluquero(peluquero);
        calendarios.put(clave, new Calendario(horaInicio, horaFin));
        return peluquero;
    }

    // =====================================================================
    //  BÚSQUEDAS
    // =====================================================================

    /** Devuelve el cliente con ese DNI, o null si no existe. */
    public Cliente buscarCliente(String dni)
    {
        if (dni == null) {
            return null;
        }
        String buscado = dni.trim();
        for (Cliente c : peluqueria.getListaClientes()) {
            if (c.getDni().equals(buscado)) {
                return c;
            }
        }
        return null;
    }

    /** Devuelve el peluquero con ese DNI, o null si no existe. */
    public Peluquero buscarPeluquero(String dni)
    {
        if (dni == null) {
            return null;
        }
        String buscado = dni.trim();
        for (Peluquero p : peluqueria.getListaPeluqueros()) {
            if (p.getDni().equals(buscado)) {
                return p;
            }
        }
        return null;
    }

    public ArrayList<Cliente> getClientes()
    {
        return peluqueria.getListaClientes();
    }

    public ArrayList<Peluquero> getPeluqueros()
    {
        return peluqueria.getListaPeluqueros();
    }

   

   
    public ArrayList<LocalTime> horariosLibres(Peluquero peluquero, LocalDate fecha)
    {
        Calendario calendario = calendarioDe(peluquero);
        ArrayList<LocalTime> libres = new ArrayList<>();
        LocalDateTime ahora = LocalDateTime.now();

        LocalTime hora = calendario.getHoraApertura();
        while (hora.isBefore(calendario.getHoraCierre())) {
            LocalDateTime fechaHora = LocalDateTime.of(fecha, hora);
            if (calendario.estaLibre(fechaHora) && !fechaHora.isBefore(ahora)) {
                libres.add(hora);
            }
            LocalTime siguiente = hora.plusMinutes(DURACION_TURNO_MINUTOS);
            if (siguiente.isBefore(hora)) {
                break; // pasó la medianoche, no seguimos
            }
            hora = siguiente;
        }
        return libres;
    }

    
    
    public Turno solicitarTurno(Cliente cliente, Peluquero peluquero, LocalDate fecha,
                                LocalTime hora, String nombreServicio, double precio)
    {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no está registrado");
        }
        if (peluquero == null) {
            throw new IllegalArgumentException("El peluquero no está registrado");
        }
        if (fecha == null || hora == null) {
            throw new IllegalArgumentException("Tenés que indicar fecha y hora");
        }
        validarTexto(nombreServicio, "El servicio");
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        if (fecha.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Esa fecha ya pasó");
        }

        if (!horariosLibres(peluquero, fecha).contains(hora)) {
            throw new IllegalArgumentException("Ese horario no está disponible "
                + "(está ocupado, es domingo, ya pasó o está fuera del horario del peluquero)");
        }

        Calendario calendario = calendarioDe(peluquero);
        if (!calendario.reservarHorario(LocalDateTime.of(fecha, hora))) {
            throw new IllegalArgumentException("No se pudo reservar el horario");
        }

        Turno turno = new Turno(fecha, hora, nombreCompleto(peluquero), Estado.PENDIENTE,
                                cliente, new Servicio(nombreServicio.trim()), precio);
        peluqueria.registrarTurno(turno);
        return turno;
    }

  
    public void confirmarTurno(Turno turno)
    {
        if (turno == null) {
            throw new IllegalArgumentException("No se indicó ningún turno");
        }
        if (turno.getEstado() != Estado.PENDIENTE) {
            throw new IllegalArgumentException("Solo se puede confirmar un turno pendiente (este está "
                + turno.getEstado() + ")");
        }
        turno.setEstado(Estado.CONFIRMADO);
    }


    public void cancelarTurno(Turno turno)
    {
        if (turno == null) {
            throw new IllegalArgumentException("No se indicó ningún turno");
        }
        if (!turno.getEstado().esActivo()) {
            throw new IllegalArgumentException("Solo se puede cancelar un turno pendiente o confirmado (este está "
                + turno.getEstado() + ")");
        }
        turno.setEstado(Estado.CANCELADO);

        Calendario calendario = calendarios.get(turno.getPeluquero());
        if (calendario != null) {
            calendario.liberarHorario(LocalDateTime.of(turno.getFecha(), turno.getHora()));
        }
    }

 
    public void marcarRealizado(Turno turno)
    {
        if (turno == null) {
            throw new IllegalArgumentException("No se indicó ningún turno");
        }
        if (!turno.getEstado().esActivo()) {
            throw new IllegalArgumentException("Solo se puede realizar un turno pendiente o confirmado (este está "
                + turno.getEstado() + ")");
        }
        turno.setEstado(Estado.REALIZADO);
    }

  
    public ArrayList<Turno> getTurnos()
    {
        return peluqueria.getListaTurnos();
    }

    /** Todos los turnos de un cliente (cualquier estado). */
    public ArrayList<Turno> getTurnosDeCliente(String dni)
    {
        ArrayList<Turno> resultado = new ArrayList<>();
        String buscado = (dni == null) ? "" : dni.trim();
        for (Turno t : peluqueria.getListaTurnos()) {
            if (t.getCliente().getDni().equals(buscado)) {
                resultado.add(t);
            }
        }
        return resultado;
    }


    public ArrayList<Turno> getTurnosActivosDeCliente(String dni)
    {
        ArrayList<Turno> resultado = new ArrayList<>();
        for (Turno t : getTurnosDeCliente(dni)) {
            if (t.getEstado().esActivo()) {
                resultado.add(t);
            }
        }
        return resultado;
    }


    public ArrayList<Turno> getTurnosDelDia(LocalDate fecha)
    {
        ArrayList<Turno> resultado = new ArrayList<>();
        for (Turno t : peluqueria.getListaTurnos()) {
            if (t.getFecha().equals(fecha)) {
                resultado.add(t);
            }
        }
        resultado.sort(Comparator.comparing(Turno::getHora));
        return resultado;
    }

   
    private String nombreCompleto(Persona persona)
    {
        return persona.getNombre() + " " + persona.getApellido();
    }

    private Calendario calendarioDe(Peluquero peluquero)
    {
        if (peluquero == null) {
            throw new IllegalArgumentException("El peluquero no está registrado");
        }
        Calendario calendario = calendarios.get(nombreCompleto(peluquero));
        if (calendario == null) {
            throw new IllegalArgumentException("El peluquero no está registrado");
        }
        return calendario;
    }

    private void validarDatosPersona(String nombre, String apellido, String dni, String telefono)
    {
        validarTexto(nombre, "El nombre");
        validarTexto(apellido, "El apellido");
        validarTexto(dni, "El DNI");
        validarTexto(telefono, "El teléfono");
        if (!dni.trim().matches("\\d+")) {
            throw new IllegalArgumentException("El DNI tiene que contener solo números");
        }
    }

    private void validarTexto(String valor, String campo)
    {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " no puede estar vacío");
        }
    }
}
