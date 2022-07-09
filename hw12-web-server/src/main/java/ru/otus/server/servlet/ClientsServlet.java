package ru.otus.server.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.dao.ClientDao;
import ru.otus.server.services.TemplateProcessor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ClientsServlet extends HttpServlet {

    private static final String CLIENTS_PAGE_TEMPLATE = "clients.html";
    private static final String TEMPLATE_ATTR_RANDOM_CLIENT = "randomClient";

    private final ClientDao clientDao;
    private final TemplateProcessor templateProcessor;

    public ClientsServlet(TemplateProcessor templateProcessor, ClientDao clientDao) {
        this.templateProcessor = templateProcessor;
        this.clientDao = clientDao;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        clientDao.findRandomClient().ifPresent(randomClient -> paramsMap.put(TEMPLATE_ATTR_RANDOM_CLIENT, randomClient));

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(CLIENTS_PAGE_TEMPLATE, paramsMap));
    }

}
