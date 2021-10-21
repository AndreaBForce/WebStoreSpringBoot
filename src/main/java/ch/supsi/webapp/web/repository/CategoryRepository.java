package ch.supsi.webapp.web.repository;

import ch.supsi.webapp.web.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

    public Category findByCategory(String categoria);
}
