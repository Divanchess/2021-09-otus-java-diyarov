package ru.otus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import ru.otus.dao.ClientDao;
import ru.otus.dao.DbClientsDao;
import ru.otus.dao.InMemoryUserDao;
import ru.otus.dao.UserDao;
import ru.otus.server.helpers.FileSystemHelper;
import ru.otus.server.webserver.ClientsWebServer;
import ru.otus.server.webserver.ClientsWebServerWithBasicSecurity;
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
public class WebServerWithBasicSecurityDemo {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    private static final String HASH_LOGIN_SERVICE_CONFIG_NAME = "realm.properties";
    private static final String REALM_NAME = "AnyRealm";

    public static void main(String[] args) throws Exception {
        UserDao userDao = new InMemoryUserDao();
        ClientDao clientDao = new DbClientsDao();
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        String hashLoginServiceConfigPath = FileSystemHelper.localFileNameOrResourceNameToFullPath(HASH_LOGIN_SERVICE_CONFIG_NAME);
        LoginService loginService = new HashLoginService(REALM_NAME, hashLoginServiceConfigPath);
        //LoginService loginService = new InMemoryLoginServiceImpl(userDao);

        ClientsWebServer clientsWebServer = new ClientsWebServerWithBasicSecurity(WEB_SERVER_PORT,
                loginService, userDao, clientDao, gson, templateProcessor);

        clientsWebServer.start();
        clientsWebServer.join();
    }
}
