package ch.supsi.webapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
        //oggetti.add(new Item(req.getParameter("title"),req.getParameter("description"),req.getParameter("author")));
        //resp.getWriter().println("{\n\"title\": "+req.getParameter("title")+"\n\"description\": "+req.getParameter("description")+"\n\"author\": "+req.getParameter("author")+"\n}");
        ObjectMapper mapper = new ObjectMapper();
        Item letto = mapper.readValue("{\"title\":\""+req.getParameter("title")+"\",\"description\":\""+req.getParameter("description")+"\",\"author\":\""+req.getParameter("author")+"\"}",Item.class);
        oggetti.add(letto);

        String jsonString = mapper.writeValueAsString(letto);
        resp.getWriter().println(jsonString);
    }
}