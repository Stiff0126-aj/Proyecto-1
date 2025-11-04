package uniandes.edu.co.proyecto.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.proyecto.Repositorio.UsuarioRepository;
import uniandes.edu.co.proyecto.modelo.Usuario;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/registrar")
    @Transactional
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        if (usuario == null || usuario.getNombre() == null || usuario.getCorreo() == null ||
            usuario.getTelefono() == null || usuario.getCedula() == null) {
            return new ResponseEntity<>("Faltan campos obligatorios", HttpStatus.BAD_REQUEST);
        }

        Long id = usuarioRepository.siguienteIdUsuario();
        usuarioRepository.insertarUsuario(id, usuario.getNombre(), usuario.getCorreo(),
                usuario.getTelefono(), usuario.getCedula(), null);

        return new ResponseEntity<>("Usuario registrado con ID=" + id, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Usuario getUsuario(@PathVariable Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }
}
