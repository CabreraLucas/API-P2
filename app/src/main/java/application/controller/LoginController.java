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

import application.model.Login;
import application.repository.LoginRepository;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginRepository loginRepo;
    
    @GetMapping("/{id}")
    public Login getOne(@PathVariable long id){
        Optional<Login> result = loginRepo.findById(id);
        if(result.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Login não encontrado");
        }
        return result.get();
    }
    
    @PostMapping
    private Login post(@RequestBody Login login){
        return loginRepo.save(login);
    }
    
    @GetMapping
    public Iterable<Login> getAll(){
        return loginRepo.findAll();
    }
    
    @PutMapping("/{id}")
    private Login put(@RequestBody Login login, @PathVariable long id){
        Optional<Login> result = loginRepo.findById(id);

        if(result.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Login não encontrado");
        }

        result.get().setEmail(login.getEmail());
        result.get().setSenha(login.getSenha());
        return loginRepo.save(result.get());
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable long id){
        if(loginRepo.existsById(id)){
            loginRepo.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Login não encontrado");
        }
    }
}
