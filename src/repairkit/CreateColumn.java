package repairkit;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CreateColumn {
    String sql;
    public void CreateColumns(Connection f) throws SQLException, FileNotFoundException {
        Statement st = f.createStatement();
        sql = "CREATE TABLE IF NOT EXISTS INFORMATION "
                +  "(id INT PRIMARY KEY, "
                + " var1 VARCHAR(255), "
                + " var2 VARCHAR(255), "
                + " route1 INT, "
                + " route2 INT)";
        st.executeUpdate(sql);

        sql = "CREATE TABLE IF NOT EXISTS MUSIC "
                +  "(id INT PRIMARY KEY, "
                + " title VARCHAR(255), "
                + " volume FLOAT)";
        st.executeUpdate(sql);

        sql = "CREATE TABLE IF NOT EXISTS SAVES "
                +  "(name VARCHAR PRIMARY KEY, "
                + " password VARCHAR(255), "
                + " progress INT, "
                + " save INT)";
        st.executeUpdate(sql);

        sql = "CREATE TABLE IF NOT EXISTS SECRETS "
                +  "(id INT PRIMARY KEY, "
                + " value INT, "
                + " url VARCHAR(255))";
        st.executeUpdate(sql);

        InjectInfo(st);
        st.close();
    }

    public void InjectInfo(Statement st) throws FileNotFoundException, SQLException {
        File file = new File("gametext.txt");
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            sql = "INSERT INTO INFORMATION(id,var1,var2,route1,route2) values";
            sql = sql + sc.nextLine();
            st.executeUpdate(sql);
        }

        file = new File("references.txt");
        sc = new Scanner(file);
        while (sc.hasNextLine()) {
            sql = "INSERT INTO MUSIC(id,title,volume) values";
            sql = sql + sc.nextLine();
            st.executeUpdate(sql);
        }

        file = new File("secretcode.txt");
        sc = new Scanner(file);
        while (sc.hasNextLine()) {
            sql = "INSERT INTO SECRETS(id,value,url) values";
            sql = sql + sc.nextLine();
            st.executeUpdate(sql);
        }

    }
}
