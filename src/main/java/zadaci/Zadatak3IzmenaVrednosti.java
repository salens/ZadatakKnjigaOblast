package zadaci;

import com.j256.ormlite.support.ConnectionSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by android on 27.9.16..
 */
public class Zadatak3IzmenaVrednosti {
    public static void main(String[] args) {

        ConnectionSource connectionSource = null;
        Connection c = null;
        try {
            //Inicjalizujemo drajver za SQLite
            Class.forName("org.sqlite.JDBC");
            //Upostavljamo konekciju sa bazom
            c = DriverManager.getConnection("jdbc:sqlite:knjigaOblast.db");



            //SQL naredbe koje zelimo da posaljemo bazi
        } catch ( Exception e )
        /*Hvatamo bilo kakav izuzetak koji moze da znaci
           da ne mozemo da uspostavimo konekciju sa bazom
         */
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        } finally{
            try {
                /*Zatvaramo konekciju sa bazom u slucaju da se desi neki
                   izuzetak ili ako sve uspe uspesno da se izvrsi
                 */
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Uspesno kreirao bazu podataka");
    }
}
