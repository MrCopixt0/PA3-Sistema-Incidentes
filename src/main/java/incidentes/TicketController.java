package incidentes;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TicketController {

    private final AsignadorTicketsService asignadorTicketsService;
    private final AgenteRepository agenteRepository;

    public TicketController(AsignadorTicketsService asignadorTicketsService, AgenteRepository agenteRepository) {
        this.asignadorTicketsService = asignadorTicketsService;
        this.agenteRepository = agenteRepository;
    }

    // Endpoint para registrar y asignar un ticket
    @PostMapping("/tickets")
    public ResponseEntity<Ticket> crearYAsignarTicket(@RequestBody TicketRequestDTO request) {
        try {
            Ticket ticketAsignado = asignadorTicketsService.crearYAsignarTicket(
                    request.getDescripcion(),
                    request.getImpacto(),
                    request.getGravedad()
            );
            return ResponseEntity.ok(ticketAsignado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Endpoint auxiliar para registrar agentes rápidamente para las pruebas
    @PostMapping("/agentes")
    public ResponseEntity<Agente> crearAgente(@RequestBody Agente agente) {
        Agente nuevoAgente = agenteRepository.save(agente);
        return ResponseEntity.ok(nuevoAgente);
    }

    // Endpoint para listar todos los agentes y ver su carga de trabajo actual
    @GetMapping("/agentes")
    public ResponseEntity<List<Agente>> listarAgentes() {
        return ResponseEntity.ok(agenteRepository.findAll());
    }
}