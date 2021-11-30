package ch.supsi.webapp.web.service;

import ch.supsi.webapp.web.model.Category;
import ch.supsi.webapp.web.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }
    public Category getByName(String name){
        Category category = categoryRepository.findByCategory(name);

        if(category != null){
            return category;
        }else{
            return categoryRepository.findByCategory("general");
        }
    }
}
