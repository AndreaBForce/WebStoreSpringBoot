package ch.supsi.webapp.web.controller;

import ch.supsi.webapp.web.model.User;
import ch.supsi.webapp.web.service.CategoryService;
import ch.supsi.webapp.web.service.ItemService;
import ch.supsi.webapp.web.service.RoleService;
import ch.supsi.webapp.web.service.UserService;
import ch.supsi.webapp.web.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        model.addAttribute("richiesta", itemService.getByAnnuncioType("richiesta"));
        model.addAttribute("offerta", itemService.getByAnnuncioType("offerta"));
        return "bIndex";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "bLoginPage.html";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegister() {
        return "bRegisterPage.html";
    }



    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String postRegister(@RequestParam("nome") String name,
                               @RequestParam("surname") String surname,
                               @RequestParam("username") String username,
                               @RequestParam("password") String password,
                               Model model) {

        //TODO IF USER ALREADY EXIST DESTROY
        User user = new User(username,name,surname,roleService.findById(8),new BCryptPasswordEncoder().encode(password));
        userService.flushUser(user);

        return "redirect:/";
    }


    @RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
    public String getItem(Model model, @PathVariable int id) {
        Item item;
        if(itemService.getItemById(id).isPresent()){
            item = itemService.getItemById(id).get();
        }else{
            item=null;
        }

        model.addAttribute("oggetto", item);
        return "bItemDetails";
    }

    @RequestMapping(value = "/item/new", method = RequestMethod.GET)
    public String getNew(Model model) {
        model.addAttribute("utenti",userService.getAllUsers());
        model.addAttribute("categorie",categoryService.getAllCategory());

        return "bCreaItem";
    }

    @RequestMapping(value = "/item/new", method = RequestMethod.POST)
    public String postNew(Model model) {
        return "redirect:/bIndex";
    }

    @RequestMapping(value = "/bEditItem.html", method = RequestMethod.GET)
    public String getEditRed(Model model) {
        model.addAttribute("utenti",userService.getAllUsers());
        model.addAttribute("categorie",categoryService.getAllCategory());
        return "bEditItem";
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
        return "bEditItem";
    }

    @RequestMapping(value = "/item/{id}/edit", method = RequestMethod.POST)
    public String postEditItem(@PathVariable int id,
                               @RequestParam("author") String author,
                               @RequestParam("title") String title,
                               @RequestParam("category") String categoria,
                               @RequestParam("annuncio") String annuncio,
                               @RequestParam("description") String description,
                               Model model) {
        Date date1= new Date();
        Item item = null;
        if(itemService.getItemById(id).isPresent() && itemService.getItemById(id).get().getImage() != null){
             item = new Item(title,description,userService.findUserByUsername(author), categoryService.getByName(categoria),date1,annuncio,id,itemService.getItemById(id).get().getImage());
        }else{
             item = new Item(title,description,userService.findUserByUsername(author), categoryService.getByName(categoria),date1,annuncio,id);
        }

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

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@RequestParam("id") String id,Model model) {
        return deleteEditItem(Integer.valueOf(id),model);
    }

    @RequestMapping(value = "/bItemDetails.html", method = RequestMethod.GET)
    public String itemsDetail(Model model) {
        return "bItemDetails";
    }

    @RequestMapping(value = "/bItemDetails.html", method = RequestMethod.POST)
    public String POSTitemsDetail(@RequestParam("id") int id,
                                  Model model) {
        return getItem(model, id);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String POSTIndexitemsDetail(@RequestParam("id") String id,
                                  Model model) {
        return getItem(model, Integer.valueOf(id));
    }

    //TODO QUA
    @RequestMapping(value = "/edita", method = RequestMethod.POST)
    public String POSTeditItemDetails(@RequestParam("id") String id,
                                       Model model) {
        return getEditItem(model, Integer.valueOf(id));
    }

    @RequestMapping(value = "/bCreaItem.html", method = RequestMethod.GET)
    public String createItem(Model model) {
        return getNew(model);
    }



    @RequestMapping(value = "/bCreaItem.html", method = RequestMethod.POST)
    public String postItem(@RequestParam("author") String author,
                           @RequestParam("title") String title,
                           @RequestParam("category") String categoria,
                           @RequestParam("annuncio") String annuncio,
                           @RequestParam("description") String description,
                           @RequestParam("image") MultipartFile file,
                            Model model) {
        int ID = (int) itemService.getLen();

        Date date1= new Date();
        Item item = null;

        //TODO Refactor
        if(!file.isEmpty()){
            try {
                item = new Item(title,description,userService.findUserByUsername(author), categoryService.getByName(categoria),date1,annuncio,ID,file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            item = new Item(title,description,userService.findUserByUsername(author), categoryService.getByName(categoria),date1,annuncio,ID);
        }

        itemService.insertInDb(item);

        return "redirect:/";
    }

    @RequestMapping(value = "/bIndex.html", method = RequestMethod.GET)
    public String home(Model model) {
        return "redirect:/";
    }


    @GetMapping(value = "/item/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] showProductImage(@PathVariable int id){
        Item item;
        if(itemService.getItemById(id).isPresent()){
            item = itemService.getItemById(id).get();

            if(item.getImage()!=null && item.getImage().length != 0){
                return item.getImage();
            }
        }
        return null;
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

}
