package incidentes;

public interface TicketRepository {
    void guardar(Ticket ticket); //insertara el ticket via ORM
}
