import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
public class ConsoleVariant {
    private boolean eg = false;
    File file = new File("quest.txt");
    Scanner sc;
    int ib,z;
    String sm,sf;

    public void ConsoleGame(String name, int progress, int save) throws Exception {
        String rl,sql,var1="",var2="";
        Scanner scanner = new Scanner(System.in);
        Connection con = DriverManager.getConnection("jdbc:h2:" + "./database/gamestats", "root", "password");
        Statement st = con.createStatement();
        ResultSet rs;
        z = 0;
        while (!eg) {
            System.out.println(FindText(progress));
            sql = "SELECT * FROM INFORMATION WHERE id="+progress;
            rs = st.executeQuery(sql);
            if (rs.next())
            System.out.println(rs.getString(3));
            if (!((rs.getString(2)).equals(rs.getString(3)))) {
                var1 = "Нажми 1 чтобы выбрать " + rs.getString(2);
                var2 = "Нажми 2 чтобы выбрать " + rs.getString(3);
                System.out.println(var1);
                System.out.println(var2);
                rl = scanner.nextLine();

                switch (rl) {
                    case "1":
                        progress = rs.getInt(4);
                        sql = "UPDATE SAVES SET progress=" + progress + " WHERE name='" + name + "'";
                        st.executeUpdate(sql);
                        break;
                    case "2":
                        progress = rs.getInt(5);
                        sql = "UPDATE SAVES SET progress=" + progress + " WHERE name='" + name + "'";
                        st.executeUpdate(sql);
                        break;
                    case "save":
                        save = progress;
                        sql = "UPDATE SAVES SET save=" + progress + " WHERE name='" + name + "'";
                        st.executeUpdate(sql);
                        break;
                    case "quit":
                        sql = "UPDATE SAVES SET progress=" + progress + " WHERE name='" + name + "'";
                        st.executeUpdate(sql);
                        rs.close();
                        st.close();
                        con.close();
                        System.exit(0);
                }
            } else {
                progress = 1; sql = "UPDATE SAVES SET progress=" + progress + " WHERE name='" + name + "'";
                System.out.println("--------------------------------------------------------------------------");
                System.out.println("Новая игра начата");
                System.out.println("--------------------------------------------------------------------------");
            }

        }
    }

    private String FindText(int progress) throws FileNotFoundException {
        sm = ""; ib = 0; sf = "";
        sc = new Scanner(file);
        while (sc.hasNextLine()) {
            ++ib;
            if (ib == progress) sm = sc.nextLine();
            if (sc.hasNextLine())
            sf = sc.nextLine();
        }
        return sm;
    }

}
