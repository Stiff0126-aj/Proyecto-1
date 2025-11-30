package uniandes.edu.co.proyecto.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import uniandes.edu.co.proyecto.models.Servicio;


@Repository
public interface ServicioRepository extends MongoRepository<Servicio, String> {
}