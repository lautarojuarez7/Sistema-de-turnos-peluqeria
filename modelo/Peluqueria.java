package modelo;
import java.util.ArrayList;


public class Peluqueria
{
    private ArrayList<Cliente> listaClientes;
    private ArrayList<Peluquero> listaPeluqueros;
    private ArrayList<Turno> listaTurnos;

    public Peluqueria(){
        listaClientes = new ArrayList<>();
        listaPeluqueros = new ArrayList<>();
        listaTurnos = new ArrayList<>();
    }

   
    public void registrarCliente(Cliente cliente){
        listaClientes.add(cliente);
    }

    public void registrarPeluquero(Peluquero peluquero){
        listaPeluqueros.add(peluquero);
    }

    public void registrarTurno(Turno turno){
        listaTurnos.add(turno);
    }

   
    public ArrayList<Cliente> getListaClientes(){
        return listaClientes;
    }

    public ArrayList<Peluquero> getListaPeluqueros(){
        return listaPeluqueros;
    }

    public ArrayList<Turno> getListaTurnos(){
        return listaTurnos;
    }
}