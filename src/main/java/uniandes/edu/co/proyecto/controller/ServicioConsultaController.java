package uniandes.edu.co.proyecto.controller;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.models.*;
import uniandes.edu.co.proyecto.repositorio.ConductorRepository;
import uniandes.edu.co.proyecto.repositorio.ServicioRepository;

@RestController
@RequestMapping("/servicios")
public class ServicioConsultaController {

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private ConductorRepository conductorRepository;

    // ---------------------------------------------------------
    // RFC1 - Histórico de servicios por usuario
    // GET /servicios/usuario/{usuarioId}/historial
    // ---------------------------------------------------------
    @GetMapping("/usuario/{usuarioId}/historial")
    public List<Servicio> obtenerHistorialUsuario(@PathVariable String usuarioId) {
        return servicioRepository.findByUsuarioId(usuarioId);
    }

    // ---------------------------------------------------------
    // RFC2 - Top 20 conductores con más servicios
    // GET /servicios/top-conductores
    // ---------------------------------------------------------
    @GetMapping("/top-conductores")
    public List<Map<String, Object>> obtenerTopConductores() {

        List<ConductorServiciosCount> conteos = servicioRepository.obtenerTop20Conductores();

        List<Map<String, Object>> respuesta = new ArrayList<>();

        for (ConductorServiciosCount c : conteos) {
            Map<String, Object> item = new HashMap<>();
            item.put("conductorId", c.getConductorId());
            item.put("totalServicios", c.getTotalServicios());

            conductorRepository.findById(c.getConductorId()).ifPresent(conductor -> {
                item.put("nombre", conductor.getNombre());
                item.put("correo", conductor.getCorreo());
                item.put("telefono", conductor.getTelefono());
            });

            respuesta.add(item);
        }

        return respuesta;
    }

    // ---------------------------------------------------------
    // RFC3 - Estadísticas por ciudad y rango de fechas
    // GET /servicios/estadisticas?ciudad=&fechaInicio=&fechaFin=
    // ---------------------------------------------------------
    @GetMapping("/estadisticas")
    public List<Map<String, Object>> obtenerEstadisticasPorCiudadYFechas(
            @RequestParam String ciudad,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date fechaFin) {

        List<TipoServicioStats> stats =
                servicioRepository.obtenerUsoServiciosPorCiudadYFechas(ciudad, fechaInicio, fechaFin);

        long total = stats.stream()
                .mapToLong(TipoServicioStats::getTotalServicios)
                .sum();

        return stats.stream().map(s -> {
            Map<String, Object> item = new HashMap<>();
            item.put("tipo", s.getTipo());
            item.put("totalServicios", s.getTotalServicios());
            double porcentaje = (total == 0) ? 0.0 :
                    (s.getTotalServicios() * 100.0 / total);
            item.put("porcentaje", porcentaje);
            return item;
        }).collect(Collectors.toList());
    }
}
