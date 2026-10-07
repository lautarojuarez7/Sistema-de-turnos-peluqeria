import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Calendario
{
  private LocalTime horaApertura;
  private LocalTime horaCierre;
  private ArrayList <LocalDateTime> horariosOcupados;

public Calendario(LocalTime horaApertura, LocalTime horaCierre){
    this.horaApertura = horaApertura;
    this.horaCierre = horaCierre;
    this.horariosOcupados = new ArrayList<>();
}
public LocalTime getHoraApertura(){
    return horaApertura;
}
public LocalTime getHoraCierre(){
    return horaCierre;
}
public boolean estaDentroDelHorario (LocalDateTime fechaHora){
    LocalTime hora = fechaHora.toLocalTime();
    DayOfWeek dia = fechaHora.getDayOfWeek();
    return dia != DayOfWeek.SUNDAY
    && !hora.isBefore(horaApertura)
    && hora.isBefore(horaCierre);
}
public boolean estaLibre(LocalDateTime fechaHora){
    return estaDentroDelHorario(fechaHora) && !horariosOcupados.contains(fechaHora);
}
public boolean reservarHorario(LocalDateTime fechaHora){
    if (estaLibre(fechaHora)){
        horariosOcupados.add(fechaHora);
        return true;
    }
    return false;
}
public void liberarHorario(LocalDateTime fechaHora){
    horariosOcupados.remove(fechaHora);
}
public ArrayList<LocalDateTime> getHorariosOcupadosDelDia(LocalDate fecha){
ArrayList<LocalDateTime> resultado = new ArrayList<>();
for (LocalDateTime h : horariosOcupados) {
    if (h.toLocalDate().equals(fecha)) { 
        resultado.add(h);
        }
    }
    return resultado;
}
}