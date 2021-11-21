package ch.supsi.webapp.web.controller;

import ch.supsi.webapp.web.CategoryService;
import ch.supsi.webapp.web.ItemService;
import ch.supsi.webapp.web.RoleService;
import ch.supsi.webapp.web.UserService;
import ch.supsi.webapp.web.model.Item;
import ch.supsi.webapp.web.model.Success;
import ch.supsi.webapp.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class AnnController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getAll(Model model) {
        //TODO PRENDERE LA CATEGORIA E DIVIDERE IN OFFERTA E DOMANDA
        model.addAttribute("annunci", itemService.getAllItems());
        return "index";
    }

    @RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
    public String getItem(Model model, @PathVariable int id) {
        Item item = itemService.getItemById(id).get();
        model.addAttribute("oggetto", item);
        return "itemDetails";
    }

    @RequestMapping(value = "/item/new", method = RequestMethod.GET)
    public String getNew(Model model) {
        model.addAttribute("utenti",userService.getAllUsers());
        model.addAttribute("categorie",categoryService.getAllCategory());

        return "createItemForm";
    }

    @RequestMapping(value = "/item/new", method = RequestMethod.POST)
    public String postNew(Model model) {
        return "redirect:/index";
    }

    @RequestMapping(value = "/item/{id}/edit", method = RequestMethod.GET)
    public String getEditItem(Model model, @PathVariable int id) {
        model.addAttribute("utenti",userService.getAllUsers());
        model.addAttribute("categorie",categoryService.getAllCategory());


        if(itemService.getItemById(id).isPresent()) {
            model.addAttribute("oggetto", itemService.getItemById(id).get());
        }else{
            return "redirect:/";
        }

        return "editItemForm";
    }

    @RequestMapping(value = "/item/{id}/edit", method = RequestMethod.POST)
    public String postEditItem(@PathVariable int id,
                               @RequestParam("author") String author,
                               @RequestParam("title") String title,
                               @RequestParam("category") String categoria,
                               @RequestParam("annuncio") String annuncio,
                               @RequestParam("date") String date,
                               @RequestParam("description") String description,
                               Model model) {
        Date date1= null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Per come lo avevo fatto deleteva l'oggetto e poi lo rimetteva dentro con un altro id
        Item item = new Item(title,description,userService.getByName(author), categoryService.getByName(categoria),date1,annuncio,id);
        itemService.modifyById(id,item);

        return "redirect:/";
    }

    @RequestMapping(value = "/item/{id}/delete", method = RequestMethod.GET)
    public String deleteEditItem(@PathVariable int id,Model model) {

        //Cerco se non è nullo rimuovo e success
        if (itemService.getItemById(id).isPresent()) {
            itemService.deleteById(id);
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/itemDetails.html", method = RequestMethod.GET)
    public String itemsDetail(Model model) {
        return "itemDetails";
    }

    @RequestMapping(value = "/createItemForm.html", method = RequestMethod.GET)
    public String createItem(Model model) {

        return getNew(model);
    }

    @RequestMapping(value = "/createItemForm.html", method = RequestMethod.POST)
    public String postItem(@RequestParam("author") String author,
                           @RequestParam("title") String title,
                           @RequestParam("category") String categoria,
                           @RequestParam("annuncio") String annuncio,
                           @RequestParam("date") String date,
                           @RequestParam("description") String description,
                            Model model) {
        int ID = (int) itemService.getLen();

        Date date1= null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Item item = new Item(title,description,userService.getByName(author), categoryService.getByName(categoria),date1,annuncio,ID);
        itemService.insertInDb(item);

        return "redirect:/";
    }

    @RequestMapping(value = "/index.html", method = RequestMethod.GET)
    public String home(Model model) {
        return "redirect:/";
    }




}
