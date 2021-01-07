import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Game {
    public static void main(String[] args) throws Exception {
        Connection con = DriverManager.getConnection("jdbc:h2:"+"./database/gamestats","root","password");
        String ps ="";
        Statement st = con.createStatement();
        MainTools mt = new MainTools();
        mt.BeforeMainLoop(st);
        mt.MainLoop(con,st);
        mt.SwitchGameMode(st,con);
        st.close();
        con.close();
    }
}

