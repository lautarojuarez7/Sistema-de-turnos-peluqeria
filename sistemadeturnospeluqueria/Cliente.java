
/**
 * Write a description of class Cliente here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Cliente
{
   private String nombre;
   private String apellido;
   private int dni;
   private int telefono;
   
   public Cliente (String nombre, String apellido, int telefono, int dni){
       this.nombre = nombre;
       this.apellido = apellido;
       this.telefono = telefono;
       this.dni = dni;
   }
   
   public String getNombre(){
       return nombre;
   }
   public String getApellido(){
       return apellido;
   }
   public int getTelefono(){
       return telefono;
   }
   public int getDni(){
       return dni;
   }
}