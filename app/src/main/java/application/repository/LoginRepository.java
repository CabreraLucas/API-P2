package application.repository;

import org.springframework.data.repository.CrudRepository;

import application.model.Login;

public interface LoginRepository extends CrudRepository<Login, Long> {

    boolean existsById(int id);
    
}
