
/**
 * Write a description of class Estado here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Estado
{
    private String nombre;
    
    public Estado (String nombre){
        this.nombre = nombre;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public boolean esActivo(){
        return nombre.equalsIgnoreCase("Confirmado");
    }
    
    public boolean esCnacelado(){
        return nombre.equalsIgnoreCase("Cnacelado");
    }
}