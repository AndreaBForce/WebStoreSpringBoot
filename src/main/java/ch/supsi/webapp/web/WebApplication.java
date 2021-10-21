package ch.supsi.webapp.web;

import ch.supsi.webapp.web.model.Category;
import ch.supsi.webapp.web.model.Role;
import ch.supsi.webapp.web.model.User;
import ch.supsi.webapp.web.repository.CategoryRepository;
import ch.supsi.webapp.web.repository.ItemRepository;
import ch.supsi.webapp.web.repository.RoleRepository;
import ch.supsi.webapp.web.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

	@Bean
	public CommandLineRunner initDatabase(CategoryRepository categoryRepository, RoleRepository roleRepository, UserRepository userRepository) {
		return (args) -> {

			if(categoryRepository.count() == 0) {
				categoryRepository.saveAndFlush(new Category("Sport"));
				categoryRepository.saveAndFlush(new Category("Home"));
				categoryRepository.saveAndFlush(new Category("Garden"));
				categoryRepository.saveAndFlush(new Category("Cooking"));
				categoryRepository.saveAndFlush(new Category("General"));
			}

			if(roleRepository.count() == 0){
				roleRepository.saveAndFlush(new Role("Author"));
				roleRepository.saveAndFlush(new Role("Administrator"));
				roleRepository.saveAndFlush(new Role("User"));
				roleRepository.saveAndFlush(new Role("Editor"));
			}

			if(userRepository.count() == 0){
				//TODO Rimpiazza con findbyname
				userRepository.saveAndFlush(new User("AUTH_Antonio",roleRepository.findById(6).get()));
				userRepository.saveAndFlush(new User("ADM_Max",roleRepository.findById(7).get()));
				userRepository.saveAndFlush(new User("USR_Diego",roleRepository.findById(8).get()));
				userRepository.saveAndFlush(new User("EDIT_Matteo",roleRepository.findById(9).get()));
			}
		};
	}
}
