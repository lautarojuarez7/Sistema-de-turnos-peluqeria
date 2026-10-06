import java.time.LocalDateTime;


public class Turno
{
   private LocalDateTime fechaHora;
   private Peluquero peluquero;
   private Estado estado;
   private Cliente cliente;
   private Servicio servicio;
   private double precio;

public Turno (LocalDateTime fechaHora, Peluquero peluquero, Estado estado, Cliente cliente, Servicio servicio, double precio)
{
    this.fechaHora = fechaHora;
    this.peluquero = peluquero;
    this.estado = estado;
    this.cliente = cliente;
    this.servicio = servicio;
    this.precio = precio;
}
public LocalDateTime getFechaHora(){
    return fechaHora;
    
}
public Peluquero getPeluquero(){
    return peluquero;
}
public Estado getEstado(){
    return estado;
}
public Cliente getCliente(){
    return cliente;
}
public Servicio getServicio(){
    return servicio;
}
public double getPrecio(){
    return precio;
}
public void setFechaHora(LocalDateTime fechaHora) {
    this.fechaHora = fechaHora;
}
public void setEstado(Estado estado){
    this.estado = estado;
}
public void mostrarEstado(){
    System.out.println("Turno de " + cliente.getNombre() + " con " + peluquero.getNombre() + " - Estado: " + estado);
}
}