import java.util.ArrayList;

/**
 * Write a description of class Peluqueria here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Peluqueria
{
    private ArrayList<Cliente> listaClientes;
    private ArrayList<Peluquero> listaPeluqueros;
    private ArrayList<Turno> listaTurnos;
    
    public Peluqueria(){
        listaClientes = new ArrayList<>();
        listapeluqueros = new ArrayList<>();
        listaTurnos = new ArrayList<>();
    }
    
    public void registrarPeluquero(Peluquero peluquero){
        listaPeluqueros.add(peluquero);
    }
    
    public void registrarTurno(Turno turno){
        listaTurnos.add(turno);    
    }
}