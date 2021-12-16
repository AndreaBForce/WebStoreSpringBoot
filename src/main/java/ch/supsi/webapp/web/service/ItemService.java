package ch.supsi.webapp.web.service;


import ch.supsi.webapp.web.model.Item;
import ch.supsi.webapp.web.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository repository;

    //Richiesta post
    public void insertInDb(Item item){
        repository.saveAndFlush(item);
    }

    //Richiesta get /
    public List<Item> getAllItems(){
        return repository.findAll();
    }

    public List<Item> getByAnnuncioType(String annuncio){
        return repository.findAllByAnnuncioEquals(annuncio);
    }

    //Richiesta get /id
    public Optional<Item> getItemById(int id){
        return repository.findById(id);
    }

    //Put socio
    public void modifyById(int id,Item letto){
        Item item = repository.getById(id);

        item = letto;

        repository.deleteById(id);
        repository.save(item);
    }

    //Richiesta delete /id
    public void deleteById(int id){
        repository.deleteById(id);
    }

    //ritorna la len della repository
    public long getLen(){
        return repository.count();
    }

    public List<Item> searchKeywordInNameAndDesc(String keyword){
        List<Item> items = repository.findAll();
        List<Item> ritorni = new ArrayList<>();

        if(items.isEmpty()){
            return null;
        }else{
            for (Item i:items) {
                if(i.getDescription().contains(keyword) || i.getTitle().contains(keyword) || i.getCategory().getCategory().contains(keyword)){
                    ritorni.add(i);
                }
            }
        }
        return ritorni;
    }
}
