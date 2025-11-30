package uniandes.edu.co.proyecto.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import uniandes.edu.co.proyecto.models.Cliente;


@Repository
public interface ClienteRepository extends MongoRepository<Cliente, String> {
}