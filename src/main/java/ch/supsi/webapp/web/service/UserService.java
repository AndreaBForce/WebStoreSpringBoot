package ch.supsi.webapp.web.service;

import ch.supsi.webapp.web.model.Item;
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

    public void flushUser(User user){
        userRepository.saveAndFlush(user);
    }


    public User findUserByUsername(String username){
        return userRepository.findByUsername(username);
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

    public List<Item> findAllPreferiti(String username){
        return findUserByUsername(username).getConfrontabili();
    }
}
