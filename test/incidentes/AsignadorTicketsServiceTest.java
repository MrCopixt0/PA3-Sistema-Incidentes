package incidentes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AsignadorTicketsServiceTest {

    //Repositorios Simulados (Stubs) globales
    private final TicketRepository dummyTicketRepo = ticket -> {};
    private final AgenteRepository dummyAgenteRepo = new AgenteRepository(){

        @Override public void guardar(Agente agente) {}
        @Override public List<Agente> buscarPorRol(String rol) {return  new ArrayList<>();}
        @Override public Agente buscarPorId(Long id) {return null;}

    };

    @Test
    public void testCalcularPrioridadMaximaDeberiaDarCinco() {
       AsignadorTicketsService service = new AsignadorTicketsService(dummyAgenteRepo, dummyTicketRepo);

       double resultado = service.calcularPrioridad(5, 5);
       assertEquals(5.0, resultado, 0.001);
    }

    @Test
    public void testImpactoFueraDeRangoDeberiaLanzarExcepcion() {
        AsignadorTicketsService service = new AsignadorTicketsService(dummyAgenteRepo,dummyTicketRepo);

        assertThrows(IllegalArgumentException.class, ()-> {
            service.calcularPrioridad(6, 3);
        });
    }

    @Test
    public void testGravedadFueraDeRangoDeberiaLanzarExcepcion() {
        AsignadorTicketsService service = new AsignadorTicketsService(dummyAgenteRepo,dummyTicketRepo);

        //verificamos que una gravedad de 0 (inválida) lance "IllegalArgumentException"
        assertThrows(IllegalArgumentException.class, () -> {
            service.calcularPrioridad(4, 0);
        });
    }

    @Test
    public void testTicketAltaPrioridadDeberiaAsignarseAAgenteSenior() {

        //simulamos el estado de los agentes en la BD
        List<Agente> agenteEnBD = Arrays.asList(
                new Agente("Pepe", "Senior", 0),
                new Agente("Lucho", "Junior", 0)
        );
        //creamos un repositorio falso que simulara el comportamiento de filtrado del ORM
        AgenteRepository fakeAgenteRepo = new AgenteRepository() {
            @Override public void guardar(Agente agente) {}
            @Override public Agente buscarPorId(Long id) {return null;}

            @Override
            public List<Agente> buscarPorRol(String rol) {
                List<Agente> filtrados = new ArrayList<>();
                for (Agente a : agenteEnBD) {
                    if (a.getRol().equals(rol)) filtrados.add(a);
                }
                return filtrados;
            }
        };
        AsignadorTicketsService service = new AsignadorTicketsService(fakeAgenteRepo, dummyTicketRepo);

        Ticket ticketAsignado = service.crearYAsignarTicket("Fallo crítico en el servidor de base de datos", 5, 4);
        assertNotNull(ticketAsignado.getAgenteAsignado(), "Se deberia haber asignado un agente");
        assertEquals("Senior", ticketAsignado.getAgenteAsignado().getRol(), "Los tickets con prioridad>= 4.0 deben ir a un Senior");
    }

    @Test
    public void testAlgoritmoGreedyDeberiaAsignarAlSeniorConMenorCarga() {

        List<Agente> agentesEnBD = Arrays.asList(
                new Agente("Pepe", "Senior", 5),
                new Agente("Juan", "Senior", 0)
        );
        AgenteRepository fakeAgenteRepo = new AgenteRepository() {
            @Override
            public void guardar(Agente agente) {
            }

            @Override
            public Agente buscarPorId(Long id) {
                return null;
            }

            @Override
            public List<Agente> buscarPorRol(String rol) {
                List<Agente> filtrados = new ArrayList<>();
                for (Agente a : agentesEnBD) {
                    if (a.getRol().equals(rol)) filtrados.add(a);
                }
                return filtrados;
            }
        };
        AsignadorTicketsService service = new AsignadorTicketsService(fakeAgenteRepo, dummyTicketRepo);
        Ticket ticketAsignado = service.crearYAsignarTicket("Caída masiva del servicio regional", 5, 5);

        assertNotNull(ticketAsignado.getAgenteAsignado());
        assertEquals("Juan", ticketAsignado.getAgenteAsignado().getNombre(), "Debes elegir a Juan por tener menor carga");
    }
}
