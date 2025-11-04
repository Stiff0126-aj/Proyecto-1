package uniandes.edu.co.proyecto.Repositorio;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.modelo.Servicio;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    // ----------------------------------------------------------
    // RF8 - Repositorio de operaciones sobre la entidad SERVICIO
    // ----------------------------------------------------------

    // 1️⃣ Buscar un conductor libre al azar
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

    // 2️⃣ Cerrar un servicio (terminar viaje)
    @Modifying
    @Transactional
    @Query(value = """
        UPDATE Servicio 
        SET hora_finalizacion = :fin, 
            precio = :precio 
        WHERE id = :id
    """, nativeQuery = true)
    void cerrarServicio(
        @Param("id") Long id,
        @Param("fin") Timestamp fin,
        @Param("precio") Double precio
    );

    // 3️⃣ Consultar todos los servicios
    @Query(value = "SELECT * FROM Servicio", nativeQuery = true)
    Collection<Servicio> darServicios();

    // 4️⃣ Consultar servicios por cliente
    @Query(value = "SELECT * FROM Servicio WHERE id_usuario_cliente = :idUsuario", nativeQuery = true)
    Collection<Servicio> darServiciosPorUsuario(@Param("idUsuario") Long idUsuario);

    // 5️⃣ Consultar servicio por ID
    @Query(value = "SELECT * FROM Servicio WHERE id = :id", nativeQuery = true)
    Servicio darServicioPorId(@Param("id") Long id);

    // 6️⃣ Eliminar un servicio
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM SERVICIO WHERE ID = :id", nativeQuery = true)
    void eliminarServicio(@Param("id") Long id);

    // 7️⃣ Actualizar los datos de un servicio
    @Modifying
    @Transactional
    @Query(value = """
        UPDATE Servicio 
        SET distancia_kilometros = :distancia, 
            tarifa_kilometro = :tarifa, 
            precio = :precio, 
            hora_inicio = :inicio, 
            hora_finalizacion = :fin
        WHERE id = :id
    """, nativeQuery = true)
    void actualizarServicio(
        @Param("id") Long id,
        @Param("distancia") Double distancia,
        @Param("tarifa") Double tarifa,
        @Param("precio") Double precio,
        @Param("inicio") Date inicio,
        @Param("fin") Date fin
    );

    // 8️⃣ Insertar un nuevo servicio (RF8)
    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO SERVICIO (
            ID, 
            DISTANCIA_KILOMETROS, 
            TARIFA_KILOMETRO, 
            PRECIO, 
            HORA_INICIO, 
            HORA_FINALIZACION, 
            SALIDA,
            ID_USUARIO_CLIENTE, 
            ID_TARJETA_CLIENTE, 
            ID_USUARIO_CONDUCTOR
        )
        VALUES (
            :id, 
            :distancia, 
            :tarifa, 
            :precio, 
            :inicio, 
            :fin, 
            :salida, 
            :idUsuarioCliente, 
            :idTarjetaCliente, 
            :idUsuarioConductor
        )
    """, nativeQuery = true)
    void insertarServicio(
        @Param("id") Long id,
        @Param("distancia") Double distancia,
        @Param("tarifa") Double tarifa,
        @Param("precio") Double precio,
        @Param("inicio") Date inicio,
        @Param("fin") Date fin,
        @Param("salida") int salida,
        @Param("idUsuarioCliente") int idUsuarioCliente,
        @Param("idTarjetaCliente") int idTarjetaCliente,
        @Param("idUsuarioConductor") Long idUsuarioConductor
    );
}
