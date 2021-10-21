package ch.supsi.webapp.web;

import ch.supsi.webapp.web.model.Category;
import ch.supsi.webapp.web.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public Category getByName(String name){
        Category category = categoryRepository.findByCategory(name);

        if(category != null){
            return category;
        }else{
            return categoryRepository.findByCategory("general");
        }
    }
}
