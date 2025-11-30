package uniandes.edu.co.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.models.Cliente;
import uniandes.edu.co.proyecto.repositorio.ClienteRepository;


@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    // RF1 - Registrar un usuario de servicios
    @PostMapping
    public Cliente registrarCliente(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }
}
