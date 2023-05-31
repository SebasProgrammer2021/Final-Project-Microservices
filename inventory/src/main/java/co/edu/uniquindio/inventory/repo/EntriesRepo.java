package co.edu.uniquindio.inventory.repo;

import co.edu.uniquindio.inventory.model.Entries;
import co.edu.uniquindio.inventory.model.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntriesRepo extends MongoRepository<Entries, String> {
    List<Entries> findByEstado(int estado);

    Optional<Entries> findTopByEstadoAndCodigoOrderByIdDesc(int estado, String codigo);
}
