package incidentes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AsignadorTicketsServiceTest {

    @Mock
    private AgenteRepository agenteRepository;

    @Mock
    private TicketRepository ticketRepository;

    private AsignadorTicketsService service;

    @BeforeEach
    public void setUp() {
        service = new AsignadorTicketsService(agenteRepository, ticketRepository);
    }

    @Test
    public void testCalcularPrioridadMaximaDeberiaDarCinco() {
        double resultado = service.calcularPrioridad(5, 5);
        assertEquals(5.0, resultado, 0.001);
    }

    @Test
    public void testImpactoFueraDeRangoDeberiaLanzarExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.calcularPrioridad(6, 3);
        });
    }

    @Test
    public void testGravedadFueraDeRangoDeberiaLanzarExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.calcularPrioridad(4, 0);
        });
    }

    @Test
    public void testTicketAltaPrioridadDeberiaAsignarseAAgenteSenior() {
        List<Agente> agentesSimulados = Arrays.asList(
                new Agente("Pepe", "Senior", 0)
        );

        // Simulamos el comportamiento del repositorio con Mockito
        when(agenteRepository.findByRol("Senior")).thenReturn(agentesSimulados);

        // Simular el guardado retornando la misma entidad recibida
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Ticket ticketAsignado = service.crearYAsignarTicket("Fallo crítico en el servidor de base de datos", 5, 4);

        assertNotNull(ticketAsignado.getAgenteAsignado());
        assertEquals("Senior", ticketAsignado.getAgenteAsignado().getRol());

        // Verificamos que se haya guardado el agente con su carga actualizada
        verify(agenteRepository, times(1)).save(any(Agente.class));
    }

    @Test
    public void testAlgoritmoGreedyDeberiaAsignarAlSeniorConMenorCarga() {
        List<Agente> agentesSimulados = Arrays.asList(
                new Agente("Pepe", "Senior", 5),
                new Agente("Juan", "Senior", 1) // Juan tiene menor carga
        );

        when(agenteRepository.findByRol("Senior")).thenReturn(agentesSimulados);
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Ticket ticketAsignado = service.crearYAsignarTicket("Caída masiva del servicio regional", 5, 5);

        assertNotNull(ticketAsignado.getAgenteAsignado());
        assertEquals("Juan", ticketAsignado.getAgenteAsignado().getNombre(), "Debió elegir a Juan por tener menor carga");
    }
}