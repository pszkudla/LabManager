package pl.visa.labmanager.DbUtils;

import pl.visa.labmanager.CsvFilesParser.DbLogDetails;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtils {
    public static Connection getConnection()  throws SQLException {
        return DriverManager.getConnection(DbLogDetails.CONNECTION_URL, DbLogDetails.USER_NAME, DbLogDetails.PASSWORD);
    }
}
