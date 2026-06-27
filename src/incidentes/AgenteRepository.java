package incidentes;
import java.util.List;

public interface AgenteRepository {
    void guardar(Agente agente); //inserta en la BD via ORM
    List<Agente> buscarPorRol(String rol); //consulta filtrada de la BD
    Agente buscarPorId(Long id);
}
