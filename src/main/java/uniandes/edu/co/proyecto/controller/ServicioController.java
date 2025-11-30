package uniandes.edu.co.proyecto.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.models.Conductor;
import uniandes.edu.co.proyecto.models.Servicio;
import uniandes.edu.co.proyecto.models.Vehiculo;
import uniandes.edu.co.proyecto.models.Vehiculo.Disponibilidad;
import uniandes.edu.co.proyecto.repositorio.ConductorRepository;
import uniandes.edu.co.proyecto.repositorio.ServicioRepository;
import uniandes.edu.co.proyecto.repositorio.VehiculoRepository;

import java.util.*;

@RestController
@RequestMapping("/servicios")
public class ServicioController {

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private ConductorRepository conductorRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    // ---------------------------------------------------------
    // RF6 - Solicitar un servicio por parte de un usuario
    // ---------------------------------------------------------
    @PostMapping
    public Servicio solicitarServicio(@RequestBody Servicio solicitud) {

        // 1. Calcular distancia aproximada (dummy)
        double distancia = solicitud.getDistanciaKm() == 0
                ? 5.0 : solicitud.getDistanciaKm(); // valor ficticio

        solicitud.setDistanciaKm(distancia);

        // 2. Tarifa según nivel del vehículo (dummy)
        double tarifa = distancia * 2000;
        solicitud.setTarifaFinal(tarifa);

        // 3. Buscar conductor disponible
        String conductorAsignado = buscarConductorDisponible(solicitud.getTipo());
        if (conductorAsignado == null) {
            throw new RuntimeException("No hay conductores disponibles en este momento.");
        }

        solicitud.setConductorAsignadoId(conductorAsignado);

        // 4. Estado del servicio
        solicitud.setEstado("asignado");
        solicitud.setHoraInicio(new Date());

        return servicioRepository.save(solicitud);
    }

    // ---------------------------------------------------------
    // Funciones auxiliares
    // ---------------------------------------------------------
   private String buscarConductorDisponible(String tipoServicio) {

    // ---------------------------------------------------------
    // 1. HORA FIJA PARA PRUEBAS (Lunes 6 de enero 2025 - 10 AM)
    // ---------------------------------------------------------
    Calendar cal = Calendar.getInstance();
    cal.set(2025, Calendar.JANUARY, 6, 10, 0, 0);  // Lunes 10 AM
    cal.set(Calendar.MILLISECOND, 0);

    Date ahora = cal.getTime();
    int diaSemanaNum = cal.get(Calendar.DAY_OF_WEEK);
    String diaSemana = obtenerDia(diaSemanaNum);

    // ---------------------------------------------------------
    // 2. Buscar TODOS los conductores
    // ---------------------------------------------------------
    List<Conductor> todos = conductorRepository.findAll();

    for (Conductor conductor : todos) {

        // ---------------------------------------------------------
        // 3. Vehículos del conductor
        // ---------------------------------------------------------
        List<Vehiculo> vehiculos = vehiculoRepository.findByConductorId(conductor.getId());
        if (vehiculos.isEmpty()) continue;

        Vehiculo vehiculo = vehiculos.get(0);

        // ---------------------------------------------------------
        // 4. Validar tipo de servicio
        // ---------------------------------------------------------
        if (tipoServicio.equals("pasajeros") && !vehiculo.getTipo().equals("carro")) {
            continue;
        }

        // ---------------------------------------------------------
        // 5. Validar disponibilidad por día y hora
        // ---------------------------------------------------------
        for (Disponibilidad disp : vehiculo.getDisponibilidades()) {

            if (disp.getDia().equalsIgnoreCase(diaSemana)) {

                if (ahora.after(disp.getInicio()) && ahora.before(disp.getFin())) {
                    return conductor.getId();   // ⭐ CONDUCTOR DISPONIBLE ⭐
                }
            }
        }
    }

    return null; 
}


    private String obtenerDia(int dia) {
        return switch (dia) {
            case Calendar.MONDAY -> "lunes";
            case Calendar.TUESDAY -> "martes";
            case Calendar.WEDNESDAY -> "miercoles";
            case Calendar.THURSDAY -> "jueves";
            case Calendar.FRIDAY -> "viernes";
            case Calendar.SATURDAY -> "sabado";
            default -> "domingo";
        };
    }
}
