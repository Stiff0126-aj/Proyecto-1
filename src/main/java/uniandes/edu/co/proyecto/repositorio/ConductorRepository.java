package uniandes.edu.co.proyecto.repositorio;



import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import uniandes.edu.co.proyecto.models.Conductor;


@Repository
public interface ConductorRepository extends MongoRepository<Conductor, String> {
}
