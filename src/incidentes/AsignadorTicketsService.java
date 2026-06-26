package incidentes;

public class AsignadorTicketsService {

    public double calcularPrioridad(int impacto, int gravedad) {
        return (impacto * 0.6) + (gravedad * 0.4);
    }
}
