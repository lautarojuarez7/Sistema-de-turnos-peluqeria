package modelo;
import java.time.LocalTime;

public class Peluquero extends Persona {

    private Especialidad especialidad;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    public Peluquero(String nombre, String apellido, String dni, String telefono,
                     Especialidad especialidad, LocalTime horaInicio, LocalTime horaFin) {
        super(nombre, apellido, dni, telefono);
        this.especialidad = especialidad;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public Especialidad getEspecialidad() { return especialidad; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }
}