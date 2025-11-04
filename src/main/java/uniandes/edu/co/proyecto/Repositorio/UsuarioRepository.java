package uniandes.edu.co.proyecto.Repositorio;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import uniandes.edu.co.proyecto.modelo.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "SELECT * FROM USUARIO WHERE CORREO = :correo", nativeQuery = true)
    Usuario buscarPorCorreo(@Param("correo") String correo);

    @Query(value = "SELECT * FROM USUARIO WHERE CEDULA = :cedula", nativeQuery = true)
    Usuario buscarPorCedula(@Param("cedula") String cedula);

    @Query(value = "SELECT * FROM USUARIO", nativeQuery = true)
    Collection<Usuario> darUsuarios();

    @Query(value = "SELECT * FROM USUARIO WHERE ID = :id", nativeQuery = true)
    Usuario darUsuarioPorId(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM USUARIO WHERE ID = :id", nativeQuery = true)
    void eliminarUsuario(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE USUARIO SET NOMBRE = :nombre, CORREO = :correo, TELEFONO = :telefono, CEDULA = :cedula, PROMEDIO_CALIFICACION = :promedio WHERE ID = :id", nativeQuery = true)
    void actualizarUsuario(@Param("id") Long id,
                           @Param("nombre") String nombre,
                           @Param("correo") String correo,
                           @Param("telefono") String telefono,
                           @Param("cedula") String cedula,
                           @Param("promedio") Double promedio);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO USUARIO (ID, NOMBRE, CORREO, TELEFONO, CEDULA, PROMEDIO_CALIFICACION) VALUES (:id, :nombre, :correo, :telefono, :cedula, :promedio)", nativeQuery = true)
    void insertarUsuario(@Param("id") Long id,
                         @Param("nombre") String nombre,
                         @Param("correo") String correo,
                         @Param("telefono") String telefono,
                         @Param("cedula") String cedula,
                         @Param("promedio") Double promedio);

    @Query(value = "SELECT NVL(MAX(ID),0)+1 FROM USUARIO", nativeQuery = true)
    Long siguienteIdUsuario();
}
