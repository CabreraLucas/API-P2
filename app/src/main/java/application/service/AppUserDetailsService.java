package application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import application.model.Login;
import application.repository.LoginRepository;

@Service
public class AppUserDetailsService implements UserDetailsService{

    @Autowired
    private LoginRepository loginRepo;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Login login = loginRepo.findByEmail(username);
        if (login == null){
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
        .username(login.getEmail())
        .password(login.getSenha())
        .roles("USER")
        .build();

    return userDetails;
    }
}