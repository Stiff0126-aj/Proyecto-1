package uniandes.edu.co.proyecto.repositorio;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import uniandes.edu.co.proyecto.models.Servicio;
import uniandes.edu.co.proyecto.models.ConductorServiciosCount;
import uniandes.edu.co.proyecto.models.TipoServicioStats;

@Repository
public interface ServicioRepository extends MongoRepository<Servicio, String> {

    // RFC1: histórico por usuario
    List<Servicio> findByUsuarioId(String usuarioId);

    // RFC2: top 20 conductores con más servicios
    @Aggregation(pipeline = {
    "{ '$match': { 'conductorAsignadoId': { $ne: null } } }",
    "{ '$group': { '_id': '$conductorAsignadoId', 'totalServicios': { '$sum': 1 } } }",
    "{ '$lookup': { 'from': 'conductores', 'localField': '_id', 'foreignField': '_id', 'as': 'conductor' } }",
    "{ '$unwind': '$conductor' }",
    "{ '$sort': { 'totalServicios': -1 } }",
    "{ '$limit': 20 }"
    })
    List<ConductorServiciosCount> obtenerTop20Conductores();

    // RFC3: estadísticas de uso por ciudad y fechas
    @Aggregation(pipeline = {
    "{ '$match': { 'puntoPartida.ciudad': ?0, 'horaInicio': { $gte: ?1, $lte: ?2 } } }",
    "{ '$group': { '_id': '$tipo', 'totalServicios': { '$sum': 1 } } }",
    "{ '$group': { '_id': null, 'total': { '$sum': '$totalServicios' }, 'detalles': { '$push': { 'tipo': '$_id', 'totalServicios': '$totalServicios' } } } }",
    "{ '$unwind': '$detalles' }",
    "{ '$project': { 'tipo': '$detalles.tipo', 'totalServicios': '$detalles.totalServicios', 'porcentaje': { '$multiply': [ { '$divide': ['$detalles.totalServicios', '$total'] }, 100 ] } } }",
    "{ '$sort': { 'totalServicios': -1 } }"
    })
    List<TipoServicioStats> obtenerUsoServiciosPorCiudadYFechas(String ciudad, Date fechaInicio, Date fechaFin);

}
