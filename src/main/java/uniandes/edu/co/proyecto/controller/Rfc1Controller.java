package uniandes.edu.co.proyecto.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uniandes.edu.co.proyecto.Repositorio.ServicioRepository;
import uniandes.edu.co.proyecto.modelo.Servicio;

@RestController
@RequestMapping("/rfc1")
public class Rfc1Controller {

    private final ServicioRepository servicioRepository;

    public Rfc1Controller(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    //SERIALIZABLE
    @GetMapping("/serializable/{idUsuario}")
    @Transactional(isolation = Isolation.SERIALIZABLE, timeout = 60)
    public Map<String, Collection<Servicio>> rfc1Serializable(@PathVariable Long idUsuario) {
        Map<String, Collection<Servicio>> result = new HashMap<>();

        // Primera lectura antes del temporizador
        Collection<Servicio> before = servicioRepository.darServiciosPorUsuario(idUsuario);
        result.put("before", before);

        // Temporizador de 30s para observar concurrencia
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Segunda lectura después del temporizador
        Collection<Servicio> after = servicioRepository.darServiciosPorUsuario(idUsuario);
        result.put("after", after);

        return result;
    }

    //READ COMMITTED
    @GetMapping("/read_committed/{idUsuario}")
    @Transactional(isolation = Isolation.READ_COMMITTED, timeout = 60)
    public Map<String, Collection<Servicio>> rfc1ReadCommitted(@PathVariable Long idUsuario) {
        Map<String, Collection<Servicio>> result = new HashMap<>();

        // Primera lectura antes del temporizador
        Collection<Servicio> before = servicioRepository.darServiciosPorUsuario(idUsuario);
        result.put("before", before);

        // Temporizador de 30s para observar concurrencia
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Segunda lectura después del temporizador
        Collection<Servicio> after = servicioRepository.darServiciosPorUsuario(idUsuario);
        result.put("after", after);

        return result;
    }
}
