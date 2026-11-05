package modelo;

/**
 * Write a description of class Estado here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public enum Estado
{
    PENDIENTE,
    CONFIRMADO,
    REALIZADO,
    CANCELADO;

    public boolean esActivo() {
        return this == PENDIENTE || this == CONFIRMADO;
    }

    public boolean esRealizado() {
        return this == REALIZADO;
    }

    public boolean esCancelado() {
        return this == CANCELADO;
    }
}