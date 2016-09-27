package zadaci;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.support.ConnectionSource;
import model.Knjiga;
import model.Oblast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by android on 27.9.16..
 */
public class Zadatak4BrisanjeVrednosti {

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

            List<Oblast> oblast = oblastDao.queryForAll();
            for (Oblast o : oblast)
                System.out.println(o.toString());

            oblast = oblastDao.queryForEq(Oblast.POLJE_NAZIV, "Aritmeticki operatori");
            oblast.get(0);
            for (Oblast obl : oblast){
                oblastDao.delete(obl);
            }


            List<Oblast> obl = oblastDao.queryForAll();
            for (Oblast ob : obl)
                System.out.println(ob.toString());



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
