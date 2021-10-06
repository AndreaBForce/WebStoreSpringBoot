package ch.supsi.webapp;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

//Service

@WebServlet(value = "/items/*")
public class ItemServlet extends HttpServlet {
    static ArrayList<Item> oggetti = new ArrayList<>();
    static ObjectMapper mapper = new ObjectMapper();
    static int proc_id = 0;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        //percorso
        String pathInfo = req.getPathInfo();

        //Se path info è null -> stampiamo la lista di elementi
        //Altrimenti ritorniamo l'elemento voluto, o se non esiste nulla
        boolean presente = false;
        if (pathInfo != null) {

            String idRequested = null;
            if (pathInfo.length() <= 6) {
                res.setStatus(400);
                //badrequest
            } else {
                idRequested = pathInfo.substring(6);
            }

            if (idRequested != null) {
                for (Item item : oggetti) {
                    if (item.getId().equals(idRequested)) {
                        res.setStatus(200);
                        res.setContentType("application/json");
                        res.getWriter().println(mapper.writeValueAsString(item));
                        presente = true;
                        break;
                    }
                    presente = false;
                }

                if (!presente) {
                    res.getWriter().println("Elemento non presente");
                    res.setStatus(404);
                }
            } else {
                res.setStatus(400);
                //id non presente
            }

        } else {
            mapper.writeValue(out, oggetti);
            final byte[] listaScritta = out.toByteArray();
            res.setStatus(200);
            res.setContentType("application/json");
            res.getWriter().println(new String(listaScritta));
        }
    }


    //Creazione item con post
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Item letto;

        //gestisco la parte di richiesta
        String contType = req.getContentType();
        if (contType != null && contType.equals("application/json")) {
            StringBuffer jb = new StringBuffer();
            String line;
            try {
                BufferedReader reader = req.getReader();
                while ((line = reader.readLine()) != null)
                    jb.append(line);
            } catch (Exception e) {
            }

            letto = mapper.readValue(jb.toString(), Item.class);
        } else {
            letto = mapper.readValue("{\"title\":\"" + req.getParameter("title") + "\",\"description\":\"" + req.getParameter("description") + "\",\"author\":\"" + req.getParameter("author") + "\"}", Item.class);
        }

        letto.setId(String.valueOf(proc_id));
        oggetti.add(letto);

        proc_id++;

        String jsonString = mapper.writeValueAsString(letto);
        resp.setStatus(200);
        resp.setContentType("application/json");
        resp.getWriter().println(jsonString);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pathInfo = req.getPathInfo();

        //Se path info è null -> nulla
        //Altrimenti eliminiamo l'elemento
        String idRequested = null;
        boolean presente = false;
        if (pathInfo != null) {

            if (pathInfo.length() <= 6) {
                //ritornare codice errore
                resp.setStatus(400);
            } else {
                idRequested = pathInfo.substring(6);
            }

            if (idRequested != null) {
                for (Item item : oggetti) {
                    if (item.getId().equals(idRequested)) {
                        oggetti.remove(item);

                        //return corretto
                        presente = true;
                        resp.setStatus(200);
                        resp.getWriter().println("Elemento " + item.getId() + " rimosso correttamente");
                        break;
                    }
                    presente = false;
                }

                if (!presente) {
                    resp.getWriter().println("Elemento non presente");
                    resp.setStatus(404);
                }
            } else {
                //ritornare codice errore
                resp.setStatus(400);
            }
        } else {
            resp.setStatus(400);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        //Se path info è null -> nulla
        //Altrimenti modifichiamo l'elemento
        String idRequested = null;
        boolean presente = false;
        if (pathInfo != null) {

            if (pathInfo.length() <= 6) {
                //ritornare codice errore
                resp.setStatus(400);
            } else {
                idRequested = pathInfo.substring(6);
            }

            if (idRequested != null) {
                for (Item item : oggetti) {
                    if (item.getId().equals(idRequested)) {
                        Item letto;
                        //gestisco la parte di richiesta
                        String contType = req.getContentType();
                        if (contType != null && contType.equals("application/json")) {
                            StringBuffer jb = new StringBuffer();
                            String line;
                            try {
                                BufferedReader reader = req.getReader();
                                while ((line = reader.readLine()) != null)
                                    jb.append(line);
                            } catch (Exception e) {
                            }

                            letto = mapper.readValue(jb.toString(), Item.class);
                        } else {
                            letto = mapper.readValue("{\"title\":\"" + req.getParameter("title") + "\",\"description\":\"" + req.getParameter("description") + "\",\"author\":\"" + req.getParameter("author") + "\"}", Item.class);
                        }

                        item.setDescription(letto.getDescription());
                        item.setAuthor(letto.getAuthor());
                        item.setTitle(letto.getTitle());

                        //return corretto
                        presente = true;
                        resp.getWriter().println("Elemento " + item.getId() + " modificato correttamente");
                        break;
                    }
                    presente = false;
                }

                if (!presente) {
                    resp.getWriter().println("Elemento non presente");
                    resp.setStatus(404);
                }
            } else {
                //ritornare codice errore
                resp.setStatus(400);
            }
        } else {
            resp.setStatus(400);
        }
    }


    //Modifica
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pathInfo = req.getPathInfo();

        //Se path info è null -> nulla
        //Altrimenti modifichiamo l'elemento
        String idRequested = null;
        boolean presente = false;
        if (pathInfo != null) {

            if (pathInfo.length() <= 6) {
                //ritornare codice errore
                resp.setStatus(400);
            } else {
                idRequested = pathInfo.substring(6);
            }

            if (idRequested != null) {
                for (Item item : oggetti) {
                    if (item.getId().equals(idRequested)) {
                        Item letto;
                        //gestisco la parte di richiesta
                        String contType = req.getContentType();
                        if (contType != null && contType.equals("application/json")) {
                            StringBuffer jb = new StringBuffer();
                            String line;
                            try {
                                BufferedReader reader = req.getReader();
                                while ((line = reader.readLine()) != null)
                                    jb.append(line);
                            } catch (Exception e) {
                            }

                            letto = mapper.readValue(jb.toString(), Item.class);
                        } else {
                            letto = mapper.readValue("{\"title\":\"" + req.getParameter("title") + "\",\"description\":\"" + req.getParameter("description") + "\",\"author\":\"" + req.getParameter("author") + "\"}", Item.class);
                        }

                        if (letto.getDescription() != null) {
                            item.setDescription(letto.getDescription());
                        }
                        if (letto.getAuthor() != null) {
                            item.setAuthor(letto.getAuthor());
                        }

                        if (letto.getTitle() != null) {
                            item.setTitle(letto.getTitle());
                        }

                        //return corretto
                        presente = true;
                        resp.getWriter().println("Elemento " + item.getId() + " modificato correttamente");
                        break;
                    }
                    presente = false;
                }

                if (!presente) {
                    resp.getWriter().println("Elemento non presente");
                    resp.setStatus(404);
                }
            } else {
                //ritornare codice errore
                resp.setStatus(400);
            }
        } else {
            resp.setStatus(400);
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getMethod().equals("PATCH")) {
            doPatch(req, resp);
        } else {
            super.service(req, resp);
        }
    }
}