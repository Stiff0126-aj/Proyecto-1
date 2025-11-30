package uniandes.edu.co.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.models.Vehiculo;
import uniandes.edu.co.proyecto.models.Vehiculo.Disponibilidad;
import uniandes.edu.co.proyecto.repositorio.VehiculoRepository;

import java.util.List;

@RestController
@RequestMapping("/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    // ---------------------------------------------------------
    // RF3 - Registrar vehículo
    // ---------------------------------------------------------
    @PostMapping
    public Vehiculo registrarVehiculo(@RequestBody Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    // ---------------------------------------------------------
    // RF4 - Registrar disponibilidad para un vehículo
    // ---------------------------------------------------------
    @PostMapping("/{vehiculoId}/disponibilidades")
    public Vehiculo registrarDisponibilidad(
            @PathVariable String vehiculoId,
            @RequestBody Disponibilidad nuevaDisponibilidad) {

        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId).orElse(null);
        if (vehiculo == null) {
            throw new RuntimeException("Vehículo no encontrado: " + vehiculoId);
        }

        // Validación: evitar superposición de horarios
        List<Disponibilidad> existentes = vehiculo.getDisponibilidades();

        for (Disponibilidad d : existentes) {
            if (d.getDia().equals(nuevaDisponibilidad.getDia())) {
                boolean seSuperpone =
                        nuevaDisponibilidad.getInicio().before(d.getFin()) &&
                        nuevaDisponibilidad.getFin().after(d.getInicio());

                if (seSuperpone) {
                    throw new RuntimeException("El rango horario se superpone con una disponibilidad existente");
                }
            }
        }

        // Si pasa la validación, se agrega
        existentes.add(nuevaDisponibilidad);
        vehiculo.setDisponibilidades(existentes);

        return vehiculoRepository.save(vehiculo);
    }

    // ---------------------------------------------------------
    // RF5 - Modificar disponibilidad
    // ---------------------------------------------------------
    @PutMapping("/{vehiculoId}/disponibilidades/{index}")
    public Vehiculo modificarDisponibilidad(
            @PathVariable String vehiculoId,
            @PathVariable int index,
            @RequestBody Disponibilidad disponibilidadModificada) {

        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId).orElse(null);
        if (vehiculo == null) {
            throw new RuntimeException("Vehículo no encontrado: " + vehiculoId);
        }

        List<Disponibilidad> lista = vehiculo.getDisponibilidades();

        if (index < 0 || index >= lista.size()) {
            throw new RuntimeException("Índice de disponibilidad inválido: " + index);
        }

        // Validar superposición con otras disponibilidades
        for (int i = 0; i < lista.size(); i++) {
            if (i == index) continue; // ignorar la que estamos modificando

            Disponibilidad d = lista.get(i);

            if (d.getDia().equals(disponibilidadModificada.getDia())) {
                boolean seSuperpone =
                        disponibilidadModificada.getInicio().before(d.getFin()) &&
                        disponibilidadModificada.getFin().after(d.getInicio());

                if (seSuperpone) {
                    throw new RuntimeException("No se puede modificar: la disponibilidad se superpone con otra existente.");
                }
            }
        }

        // Si todo está bien, reemplazar
        lista.set(index, disponibilidadModificada);
        vehiculo.setDisponibilidades(lista);

        return vehiculoRepository.save(vehiculo);
    }


}
