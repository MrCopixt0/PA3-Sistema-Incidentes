package incidentes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AgenteRepository extends JpaRepository<Agente, Long> {
    // Spring Data genera automáticamente la consulta SQL a partir del nombre del método
    List<Agente> findByRol(String rol);
}