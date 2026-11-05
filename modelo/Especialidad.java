package modelo;

public enum Especialidad {
    PELO("Corte de pelo"),
    BARBA("Barba"),
    CEJAS("Cejas");

    private final String nombre;

    Especialidad(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean esPelo() {
        return this == PELO;
    }

    public boolean esBarba() {
        return this == BARBA;
    }

    public boolean esCejas() {
        return this == CEJAS;
    }
}
