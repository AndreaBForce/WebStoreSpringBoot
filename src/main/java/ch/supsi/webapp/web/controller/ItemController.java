package ch.supsi.webapp.web.controller;


import ch.supsi.webapp.web.CategoryService;
import ch.supsi.webapp.web.ItemService;
import ch.supsi.webapp.web.RoleService;
import ch.supsi.webapp.web.UserService;
import ch.supsi.webapp.web.model.Item;
import ch.supsi.webapp.web.model.Success;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/API/items")
public class ItemController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private CategoryService categoryService;



    //Post creazione del socio
    //In automatico se è vuoto
    @PostMapping(value = "")
    public ResponseEntity<Item> post(@RequestBody Item item) {
        int ID = (int) itemService.getLen();
        Item aggiungere = new Item(item.getTitle(),item.getDescription(),userService.getById(item.getAuthor().getId()),categoryService.getByName(item.getCategory().getCategory()));
        //Inserisce nel db

        itemService.insertInDb(aggiungere);
        //TODO FIXARE JACKSON CHE NON STAMPA
        //return new ResponseEntity<Item>(aggiungere,HttpStatus.CREATED);
        return new ResponseEntity<Item>(HttpStatus.CREATED);
    }

    //Get tutta la lista
    @GetMapping(value = "")
    public List<Item> get() {
        return itemService.getAllItems();
    }

    //Get con id socio
    //Se non trova gestisce con 404
    @GetMapping(value = "/{id}")
    public ResponseEntity<Item> getOggetto(@PathVariable int id) {
        Item item = itemService.getItemById(id).get();

        //Check se non found
        if (item == null) {
            return new ResponseEntity<Item>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Item>(item, HttpStatus.OK);
        }


    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Item> put(@RequestBody Item letto,@PathVariable int id){
       Item item = itemService.getItemById(id).get();

        //Check se non found
        if (item != null) {
            itemService.modifyById(id,letto);
            return new ResponseEntity<Item>(item, HttpStatus.OK);
        } else {
            return new ResponseEntity<Item>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Success> delete(@PathVariable int id) {

        Success succes = new Success(false);
        //Cerco se non è nullo rimuovo e success
        if(itemService.getItemById(id).isPresent()){
            succes.setSuccess(true);
            itemService.deleteById(id);
            return new ResponseEntity<Success>(succes,HttpStatus.OK);
        }else{
            return new ResponseEntity<Success>(HttpStatus.NOT_FOUND);
        }
    }

}
