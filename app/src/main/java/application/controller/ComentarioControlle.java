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

import application.model.Comentario;
import application.repository.ComentarioRepository;
import application.repository.UsuarioRepository;

@RestController
@RequestMapping("/comentarios")
public class ComentarioControlle {
    @Autowired
    private ComentarioRepository comentarioRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @GetMapping
    public Iterable<Comentario> getAll(){
        return comentarioRepo.findAll();
    }

    @GetMapping("/{id}")
    public Comentario getOne(@PathVariable long id){
        Optional<Comentario> result = comentarioRepo.findById(id);
        if(result.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comentario não encontrado");
        }
        return result.get();
    }

    @PostMapping
    public Comentario post(@RequestBody Comentario comentario){
        if(!usuarioRepo.existsById(comentario.getUsuario().getId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario não encontrado");
        }
        return comentarioRepo.save(comentario);
    }

    
    @PutMapping("/{id}")
    public Comentario put(@RequestBody Comentario comentario, @PathVariable long id){
        Optional<Comentario> result = comentarioRepo.findById(id);
        if(result.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comentario não encontrado");
        }
        if(!usuarioRepo.existsById(comentario.getUsuario().getId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Comentario não encontrado");
        }
        result.get().setTitulo(comentario.getTitulo());
        result.get().setTexto(comentario.getTexto());
        result.get().setUsuario(comentario.getUsuario());

        return comentarioRepo.save(result.get());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id){
        if(comentarioRepo.existsById(id)){
            comentarioRepo.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comentario não encontrado");
        }
    }
}
