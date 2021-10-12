package ch.supsi.webapp.web;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class ItemController {

    private List<Item> oggetti = new ArrayList<>();

    static int ID = 0;

    //Post creazione del socio
    //In automatico se è vuoto
    @RequestMapping(value = "/items", method = RequestMethod.POST)
    public ResponseEntity<Item> post(@RequestBody Item item) {
        item.setId(ID);

        oggetti.add(item);
        ID++;
        return new ResponseEntity<Item>(item, HttpStatus.CREATED);
    }

    //Get tutta la lista
    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public List<Item> get() {
        return oggetti;
    }

    //Get con id socio
    //Se non trova gestisce con 404
    @RequestMapping(value = "/items/{id}", method = RequestMethod.GET)
    public ResponseEntity<Item> getOggetto(@PathVariable int id) {
        Item item = null;

        for (Item e : oggetti) {
            if (e.getId() == id) {
                item = e;
                break;
            }
        }

        //Check se non found
        if (item == null) {
            return new ResponseEntity<Item>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Item>(item, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/items/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Item> put(@RequestBody Item letto,@PathVariable int id){
        Item item = null;
        for (Item e : oggetti) {
            if (e.getId() == id) {
                e.setTitle(letto.getTitle());
                e.setDescription(letto.getDescription());
                e.setAuthor(letto.getAuthor());
                item = e;
                break;
            }
        }

        //Check se non found
        if (item != null) {
            return new ResponseEntity<Item>(item, HttpStatus.OK);
        } else {
            return new ResponseEntity<Item>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/items/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Success> delete(@PathVariable int id) {

        Success succes = new Success(false);

        for (Item e : oggetti) {
            if (e.getId() == id) {
                oggetti.remove(e);
                succes.setSuccess(true);
                break;
            }
        }

        if(succes.isSuccess()){
            return new ResponseEntity<Success>(succes,HttpStatus.OK);
        }else{
            return new ResponseEntity<Success>(HttpStatus.NOT_FOUND);
        }

    }

}
