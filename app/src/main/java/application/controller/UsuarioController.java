package application.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import application.model.Usuario;
import application.repository.LoginRepository;
import application.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private LoginRepository loginRepo;

    @GetMapping
    public Iterable<Usuario> getAll(){
        return usuarioRepo.findAll();
    }

    @PostMapping
    public Usuario post(@RequestBody Usuario usuario){
        if(!loginRepo.existsById(usuario.getLogin().getId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login não encontrado");
        }
        return usuarioRepo.save(usuario);
    }

    @PutMapping("/{id}")
    public Usuario put(@RequestBody Usuario usuario, @PathVariable long id){
        Optional<Usuario> result = usuarioRepo.findById(id);
        if(result.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado");
        }
        if(!loginRepo.existsById(usuario.getLogin().getId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login não encontrado");
        }
        result.get().setNome(usuario.getNome());
        result.get().setNomeExibicao(usuario.getNomeExibicao());
        result.get().setLogin(usuario.getLogin());

        return usuarioRepo.save(result.get());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id){
        if(usuarioRepo.existsById(id)){
            usuarioRepo.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }
    }
}
