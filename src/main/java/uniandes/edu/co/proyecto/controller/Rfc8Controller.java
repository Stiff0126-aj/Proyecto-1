package uniandes.edu.co.proyecto.controller;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uniandes.edu.co.proyecto.Repositorio.ServicioRepository;

@RestController
@RequestMapping("/rfc8")
public class Rfc8Controller {
    
    private final ServicioRepository servicioRepository;

    public Rfc8Controller(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }


    @GetMapping("/ejecutar")
    @Transactional
        public String ejecutarRF8(@RequestParam Long idServicio) {

        
        Long idConductor = servicioRepository.asignarConductor();
        if (idConductor == null) {
            throw new RuntimeException("No hay conductores disponibles.");
        }

       
        servicioRepository.insertarServicio(
                idServicio,
                10.0,
                2000.0,
                0.0,
                new Date(System.currentTimeMillis() - 600000),
                new Date(System.currentTimeMillis()),
                (int)1L,       // SALIDA (debe existir en la tabla de SALIDAS o CIUDADES)
                (int)8000L,     // ID_USUARIO_CLIENTE existente
                (int)8000L,     // ID_TARJETA_CLIENTE existente
                idConductor
        );

        servicioRepository.actualizarServicio(
                idServicio,
                12.5,                 // nueva distancia
                2000.0,               // misma tarifa
                25000.0,              // precio parcial
                new Date(System.currentTimeMillis() - 600000), // hora inicio anterior
                new Date()            // hora finalización
        );

        servicioRepository.cerrarServicio(
                idServicio,
                new Timestamp(System.currentTimeMillis()),
                30000.0               // precio final
        );

        return "✅ Transacción RF8 completada exitosamente.";
    }

}
