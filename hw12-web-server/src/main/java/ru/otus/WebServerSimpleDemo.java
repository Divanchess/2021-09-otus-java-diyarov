package ru.otus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.otus.dao.ClientDao;
import ru.otus.dao.DbClientsDao;
import ru.otus.dao.InMemoryUserDao;
import ru.otus.dao.UserDao;
import ru.otus.server.webserver.ClientsWebServer;
import ru.otus.server.webserver.ClientsWebServerSimple;
import ru.otus.server.services.TemplateProcessor;
import ru.otus.server.services.TemplateProcessorImpl;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница пользователей
    http://localhost:8080/users

    // REST сервис
    http://localhost:8080/api/user/3
*/
public class WebServerSimpleDemo {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    public static void main(String[] args) throws Exception {
        UserDao userDao = new InMemoryUserDao();
        ClientDao clientDao = new DbClientsDao();
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        ClientsWebServer clientsWebServer = new ClientsWebServerSimple(WEB_SERVER_PORT, userDao,
                clientDao, gson, templateProcessor);

        clientsWebServer.start();
        clientsWebServer.join();
    }
}
