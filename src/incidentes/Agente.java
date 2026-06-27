package incidentes;

public class Agente {

    private String nombre;
    private String rol;
    private int cargaTrabajo;

    public Agente(String nombre, String rol, int cargaTrabajo) {

        this.nombre = nombre;
        this.rol = rol;
        this.cargaTrabajo = cargaTrabajo;
    }

    public String getNombre() {return nombre;}
    public String getRol() {return rol;}
    public int getCargaTrabajo(){return cargaTrabajo;}

}
