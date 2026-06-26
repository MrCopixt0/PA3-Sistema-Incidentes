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
}
