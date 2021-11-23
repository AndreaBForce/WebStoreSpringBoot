package ch.supsi.webapp.web.repository;

import ch.supsi.webapp.web.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item,Integer> {

    List<Item> findAllByAnnuncioEquals(String annuncio);

}
