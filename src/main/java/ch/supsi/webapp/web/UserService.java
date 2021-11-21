package ch.supsi.webapp.web;

import ch.supsi.webapp.web.model.User;
import ch.supsi.webapp.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    //TODO USARE QUERY DEL DATABASE DIRETTAMENTE
    public User getByName(String name){
        switch (name){
            case "AUTH_Antonio":
                return userRepository.getById(10);
            case "ADM_Max":
                return userRepository.getById(11);
            case "USR_Diego":
                return userRepository.getById(12);
            case "EDIT_Matteo":
                return userRepository.getById(13);
            default:
                return userRepository.getById(10);
        }
    }

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
