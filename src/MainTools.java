import java.sql.*;
import java.util.Scanner;

public class MainTools {
    String sql, s = "", ps = "Зарегистрированные имена:",inject = "";;
    ResultSet rs;
    int v = 0, mode = 0, sv = 0, pgs = 1;
    boolean anotherflag2 = true;
    Scanner scanner = new Scanner(System.in);


    public void BeforeMainLoop(Statement st) throws Exception {
        System.out.println("Подсказка 1: Создайте нового пользователя командой create 'name' 'password' или выберите существующего командой choose 'name' 'password'");
        System.out.println("Подсказка 2: Сейчас можно удалить пользователя командой delete 'name' 'password'");
        sql = "SELECT * FROM SAVES";
        rs = st.executeQuery(sql);
        while (rs.next()) {
            ps = ps + " " + rs.getString(1);
        }
        System.out.println(ps);
    }

    public void MainLoop(Connection con, Statement st) throws Exception{
        boolean flag = true, flag2 = true, flag3 = false, executed = false, mypants = true;
        int checker = 0;
        Scanner scanner = new Scanner(System.in);
        String[] f = new String[3];
        f[0] = "";
        f[1] = "";
        f[2] = "";

        while (flag2) {
            s = scanner.nextLine();
            if (s.equals("quit")) {
                rs.close();
                st.close();
                con.close();
                System.exit(0);
            }
            if (flag) {
                for (int i = 0; i < s.length(); i++) {
                    if (s.substring(i, i + 1).equals(" ")) ++checker;
                    if (checker > 2) break;
                    if (!(s.substring(i, i + 1).equals(" "))) f[checker] = f[checker] + s.substring(i, i + 1);
                }
                if (checker != 2 || (s.substring(s.length() - 1, s.length())).equals(" ") || (s.substring(0, 1)).equals(" ") || (!((f[0].equals("create")) || (f[0].equals("delete")) || (f[0].equals("choose")))))
                    System.out.println("Неверная команда"); else
                if (f[0].equals("create")) {
                    sql = "SELECT * FROM SAVES WHERE name='" + f[1] + "'";
                    rs = st.executeQuery(sql);
                    if (rs.next()) System.out.println("Это имя уже занято");
                    else {
                        sql = "INSERT INTO SAVES(name,password,progress,save) values('" + f[1] + "','" + f[2] + "',1,0)";
                        st.executeUpdate(sql);
                        flag = false;
                        flag3 = true;
                        inject = f[1];
                    }
                } else
                if (f[0].equals("choose")) {
                    sql = "SELECT * FROM SAVES WHERE name='" + f[1] + "' AND password='" + f[2] + "'";
                    rs = st.executeQuery(sql);
                    if (rs.next()) {
                        System.out.println("Регистрация успешна");
                        flag = false;
                        flag3 = true;
                        inject = f[1];
                        sv = rs.getInt(4);
                        pgs = rs.getInt(3);
                    } else System.out.println("Такого пользователя нет или пароль неверен");
                } else
                if (f[0].equals("delete")) {
                    sql = "SELECT * FROM SAVES WHERE name='" + f[1] + "' AND password='" + f[2] + "'";
                    rs = st.executeQuery(sql);
                    if (rs.next()) {
                        sql = "DELETE FROM SAVES WHERE name='" + f[1] + "'";
                        st.executeUpdate(sql);
                    } else System.out.println("Такого пользователя нет или пароль неверен");
                }

                checker = 0;
                f[0] = "";
                f[1] = "";
                f[2] = "";
            }
            if (flag3) {
                if (!executed) {
                    System.out.println("Подсказка 3: игру в режиме консоль можно сохранить командой save");
                    System.out.println("Подсказка 4: Выберите режим игры: 1 - консоль, 2 - приложение. Введите 1 или 2");
                    executed = true;
                    mypants = false;
                }
                if (!mypants) {
                    if (s.equals("1")) {
                        mode = 1;
                        flag3 = false;
                        flag2 = false;
                    } else if (s.equals("2")) {
                        mode = 2;
                        flag3 = false;
                        flag2 = false;
                    }
                }
            }
        }
    }

    public void SwitchGameMode(Statement st,Connection con) throws Exception{
        switch (mode) {
            case 1:
                System.out.println("Подсказка 5: Введите 'start' чтобы начать новую игру 'continue' чтобы продолжить с чекпоинта 'load' чтобы загрузить игру 'quit' чтобы покинуть игру");
                while (anotherflag2) {
                    s = scanner.nextLine();
                    switch (s) {
                        case "start":
                            pgs = 1; anotherflag2 = false; break;
                        case "continue":
                            sql = "SELECT * FROM SAVES WHERE name='" + inject + "'";
                            rs = st.executeQuery(sql);
                            if (rs.next()) {
                                pgs = rs.getInt(3);
                            }
                            anotherflag2 = false;
                            break;
                        case "load":
                            if (sv == 0) sv = 1;
                            pgs = sv; anotherflag2 = false; break;
                        case "quit":
                            rs.close();
                            st.close();
                            con.close();
                            System.exit(0);
                    }
                }
                ConsoleVariant console = new ConsoleVariant();
                rs.close();
                st.close();
                con.close();
                console.ConsoleGame(inject,pgs,sv);
                break;
            case 2:
                System.out.println("Подсказка 5: Введите 'start' чтобы начать новую игру 'continue' чтобы продолжить с чекпоинта 'load' чтобы загрузить игру 'quit' чтобы покинуть игру");
                while (anotherflag2) {
                    s = scanner.nextLine();
                    switch (s) {
                        case "start":
                            pgs = 1; anotherflag2 = false; break;
                        case "continue":
                            sql = "SELECT * FROM SAVES WHERE name='" + inject + "'";
                            rs = st.executeQuery(sql);
                            if (rs.next()) {
                                pgs = rs.getInt(3);
                            }
                            anotherflag2 = false;
                            break;
                        case "load":
                            if (sv == 0) sv = 1;
                            pgs = sv; anotherflag2 = false; break;
                        case "quit":
                            rs.close();
                            st.close();
                            con.close();
                            System.exit(0);
                    }
                }
                GrapicVariant graph = new GrapicVariant();
                rs.close();
                st.close();
                con.close();
                graph.GraphicGame(inject,pgs,sv);
                break;
        }
    }


}
