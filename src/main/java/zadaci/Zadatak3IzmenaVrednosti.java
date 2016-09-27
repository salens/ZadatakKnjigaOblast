package zadaci;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import model.Knjiga;
import model.Oblast;

import java.io.IOException;
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

        try {
            connectionSource = new JdbcConnectionSource("jdbc:sqlite:knjigaOblast.db");


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
            List<Oblast> noveOblasti = oblastDao.queryForAll();
            for (Oblast o : noveOblasti)
                System.out.println(o.toString());



            //SQL naredbe koje zelimo da posaljemo bazi
        } catch ( Exception e )
        /*Hvatamo bilo kakav izuzetak koji moze da znaci
           da ne mozemo da uspostavimo konekciju sa bazom
         */
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        } finally {
            if (connectionSource != null) {
                try {
                    connectionSource.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Uspesno kreirao bazu podataka");
    }
}
