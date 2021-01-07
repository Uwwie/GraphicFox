import java.io.IOException;
import java.sql.SQLException;

public class GrapicVariant {


    public void GraphicGame(String name, int progress, int save) throws IOException, ClassNotFoundException, SQLException {
        Decryption dec = new Decryption();
        try {
            dec.create();
        } catch (IOException e) { }
        SimpleGUI.main(name,progress,save);

    }

}
