package uniandes.edu.co.proyecto.repositorio;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import uniandes.edu.co.proyecto.models.Vehiculo;

@Repository
public interface VehiculoRepository extends MongoRepository<Vehiculo, String> {
      List<Vehiculo> findByConductorId(String conductorId);
}
