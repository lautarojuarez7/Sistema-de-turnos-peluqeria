package modelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Turno {

  
    private LocalDate fecha;
    private LocalTime hora;
    private String peluquero;
    private Estado estado;
    private Cliente cliente;
    private Servicio servicio;
    private double precio;

    public Turno(LocalDate fecha, LocalTime hora, String peluquero,
                 Estado estado, Cliente cliente, Servicio servicio, double precio) {
        this.fecha = fecha;
        this.hora = hora;
        this.peluquero = peluquero;
        this.estado = estado;
        this.cliente = cliente;
        this.servicio = servicio;
        this.precio = precio;
    }


    public LocalDate getFecha() { return fecha; }
    public LocalTime getHora() { return hora; }
    public String getPeluquero() { return peluquero; }
    public Estado getEstado() { return estado; }
    public Cliente getCliente() { return cliente; }
    public Servicio getServicio() { return servicio; }
    public double getPrecio() { return precio; }


    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public void setHora(LocalTime hora) { this.hora = hora; }
    public void setPeluquero(String peluquero) { this.peluquero = peluquero; }
    public void setEstado(Estado estado) { this.estado = estado; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public void setServicio(Servicio servicio) { this.servicio = servicio; }
    public void setPrecio(double precio) { this.precio = precio; }

   
    public double calcularTotal() {
        return precio;
    }

   
    public void mostrarEstado() {
        DateTimeFormatter fmtFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter fmtHora = DateTimeFormatter.ofPattern("HH:mm");

        System.out.println("=== TURNO ===");
        System.out.println("Fecha: " + fecha.format(fmtFecha));
        System.out.println("Hora: " + hora.format(fmtHora));
        System.out.println("Peluquero: " + peluquero);
        System.out.println("Cliente: " + cliente);
        System.out.println("Servicio: " + servicio);
        System.out.println("Total: $" + calcularTotal());
        System.out.println("Estado: " + estado);
    }
}