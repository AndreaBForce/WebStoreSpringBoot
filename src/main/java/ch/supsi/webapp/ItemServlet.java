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

@WebServlet(value="/items/*")
public class ItemServlet extends HttpServlet {
    static ArrayList<Item> oggetti = new ArrayList<>();
    static ObjectMapper mapper = new ObjectMapper();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {





        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        System.out.println(req.getPathInfo());

        mapper.writeValue(out, oggetti);
        final byte[] listaScritta = out.toByteArray();

        res.setContentType("application/json");
        res.getWriter().println(new String(listaScritta));
    }


    //Creazione item con post
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Item letto;

        //gestisco la parte di richiesta
        String contType = req.getContentType();
        if(contType!= null && contType.equals("application/json")){
            StringBuffer jb = new StringBuffer();
            String line;
            try {
                BufferedReader reader = req.getReader();
                while ((line = reader.readLine()) != null)
                    jb.append(line);
            } catch (Exception e){}

            letto = mapper.readValue(jb.toString(),Item.class);
        }else{
            letto = mapper.readValue("{\"title\":\""+req.getParameter("title")+"\",\"description\":\""+req.getParameter("description")+"\",\"author\":\""+req.getParameter("author")+"\"}",Item.class);
        }
        oggetti.add(letto);

        String jsonString = mapper.writeValueAsString(letto);

        resp.setContentType("application/json");
        resp.getWriter().println(jsonString);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }


}