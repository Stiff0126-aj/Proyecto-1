package uniandes.edu.co.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uniandes.edu.co.proyecto.models.Servicio;
import uniandes.edu.co.proyecto.repositorio.ServicioRepository;

@RestController
@RequestMapping("/servicios")
public class ServicioFinalizarController {

    @Autowired
    private ServicioRepository servicioRepository;

    // ---------------------------------------------------------
    // RF7 - Finalizar Servicio
    // ---------------------------------------------------------
    @PutMapping("/{servicioId}/finalizar")
    public Servicio finalizarServicio(
            @PathVariable String servicioId,
            @RequestBody Servicio datosFinalizacion) {

        Servicio servicio = servicioRepository.findById(servicioId)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));

        // Solo se puede finalizar si está asignado
        if (!servicio.getEstado().equals("asignado")) {
            throw new RuntimeException("El servicio debe estar en estado ASIGNADO para finalizar.");
        }

        // Actualizar SOLO los campos del viaje que existen en tu modelo
        servicio.setHoraInicio(datosFinalizacion.getHoraInicio());
        servicio.setHoraFin(datosFinalizacion.getHoraFin());
        servicio.setDistanciaKm(datosFinalizacion.getDistanciaKm());
        servicio.setTarifaFinal(datosFinalizacion.getTarifaFinal());

        servicio.setEstado("completado");

        return servicioRepository.save(servicio);
    }
}
