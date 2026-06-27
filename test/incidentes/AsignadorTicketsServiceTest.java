package incidentes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AsignadorTicketsServiceTest {

    @Test
    public void testCalcularPrioridadMaximaDeberiaDarCinco() {
        //se instancia la clase a probar :v
        AsignadorTicketsService service = new AsignadorTicketsService();
        //se ejecuta el metodo con impacto y gravedad
        double resultado = service.calcularPrioridad(5, 5);
        //afirmamos el resultado y añadimos un tercer parametro como margen de error permitido para decimales.
        assertEquals(5.0, resultado, 0.001);

    }

    @Test
    public void testImpactoFueraDeRangoDeberiaLanzarExcepcion() {
        AsignadorTicketsService service = new AsignadorTicketsService();

        //verificamos que un impacto de 6 (inválido) lance un argumento "IllegalArgumentException"
        assertThrows(IllegalArgumentException.class, () -> {
            service.calcularPrioridad(6, 3);
        });
    }

    @Test
    public void testGravedadFueraDeRangoDeberiaLanzarExcepcion() {
        AsignadorTicketsService service = new AsignadorTicketsService();

        //verificamos que una gravedad de 0 (inválida) lance "IllegalArgumentException"
        assertThrows(IllegalArgumentException.class, () -> {
            service.calcularPrioridad(4, 0);
        });
    }

    @Test
    public void testTicketAltaPrioridadDeberiaAsignarseAAgenteSenior() {
        AsignadorTicketsService service = new AsignadorTicketsService();

        //creamos una lista de agentes disponibles.
        java.util.List<Agente> agentes = java.util.Arrays.asList(
        new Agente("Pepe", "Senior", 0),
        new Agente("Lucho", "Junior", 0)
        );

        double prioridad = service.calcularPrioridad(5, 4);
        Agente agenteAsignado = service.asignarAgente(prioridad, agentes);

        assertNotNull(agenteAsignado, "Se debería haber asignado un agente");
        assertEquals("Senior", agenteAsignado.getRol(), "Los tickets con prioridad >= 4.0 deben ir a un Senior");
    }

    @Test
    public void testAlgoritmoGreedyDeberiaAsignarAlSeniorConMenorCarga() {

        AsignadorTicketsService service = new AsignadorTicketsService();

        java.util.List<Agente> agentes = java.util.Arrays.asList(
                new Agente("Pepe", "Senior", 5),
                new Agente("Juan", "Senior", 0)
        );

        double prioridad = service.calcularPrioridad(5, 5); //senior requerido
        Agente agenteAsignado = service.asignarAgente(prioridad, agentes);

        assertNotNull(agenteAsignado);
        assertEquals("Juan", agenteAsignado.getNombre(), "Debes elegir a Juan por tener menor carga");

    }
}
