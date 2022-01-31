package ch.supsi.webapp.web.controller;

import ch.supsi.webapp.web.model.User;
import ch.supsi.webapp.web.service.CategoryService;
import ch.supsi.webapp.web.service.ItemService;
import ch.supsi.webapp.web.service.RoleService;
import ch.supsi.webapp.web.service.UserService;
import ch.supsi.webapp.web.model.Item;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nonapi.io.github.classgraph.json.JSONDeserializer;
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

    //Ritorna la index page
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getAll(Model model) {
        //TODO PRENDERE LA CATEGORIA E DIVIDERE IN OFFERTA E DOMANDA
        model.addAttribute("richiesta", itemService.getByAnnuncioType("richiesta"));
        model.addAttribute("offerta", itemService.getByAnnuncioType("offerta"));
        return "bIndex";
    }

    //Ritorna la login page
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "bLoginPage.html";
    }

    //Ritorna la pagina di registrazione
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegister() {
        return "bRegisterPage.html";
    }

    //Registra la persona e ritorna all'index
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

    //Ritorna un item richiesto nella pagina item details
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

    //Ritorna la pagina di creazione item
    @RequestMapping(value = "/item/new", method = RequestMethod.GET)
    public String getNew(Model model) {
        model.addAttribute("utenti",userService.getAllUsers());
        model.addAttribute("categorie",categoryService.getAllCategory());

        return "bCreaItem";
    }

    //Inserisce l'item nel db e ritorna l'index
    @RequestMapping(value = "/item/new", method = RequestMethod.POST)
    public String postNew(Model model) {
        return "redirect:/bIndex";
    }

    //Ritorna la pagina di modifica dell'item richiesto
    @RequestMapping(value = "/bEditItem.html", method = RequestMethod.GET)
    public String getEditRed(Model model) {
        model.addAttribute("utenti",userService.getAllUsers());
        model.addAttribute("categorie",categoryService.getAllCategory());
        return "bEditItem";
    }

    //Ritorna la pagina di modifica dell'item con l'item gia inserito
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

    //Modifica l'item e lo inserisce nel db
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
            //TODO FIXARE QUA L'EDIT
             //item = new Item(title,description,userService.findUserByUsername(author), categoryService.getByName(categoria),date1,annuncio,id);
        }

        itemService.modifyById(id,item);

        return "redirect:/";
    }

    //Delete dell'item
    @RequestMapping(value = "/item/{id}/delete", method = RequestMethod.GET)
    public String deleteEditItem(@PathVariable int id,Model model) {

        //Cerco se non è nullo rimuovo e success
        if (itemService.getItemById(id).isPresent()) {
            itemService.deleteById(id);
        }
        return "redirect:/";
    }

    //Delete dell'item ma con la post ovvero chiamato dentro l'html
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@RequestParam("id") String id,Model model) {
        return deleteEditItem(Integer.valueOf(id),model);
    }

    //Ritorna la pagina di item details
    @RequestMapping(value = "/bItemDetails.html", method = RequestMethod.GET)
    public String itemsDetail(Model model) {
        return "bItemDetails";
    }

    //Ritorna la pagina di item details con l'id e i campi precompilati
    @RequestMapping(value = "/bItemDetails.html", method = RequestMethod.POST)
    public String POSTitemsDetail(@RequestParam("id") int id,
                                  Model model) {
        return getItem(model, id);
    }

    //Post che ritorna l'index con solo un item
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String POSTIndexitemsDetail(@RequestParam("id") String id,
                                  Model model) {
        return getItem(model, Integer.valueOf(id));
    }

    //TODO QUA
    //Edita di un singolo item mostrato nella home
    @RequestMapping(value = "/edita", method = RequestMethod.POST)
    public String POSTeditItemDetails(@RequestParam("id") String id,
                                       Model model) {
        return getEditItem(model, Integer.valueOf(id));
    }

    //crea l'item
    @RequestMapping(value = "/bCreaItem.html", method = RequestMethod.GET)
    public String createItem(Model model) {
        return getNew(model);
    }

    //aggiunge l'item nell'db
    @RequestMapping(value = "/bCreaItem.html", method = RequestMethod.POST)
    public String postItem(@RequestParam("author") String author,
                           @RequestParam("title") String title,
                           @RequestParam("category") String categoria,
                           @RequestParam("annuncio") String annuncio,
                           @RequestParam("description") String description,
                           @RequestParam("prezzo") String prezzo,
                           @RequestParam("luogo") String luogo,
                           @RequestParam("image") MultipartFile file,
                            Model model) {
        int ID = (int) itemService.getLen();

        Date date1= new Date();
        Item item = null;

        //TODO Refactor
        if(!file.isEmpty()){
            try {
                item = new Item(title,description,userService.findUserByUsername(author), categoryService.getByName(categoria),date1,annuncio,ID,file.getBytes(),Float.valueOf(prezzo),luogo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            item = new Item(title,description,userService.findUserByUsername(author), categoryService.getByName(categoria),date1,annuncio,ID,Float.valueOf(prezzo),luogo);
        }

        itemService.insertInDb(item);

        return "redirect:/";
    }

    //ritorna la home
    @RequestMapping(value = "/bIndex.html", method = RequestMethod.GET)
    public String home(Model model) {
        return "redirect:/";
    }


    //ritorna la immagine e permette di mostrarla
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

    //termina la sessione e effettua il logout
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }


    //SEARCH
    @RequestMapping(value = "/item/search", method = RequestMethod.GET)
    @ResponseBody
    public List<Item> getSearch(@RequestParam("q") String q) {
        List<Item> items = itemService.searchKeywordInNameAndDesc(q);
        if(!items.isEmpty()){
            for (Item i: items) {
                i.setImage(null);
            }
        }
        return items;
    }


    @RequestMapping(value = "/bConfronta.html/{username}", method = RequestMethod.GET)
    public String getConfronta(@PathVariable String username,Model model) {

        model.addAttribute("pref", userService.findAllConfronta(username));
        model.addAttribute("cat", categoryService.getAllCategory());

        return "bConfronta";
    }

    //EX1
    //Aggiunge il confronta
    @RequestMapping(value = "/addconfronta", method = RequestMethod.POST)
    public String addConfronta(@RequestParam("ids") String id,
                               @RequestParam("usr") String username) {
        userService.findUserByUsername(username).getConfrontabili().add(itemService.getItemById(Integer.valueOf(id)).get());

        itemService.getItemById(Integer.valueOf(id)).get().setUtente(userService.findUserByUsername(username));

        itemService.insertInDb(itemService.getItemById(Integer.valueOf(id)).get());
        userService.flushUser(userService.findUserByUsername(username));

        return "redirect:/";

    }


    @RequestMapping(value = "/item/confronta", method = RequestMethod.GET)
    @ResponseBody
    public List<Item> getConfronta(@RequestParam("usr") String username) {
        List<Item> items = userService.findAllConfronta(username);
        if(!items.isEmpty()){
            for (Item i: items) {
                i.setImage(null);
            }
        }
        return items;
    }

    //Aggiunge il preferito
    @RequestMapping(value = "/addpreferito", method = RequestMethod.POST)
    public String addPreferito(@RequestParam("idpref") String id,
                               @RequestParam("usr_pref") String username) {
        userService.findUserByUsername(username).getPreferiti().add(itemService.getItemById(Integer.valueOf(id)).get());
        itemService.getItemById(Integer.valueOf(id)).get().setUtenteprefe(userService.findUserByUsername(username));
        itemService.insertInDb(itemService.getItemById(Integer.valueOf(id)).get());
        userService.flushUser(userService.findUserByUsername(username));
        return "redirect:/";
    }

    @RequestMapping(value = "/bPreferiti.html/{username}", method = RequestMethod.GET)
    public String getPreferiti(@PathVariable String username,Model model) {
        model.addAttribute("preferiti", userService.findAllPreferiti(username));
        return "bPreferiti";
    }
}


