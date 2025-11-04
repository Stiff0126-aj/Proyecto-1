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
        FROM CONDUCTOR c
        WHERE NOT EXISTS (
            SELECT 1 FROM SERVICIO s
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
    @Query(value = "UPDATE SERVICIO SET HORA_FINALIZACION = :fin, PRECIO = :precio WHERE ID = :id", nativeQuery = true)
    void cerrarServicio(@Param("id") Long id,
                        @Param("fin") java.sql.Timestamp fin,
                        @Param("precio") Double precio);

    @Query(value = "SELECT * FROM SERVICIO", nativeQuery = true)
    Collection<Servicio> darServicios();

    @Query(value = "SELECT * FROM SERVICIO WHERE ID_USUARIO_CLIENTE = :idUsuario", nativeQuery = true)
    Collection<Servicio> darServiciosPorUsuario(@Param("idUsuario") Long idUsuario);

    @Query(value = "SELECT * FROM SERVICIO WHERE ID = :id", nativeQuery = true)
    Servicio darServicioPorId(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM SERVICIO WHERE ID = :id", nativeQuery = true)
    void eliminarServicio(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE SERVICIO SET DISTANCIA_KILOMETROS = :distancia, TARIFA_KILOMETRO = :tarifa, PRECIO = :precio, HORA_INICIO = :inicio, HORA_FINALIZACION = :fin WHERE ID = :id", nativeQuery = true)
    void actualizarServicio(@Param("id") Long id,
                            @Param("distancia") Double distancia,
                            @Param("tarifa") Double tarifa,
                            @Param("precio") Double precio,
                            @Param("inicio") Date inicio,
                            @Param("fin") Date fin);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO SERVICIO (ID, DISTANCIA_KILOMETROS, TARIFA_KILOMETRO, PRECIO, HORA_INICIO, HORA_FINALIZACION) VALUES (:id, :distancia, :tarifa, :precio, :inicio, :fin)", nativeQuery = true)
    void insertarServicio(@Param("id") Long id,
                          @Param("distancia") Double distancia,
                          @Param("tarifa") Double tarifa,
                          @Param("precio") Double precio,
                          @Param("inicio") Date inicio,
                          @Param("fin") Date fin);

    @Query(value = "SELECT TARIFA_KILOMETRO FROM SERVICIO WHERE ID = :id", nativeQuery = true)
    Double obtenerTarifaPorServicio(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE SERVICIO SET HORA_FINALIZACION = :fin, DISTANCIA_KILOMETROS = :distancia, PRECIO = :precio WHERE ID = :id", nativeQuery = true)
    int cerrarServicioRf9(@Param("id") Long id,
                          @Param("fin") java.sql.Timestamp fin,
                          @Param("distancia") Double distancia,
                          @Param("precio") Double precio);
}
