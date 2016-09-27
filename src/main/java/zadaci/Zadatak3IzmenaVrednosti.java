package zadaci;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import model.Knjiga;
import model.Oblast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by android on 27.9.16..
 */
public class Zadatak3IzmenaVrednosti {

    static Dao<Knjiga,Integer> knjigaDao;
    static Dao<Oblast,Integer> oblastDao;

    public static void main(String[] args) {

        ConnectionSource connectionSource = null;
        Connection c = null;
        try {
            //Inicjalizujemo drajver za SQLite
            Class.forName("org.sqlite.JDBC");
            //Upostavljamo konekciju sa bazom
            c = DriverManager.getConnection("jdbc:sqlite:knjigaOblast.db");


            knjigaDao = DaoManager.createDao(connectionSource, Knjiga.class);
            oblastDao = DaoManager.createDao(connectionSource, Oblast.class);



            //izlistavanje svih oblasti i knjiga
            List<Oblast> oblast = oblastDao.queryForAll();
            for (Oblast o : oblast)
                System.out.println(o.toString());

            oblast = oblastDao.queryForEq(Oblast.POLJE_NAZIV, "Activity klasa");
            for (Oblast obl : oblast){
                obl.setPocetnaStrana(35);
                oblastDao.update(obl);
            }

            for (Oblast o : oblast)
                System.out.println("Pocetna strana " + o.getPocetnaStrana() + "Updatovane oblasti");


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
