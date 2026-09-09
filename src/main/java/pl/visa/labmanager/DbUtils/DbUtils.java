package pl.visa.labmanager.DbUtils;

import io.github.cdimascio.dotenv.Dotenv;
import pl.visa.labmanager.CsvFilesParser.DbLogDetails;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtils {
    public static Connection getConnection()  throws SQLException {
        Dotenv dotenv = Dotenv.configure().load();
        String url = dotenv.get("spring.datasource.url");
        String login = dotenv.get("spring.datasource.username");
        String password = dotenv.get("spring.datasource.password=");

        return DriverManager.getConnection(url, login, password);
    }
}
