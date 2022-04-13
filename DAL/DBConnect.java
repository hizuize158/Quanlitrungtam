package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnect {

    protected Connection conn;

    public DBConnect() {
        final String url = "jdbc:oracle:thin:@localhost:1521:orcl";
        final String user = "quanly";
        final String password = "1234";
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
