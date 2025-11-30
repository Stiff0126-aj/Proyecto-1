package uniandes.edu.co.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.models.Conductor;
import uniandes.edu.co.proyecto.repositorio.ConductorRepository;

@RestController
@RequestMapping("/conductores")
public class ConductorController {

    @Autowired
    private ConductorRepository conductorRepository;

    // RF2 - Registrar un usuario conductor
    @PostMapping
    public Conductor registrarConductor(@RequestBody Conductor conductor) {
        return conductorRepository.save(conductor);
    }
}