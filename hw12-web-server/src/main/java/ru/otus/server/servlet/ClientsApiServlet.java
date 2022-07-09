package ru.otus.server.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.dao.ClientDao;
import ru.otus.database.crm.model.Client;

import java.io.IOException;


public class ClientsApiServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(ClientsApiServlet.class);

    private static final int ID_PATH_PARAM_POSITION = 1;

    private final ClientDao clientDao;
    private final Gson gson;

    public ClientsApiServlet(ClientDao clientDao, Gson gson) {
        this.clientDao = clientDao;
        this.gson = gson;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Client client = clientDao.findById(extractIdFromRequest(request)).orElse(null);

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        out.print(gson.toJson(client));
    }

    private long extractIdFromRequest(HttpServletRequest request) {
        String[] path = request.getPathInfo().split("/");
        String id = (path.length > 1)? path[ID_PATH_PARAM_POSITION]: String.valueOf(- 1);
        return Long.parseLong(id);
    }

}
