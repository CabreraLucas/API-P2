package application.repository;

import org.springframework.data.repository.CrudRepository;

import application.model.Comentario;

public interface ComentarioRepository extends CrudRepository<Comentario, Long> {
    
}
