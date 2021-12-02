package ch.supsi.webapp.web.service;

import ch.supsi.webapp.web.model.Role;
import ch.supsi.webapp.web.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public Role findById(int id){
        return roleRepository.getById(id);
    }
}
