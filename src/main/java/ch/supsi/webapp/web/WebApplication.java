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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
				roleRepository.saveAndFlush(new Role("ROLE_AUTHOR"));
				roleRepository.saveAndFlush(new Role("ROLE_ADMIN"));
				roleRepository.saveAndFlush(new Role("ROLE_TONIO"));
				roleRepository.saveAndFlush(new Role("ROLE_WATCHER"));
			}

			if(userRepository.count() == 0){
				//TODO Rimpiazza con findbyname
				userRepository.saveAndFlush(new User("admin","andrea","riccardi",roleRepository.findById(7).get(),new BCryptPasswordEncoder().encode("admin")));
				userRepository.saveAndFlush(new User("adm_diego","diego","moranda",roleRepository.findById(7).get(),new BCryptPasswordEncoder().encode("admin")));
				/*userRepository.saveAndFlush(new User("Paky",roleRepository.findById(7).get()));
				userRepository.saveAndFlush(new User("Diego",roleRepository.findById(8).get()));
				userRepository.saveAndFlush(new User("Matteo",roleRepository.findById(9).get()));*/
			}
		};
	}
}
