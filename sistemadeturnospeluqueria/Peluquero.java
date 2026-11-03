import java.time.LocalTime;

public class Peluquero
{
    private String nombreCompleto;
    private Especialidad especialidad;
    private double precio;
    private LocalTime horaInicio;
    private LocalTime horaFin;
 
public Peluquero(String nombreCompleto, Especialidad especialidad, double precio, LocalTime horaInicio, LocalTime horaFin){
    this.nombreCompleto = nombreCompleto;
    this.especialidad = especialidad;
    this.precio = precio;
    this.horaInicio = horaInicio;
    this.horaFin = horaFin; 
}
public String getNombre(){
    return nombreCompleto;
}
public Especialidad getEspecialidad(){
    return especialidad;
}
public double getPrecio(){
    return precio;
}
public LocalTime getHoraInicio(){
    return horaInicio;
}
public LocalTime getHoraFin(){
    return horaFin;
}
public void setPrecio(double precio){
    this.precio = precio;
}
public void setHoraInicio(LocalTime horaInicio){
    this.horaInicio = horaInicio;
}
public void setHoraFin(LocalTime horaFin){
    this.horaFin = horaFin;
}
}