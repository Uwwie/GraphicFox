import org.h2.store.fs.FileUtils;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.*;
import java.util.Scanner;

public class SimpleGUI extends JFrame {
    private JFrame frame;
    private int secretService = 0;
    private ImageIcon background;
    private  JLabel bl,ts,ts2,ts3;
    private JButton var1,var2,var3,var4,var5,var6;
    private String s,name,variant1,variant2,sql,line1,line2,line3,sm,sf,all,s1,s2,url;
    private int progress, save,ib,comp1,comp2,lastP,values;
    private float volume1,volume2;
    Scanner sc;
    Statement st;
    File file = new File("quest.txt");
    ResultSet rs;
    private int sw = 0;
    Clip clip;
    AudioInputStream inputStream;
    File z;
    Thread player;
    FloatControl gainControl;


    public static void main(String name,int progress,int save) throws IOException, ClassNotFoundException, SQLException {
        SimpleGUI f = new SimpleGUI();
        f.name = name;
        f.progress = progress;
        f.lastP = progress;
        f.save = save;
        f.SimpleGUI();
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    private void SimpleGUI() throws IOException, ClassNotFoundException, SQLException {

        Connection con = DriverManager.getConnection("jdbc:h2:" + "./database/gamestats", "root", "password");
        st = con.createStatement();
        sql = "SELECT * FROM INFORMATION WHERE id=" + progress;
        rs = st.executeQuery(sql);
        if (rs.next()) {
            variant1 = rs.getString(2);
            variant2 = rs.getString(3);
            comp1 = rs.getInt(4); comp2 = rs.getInt(5);
        }
        all = FindText(progress);
        analyze();

        sql = "SELECT * FROM MUSIC WHERE id="+progress;
        rs = st.executeQuery(sql);
        if (rs.next()) {
            s1 = rs.getString(2);
            volume1 = rs.getFloat(3);
        }
        z = new File("temp/Music/"+s1);
        play();
        player.start();
        InitFrames();

        var1 = new JButton(variant1);
        var1.setBackground(Color.ORANGE);
        var1.setForeground(Color.BLACK);
        var1.setBounds(10,890,480,30);
        var1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(var1);
                frame.remove(var2);
                frame.remove(bl);
                frame.remove(ts);
                frame.remove(ts2);
                frame.remove(ts3);
                try {
                    UpdOne(4);
                } catch (SQLException | FileNotFoundException throwables) {
                    throwables.printStackTrace();
                }

                s = "temp/Images/"+String.valueOf(progress)+".jpg";
                background = new ImageIcon(s);
                bl = new JLabel(background);
                bl.setSize(1000,800);
                ts.setText(line1);
                ts2.setText(line2);
                ts3.setText(line3);
                var1.setText(variant1);
                var2.setText(variant2);
                frame.setVisible(false);
                frame.add(bl);
                if (comp1 == comp2 || variant1.equals(variant2)) {
                    frame.add(var5);

                    if(secretService == 1){
                        try {
                            declareURL();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        if (values !=0) {
                            frame.add(var6);
                            secretService = -1;
                        }
                    }

                } else {
                    if (comp1 !=0) frame.add(var1);
                    if (comp2 !=0) frame.add(var2);
                }

                frame.add(ts);
                frame.add(ts2);
                frame.add(ts3);
                frame.setVisible(true);
                try {
                    songchanger();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (UnsupportedAudioFileException unsupportedAudioFileException) {
                    unsupportedAudioFileException.printStackTrace();
                } catch (LineUnavailableException | SQLException lineUnavailableException) {
                    lineUnavailableException.printStackTrace();
                }
            }
        });

        var2 = new JButton(variant2);
        var2.setBackground(Color.ORANGE);
        var2.setForeground(Color.BLACK);
        var2.setBounds(510,890,480,30);
        var2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(var1);
                frame.remove(var2);
                frame.remove(bl);
                frame.remove(ts);
                frame.remove(ts2);
                frame.remove(ts3);
                try {
                    UpdOne(5);
                } catch (SQLException | FileNotFoundException throwables) {
                    throwables.printStackTrace();
                }

                s = "temp/Images/"+String.valueOf(progress)+".jpg";
                background = new ImageIcon(s);
                bl = new JLabel(background);
                bl.setSize(1000,800);
                ts.setText(line1);
                ts2.setText(line2);
                ts3.setText(line3);
                var1.setText(variant1);
                var2.setText(variant2);
                frame.setVisible(false);
                frame.add(bl);
                if (comp1 == comp2 || variant1.equals(variant2)) {
                    frame.add(var5);

                    if(secretService == 1){
                        try {
                            declareURL();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        if (values !=0) {
                            frame.add(var6);
                            secretService = -1;
                        }
                    }

                } else {
                    if (comp1 !=0) frame.add(var1);
                    if (comp2 !=0) frame.add(var2);
                }
                frame.add(ts);
                frame.add(ts2);
                frame.add(ts3);
                frame.setVisible(true);
                try {
                    songchanger();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (UnsupportedAudioFileException unsupportedAudioFileException) {
                    unsupportedAudioFileException.printStackTrace();
                } catch (LineUnavailableException | SQLException lineUnavailableException) {
                    lineUnavailableException.printStackTrace();
                }
            }
        });

        var3 = new JButton("Сохранить игру");
        var3.setBackground(Color.ORANGE);
        var3.setForeground(Color.BLACK);
        var3.setBounds(510,925,480,30);
        var3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save = progress;
                sql = "UPDATE SAVES SET save=" + progress + " WHERE name='" + name + "'";
                try {
                    st.executeUpdate(sql);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        var4 = new JButton("Выйти из игры");
        var4.setBackground(Color.ORANGE);
        var4.setForeground(Color.BLACK);
        var4.setBounds(10,925,480,30);
        var4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sql = "UPDATE SAVES SET progress=" + progress + " WHERE name='" + name + "'";
                clip.stop();
                clip.close();
                try {
                    inputStream.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                try {
                    st.executeUpdate(sql);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    rs.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    st.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    con.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    deleteDirectoryWalkTree(Paths.get("temp"));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                System.exit(0);
            }
        });

        var5 = new JButton("Начать новую игру");
        var5.setBackground(Color.ORANGE);
        var5.setForeground(Color.BLACK);
        var5.setBounds(10,890,480,30);
        var5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(var5);
                frame.remove(var6);
                frame.remove(bl);
                ++secretService;
                try {
                    Startnew();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }

                s = "temp/Images/"+String.valueOf(progress)+".jpg";
                background = new ImageIcon(s);
                bl = new JLabel(background);
                bl.setSize(1000,800);
                ts.setText(line1);
                ts2.setText(line2);
                ts3.setText(line3);
                var1.setText(variant1);
                var2.setText(variant2);
                frame.setVisible(false);
                frame.add(bl);
                frame.add(var1);
                frame.add(var2);
                frame.add(ts);
                frame.add(ts2);
                frame.add(ts3);
                frame.setVisible(true);

                try {
                    songchanger();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (UnsupportedAudioFileException unsupportedAudioFileException) {
                    unsupportedAudioFileException.printStackTrace();
                } catch (LineUnavailableException | SQLException lineUnavailableException) {
                    lineUnavailableException.printStackTrace();
                }

            }
        });

        var6 = new JButton("Секрет");
        var6.setBackground(Color.ORANGE);
        var6.setForeground(Color.BLACK);
        var6.setBounds(510,890,480,30);
        var6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openWebpage(url);
            }
        });

        AddFrames();
    }

    private void InitFrames(){
        s = "temp/Images/"+String.valueOf(progress)+".jpg";
        ImageIcon gena = new ImageIcon("resources/GennadiyGorin3.jpg");
        background = new ImageIcon(s);
        bl = new JLabel(background);
        bl.setSize(1000,800);


        ts = new JLabel(line1);  //128 chars in one line max
        ts.setSize(1000,1000);
        ts.setLocation(0,320);
        ts.setForeground(Color.BLACK);
        ts.setFont(new Font("",Font.BOLD,15));
        ts.setHorizontalAlignment(JLabel.CENTER);


        ts2 = new JLabel(line2);  //128 chars in one line max
        ts2.setSize(1000,1000);
        ts2.setLocation(0,336);
        ts2.setForeground(Color.BLACK);
        ts2.setFont(new Font("",Font.BOLD,15));
        ts2.setHorizontalAlignment(JLabel.CENTER);

        ts3 = new JLabel(line3);  //128 chars in one line max
        ts3.setSize(1000,1000);
        ts3.setLocation(0,352);
        ts3.setForeground(Color.BLACK);
        ts3.setFont(new Font("",Font.BOLD,15));
        ts3.setHorizontalAlignment(JLabel.CENTER);

        frame = new JFrame("Лисенок");
        frame.setIconImage(gena.getImage());
        frame.setSize(1016,1000);
        frame.setResizable(false);
        frame.getContentPane().setBackground(Color.PINK);


    }


    private void AddFrames(){
        frame.setLayout(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(ts);
        frame.add(ts2);
        frame.add(ts3);
        frame.add(bl);
        if (comp1 == comp2 || variant1.equals(variant2)) frame.add(var5); else {
            if (comp1 !=0) frame.add(var1);
            if (comp2 !=0) frame.add(var2);
        }
        frame.add(var3);
        frame.add(var4);
        frame.setVisible(true);
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

    private void UpdOne(int a) throws SQLException, FileNotFoundException {
        sql = "SELECT * FROM INFORMATION WHERE id="+progress;
        rs = st.executeQuery(sql);
        if (rs.next()) {
            progress = rs.getInt(a);
            sql = "UPDATE SAVES SET progress=" + progress + " WHERE name='" + name + "'";
            st.executeUpdate(sql);
        }
        sql = "SELECT * FROM INFORMATION WHERE id="+progress;
        rs = st.executeQuery(sql);
        if (rs.next()) {
            variant1 = rs.getString(2);
            variant2 = rs.getString(3);
            comp1 = rs.getInt(4); comp2 = rs.getInt(5);
        }
        all = FindText(progress);
        analyze();


    }

    private void Startnew() throws SQLException, FileNotFoundException {
        progress = 1;
        sql = "UPDATE SAVES SET progress=" + progress + " WHERE name='" + name + "'";
        st.executeUpdate(sql);

        sql = "SELECT * FROM INFORMATION WHERE id="+progress;
        rs = st.executeQuery(sql);
        sql = "SELECT * FROM INFORMATION WHERE id="+progress;
        rs = st.executeQuery(sql);
        if (rs.next()) {
            variant1 = rs.getString(2);
            variant2 = rs.getString(3);
        }
        all = FindText(progress);
       analyze();

    }



    private void songchanger() throws IOException, UnsupportedAudioFileException, LineUnavailableException, SQLException {
        sql = "SELECT * FROM MUSIC WHERE id="+progress;
        rs = st.executeQuery(sql);
        if (rs.next()) {
            s1 = rs.getString(2);
            volume1 = rs.getFloat(3);
        }

        sql = "SELECT * FROM MUSIC WHERE id="+lastP;
        rs = st.executeQuery(sql);
        if (rs.next()) {
            s2 = rs.getString(2);
            volume2 = rs.getFloat(3);
        }

        if((lastP != progress) && !(s1.equals(s2))) {
                clip.stop();
                clip.close();
                z = new File("temp/Music/"+s1);
                play();
                player.start();
        }
        lastP = progress;
    }

    private void play(){
        player = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    clip = AudioSystem.getClip();
                    inputStream = AudioSystem.getAudioInputStream(z);
                    clip.open(inputStream);
                    gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    gainControl.setValue(20f * (float) Math.log10(volume1));
                    clip.start();
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                } catch(Exception e){
                }

            }
        });
    }

    private void analyze(){
        if (all.length()>240) {
            line1 = all.substring(0,120);
            line2 = all.substring(120,240);
            line3 = all.substring(240);
        } else if (all.length()>120) {
            line1 = all.substring(0,120);
            line2 = all.substring(120);
            line3 = "";
        } else {
            line1 = all;
            line2 = "";
            line3 = "";
        }

    }

    private void openWebpage(String urlString) {
        try {
            Desktop.getDesktop().browse(new URL(urlString).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void declareURL() throws SQLException {
        sql = "SELECT * FROM SECRETS WHERE id=" + progress;
        rs = st.executeQuery(sql);
        if (rs.next()) {
            values = rs.getInt(2);
            url = rs.getString(3);
        }
    }

    void deleteDirectoryWalkTree(Path path) throws IOException {
        FileVisitor visitor = new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                if (exc != null) {
                    throw exc;
                }
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        };
        Files.walkFileTree(path, visitor);
    }



}

