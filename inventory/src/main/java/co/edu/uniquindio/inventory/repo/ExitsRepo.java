package co.edu.uniquindio.inventory.repo;

import co.edu.uniquindio.inventory.model.Exits;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExitsRepo extends MongoRepository<Exits, String> {

}
