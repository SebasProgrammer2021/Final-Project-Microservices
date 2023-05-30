package co.edu.uniquindio.inventory.repo;

import co.edu.uniquindio.inventory.model.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepo extends MongoRepository<Inventory, String> {
    List<Optional<Inventory>> findAllByEstado(int estado);

    List<Inventory> findAllByCodigo(String codigo);

    List<Inventory> findByEstado(int estado);

    List<Inventory> findAllByEstadoAndCodigo(int estado, String codigo);

    Inventory findFirstByEstadoAndCodigo(int estado, String codigo);

    Optional<Inventory> findTopByEstadoAndCodigoOrderByIdDesc(int estado, String codigo);
}
