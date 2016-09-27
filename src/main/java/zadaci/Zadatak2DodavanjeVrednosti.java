package zadaci;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import model.Knjiga;
import model.Oblast;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by android on 27.9.16..
 */
public class Zadatak2DodavanjeVrednosti {

    static Dao<Knjiga,Integer> knjigaDao;
    static Dao<Oblast,Integer> oblastDao;

    public static void main(String[] args){

        ConnectionSource connectionSource = null;

        try {
            connectionSource = new JdbcConnectionSource("jdbc:sqlite:knjigaOblast.db");

            knjigaDao = DaoManager.createDao(connectionSource, Knjiga.class);
            oblastDao = DaoManager.createDao(connectionSource, Oblast.class);

            TableUtils.clearTable(connectionSource, Knjiga.class);



            Knjiga knjiga1 = new Knjiga("Java programiranje",650,new Date());
            knjigaDao.create(knjiga1);

            Knjiga knjiga2 = new Knjiga("Android programiranje",500,new Date());
            knjigaDao.create(knjiga2);


            Oblast oblast1 = new Oblast("Uvod", 2);
            oblast1.setKnjiga(knjiga1);
            oblastDao.create(oblast1);


            Oblast oblast2 = new Oblast("Naredbe", 10);
            oblast2.setKnjiga(knjiga1);
            oblastDao.create(oblast2);

            Oblast oblast3 = new Oblast("Aritmeticki operatori", 20);
            oblast3.setKnjiga(knjiga1);
            oblastDao.create(oblast3);

            Oblast oblast4 = new Oblast("Android operativni sistem", 2);
            oblast4.setKnjiga(knjiga2);
            oblastDao.create(oblast4);

            Oblast oblast5 = new Oblast("Activity klasa", 30);
            oblast5.setKnjiga(knjiga2);
            oblastDao.create(oblast5);


           //izlistavanje svih oblasti i knjiga
            List<Oblast> oblast = oblastDao.queryForAll();
            for (Oblast o : oblast)
                System.out.println(o.toString());

            List<Knjiga> knjiga = knjigaDao.queryForAll();
            for (Knjiga k : knjiga)
                System.out.println(k.toString());





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
