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

@WebServlet(value="/Item")
public class ItemServlet extends HttpServlet {
    static ArrayList<Item> oggetti = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(out, oggetti);
        final byte[] listaScritta = out.toByteArray();

        res.getWriter().println(new String(listaScritta));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        Item letto;

        //gestisco la parte di richiesta in caso sia Raw ovvero che mi arriva json con postman
        String contType = req.getContentType();

        if(contType!= null && contType.equals("application/json")){
            StringBuffer jb = new StringBuffer();
            String line = null;
            try {
                BufferedReader reader = req.getReader();
                while ((line = reader.readLine()) != null)
                    jb.append(line);
            } catch (Exception e) { /*report an error*/ }

            letto = mapper.readValue(jb.toString(),Item.class);
        }else{
            letto = mapper.readValue("{\"title\":\""+req.getParameter("title")+"\",\"description\":\""+req.getParameter("description")+"\",\"author\":\""+req.getParameter("author")+"\"}",Item.class);
        }

        oggetti.add(letto);

        String jsonString = mapper.writeValueAsString(letto);
        resp.getWriter().println(jsonString);
    }
}