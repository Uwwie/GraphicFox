package repairkit;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateTable {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, FileNotFoundException {
        Class.forName("org.h2.Driver");
        Connection con = DriverManager.getConnection("jdbc:h2:"+"./database/gamestats","root","password");
        CreateColumn cc = new CreateColumn();
        cc.CreateColumns(con);
        con.close();
    }
}
