package ch.supsi.webapp.web;

import ch.supsi.webapp.web.model.User;
import ch.supsi.webapp.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User getById(int userId){
        User utente = userRepository.getById(userId);

        if(utente != null){
            return utente;
        }else{
            utente = userRepository.getById(7);
            return utente;
        }
    }
}
