package uniandes.edu.co.proyecto.Repositorio;


import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.modelo.Servicio;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    // Buscar un conductor libre al azar
    @Query(value = """
        SELECT c.id_usuario
        FROM Conductor c
        WHERE NOT EXISTS (
            SELECT 1 FROM Servicio s
            WHERE s.id_usuario_conductor = c.id_usuario
            AND s.hora_finalizacion IS NULL
        )
        ORDER BY DBMS_RANDOM.VALUE
        FETCH FIRST 1 ROWS ONLY
    """, nativeQuery = true)
    Long asignarConductor();

    // Cerrar un servicio (terminar viaje)
    @Modifying
    @Transactional
    @Query(value = "UPDATE Servicio SET hora_finalizacion = :fin, precio = :precio WHERE id = :id", nativeQuery = true)
    void cerrarServicio(@Param("id") Long id,
                        @Param("fin") java.sql.Timestamp fin,
                        @Param("precio") Double precio);


    @Query(value = "SELECT * FROM Servicio", nativeQuery = true)
    Collection<Servicio> darServicios();

    @Query(value = "SELECT * FROM Servicio WHERE id_usuario = :idUsuario", nativeQuery = true)
    Collection<Servicio> darServiciosPorUsuario(@Param("id_usuario_cliente") Long idUsuario);

    @Query(value = "SELECT * FROM Servicio WHERE id = :id", nativeQuery = true)
    Servicio darServicioPorId(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Servicio WHERE id = :id", nativeQuery = true)
    void eliminarServicio(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Servicio " +
                   "SET distancia_kilometros = :distancia, " +
                   "    tarifa_kilometro = :tarifa, " +
                   "    precio = :precio, " +
                   "    hora_inicio = :inicio, " +
                   "    hora_finalizacion = :fin " +
                   "WHERE id = :id", nativeQuery = true)
    void actualizarServicio(@Param("id") Long id,
                            @Param("distancia") Double distancia,
                            @Param("tarifa") Double tarifa,
                            @Param("precio") Double precio,
                            @Param("inicio") Date inicio,
                            @Param("fin") Date fin);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Servicio (id, distancia_kilometros, tarifa_kilometro, precio, hora_inicio, hora_finalizacion) " +
                   "VALUES (:id, :distancia, :tarifa, :precio, :inicio, :fin)", nativeQuery = true)
    void insertarServicio(@Param("id") Long id,
                          @Param("distancia") Double distancia,
                          @Param("tarifa") Double tarifa,
                          @Param("precio") Double precio,
                          @Param("inicio") Date inicio,
                          @Param("fin") Date fin);
}
