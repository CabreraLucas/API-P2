package application.repository;

import org.springframework.data.repository.CrudRepository;

import application.model.Login;

public interface LoginRepository extends CrudRepository<Login, Long> {
    public Login findByEmail(String email);

    public boolean existsById(int id);
}
