package uniandes.edu.co.proyecto.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.Repositorio.ServicioRepository;
import uniandes.edu.co.proyecto.modelo.Servicio;

@RestController
@RequestMapping("/servicios")
public class ServicioController {

    private final ServicioRepository servicioRepository;

    public ServicioController(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    // RF8: Solicitar servicio
    @PostMapping
    public Servicio solicitarServicio(@RequestBody Servicio servicio) {
        Long conductorId = servicioRepository.asignarConductor();
        // Asignar conductor aquí (ejemplo simplificado)
        servicioRepository.save(servicio);
        return servicio;
    }

    // RF9: Finalizar servicio
    @PutMapping("/{id}/finalizar")
    public ResponseEntity<?> finalizarServicio(@PathVariable Long id,
                                               @RequestParam Double distanciaKm,
                                               @RequestParam(required = false) Double precioFinal) {
        java.sql.Timestamp fin = new java.sql.Timestamp(System.currentTimeMillis());
        Double tarifa = servicioRepository.obtenerTarifaPorServicio(id);
        if (tarifa == null) {
            return new ResponseEntity<>("Servicio " + id + " no existe", HttpStatus.NOT_FOUND);
        }
        double precio = (precioFinal == null) ? (tarifa * distanciaKm) : precioFinal;
        int rows = servicioRepository.cerrarServicioRf9(id, fin, distanciaKm, precio);
        if (rows == 0) {
            return new ResponseEntity<>("No se pudo actualizar el servicio " + id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Servicio " + id + " finalizado. Distancia=" + distanciaKm + " km, Precio=" + precio, HttpStatus.OK);
    }
}
