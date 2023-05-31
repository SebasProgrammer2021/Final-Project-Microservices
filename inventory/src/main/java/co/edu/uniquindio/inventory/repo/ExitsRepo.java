package co.edu.uniquindio.inventory.repo;

import co.edu.uniquindio.inventory.model.Entries;
import co.edu.uniquindio.inventory.model.Exits;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExitsRepo extends MongoRepository<Exits, String> {
}
