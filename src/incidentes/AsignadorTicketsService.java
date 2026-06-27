package incidentes;
import java.util.List;

public class AsignadorTicketsService {

    private final AgenteRepository agenteRepository;
    private final TicketRepository ticketRepository;

    //inyectamos las dependencias de los repositorios del ORM

    public AsignadorTicketsService(AgenteRepository agenteRepository, TicketRepository ticketRepository) {

        this.agenteRepository = agenteRepository;
        this.ticketRepository = ticketRepository;
    }

    public double calcularPrioridad(int impacto, int gravedad){
        validarRangos(impacto, gravedad);
        return (impacto * 0.6) + (gravedad * 0.4);
    }

    private void validarRangos(int impacto, int gravedad) {

        if (impacto < 1 || impacto > 5 || gravedad < 1 || gravedad > 5) {
            throw new IllegalArgumentException("El impacto y la gravedad deben estar entre 1 y 5");
        }
    }

    //metodo principal
    public Ticket crearYAsignarTicket(String descripcion, int impacto, int gravedad) {
        double prioridad = calcularPrioridad(impacto, gravedad);
        Ticket ticket = new Ticket(descripcion, impacto, gravedad, prioridad);

        String rolRequerido = (prioridad >= 4.0) ? "Senior" : "Junior";

        //esto consultara los agentes aptos directamente desde la base de datos via ORM
        List<Agente> agentesDisponibles = agenteRepository.buscarPorRol(rolRequerido);

        Agente mejorAgente = null;
        for (Agente a : agentesDisponibles) {
            if (mejorAgente == null || a.getCargaTrabajo() < mejorAgente.getCargaTrabajo()) {
                mejorAgente = a;
            }
        }
        if (mejorAgente != null) {
            ticket.setAgenteAsignado(mejorAgente);

            //se actualiza la carga del agente elegido
            mejorAgente.setCargaTrabajo(mejorAgente.getCargaTrabajo() + 1);

            //sincronizamos la nueva carga de trabajo en la BD
            agenteRepository.guardar(mejorAgente);
        }
        //se guarda el ticket
        ticketRepository.guardar(ticket);

        return ticket;
    }

}
