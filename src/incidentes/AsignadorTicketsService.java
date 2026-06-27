package incidentes;

public class AsignadorTicketsService {

    public double calcularPrioridad(int impacto, int gravedad) {

        if (impacto < 1 || impacto > 5 || gravedad < 1 || gravedad > 5){
            throw new IllegalArgumentException("El impacto y la gravedad deben estar entre 1 y 5");
        }
        return (impacto * 0.6) + (gravedad * 0.4);
    }
}
