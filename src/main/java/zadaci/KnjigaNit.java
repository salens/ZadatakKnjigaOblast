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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 27.9.16..
 */
public class KnjigaNit extends Thread{

    private String imeClana;
    private Knjiga knjiga;

    static Dao<Knjiga,Integer> knjigaDao;
    static Dao<Oblast,Integer> oblastDao;

    public KnjigaNit(Knjiga knjiga, String imeClana) {
        this.knjiga = knjiga;
        this.imeClana = imeClana;
    }

    public static void main(String[] args) {

        ConnectionSource connectionSource = null;

        try {
            connectionSource = new JdbcConnectionSource("jdbc:sqlite:knjigaOblast.db");

            knjigaDao = DaoManager.createDao(connectionSource, Knjiga.class);
            oblastDao = DaoManager.createDao(connectionSource, Oblast.class);


            List<Knjiga> knjige = knjigaDao.queryForAll();
            List<KnjigaNit> knjige2 = new ArrayList<>();

            for (Knjiga k : knjige){
                KnjigaNit knjigaNit = new KnjigaNit(k, "marko");
                knjigaNit.start();
                knjige2.add(knjigaNit);
            }

            for (KnjigaNit knjiga :knjige2) {
                knjiga.join();
            }

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
    }

    private void knjizara(){

        do{
            try {

                int randomnumber = ( (int)(Math.random( )*5000) +1);
                if(knjiga.isPrisutna()) {
                    System.out.println("Pozajmljuje knjigu od 0 do 5 sec");
                    synchronized (knjiga){
                        knjiga.setPrisutna(false);
                    }
                    this.sleep(randomnumber);

                    synchronized (knjiga){
                        knjiga.setPrisutna(true);
                        System.out.println("Vrati mi knjigu");
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while(!knjiga.isPrisutna());


    }


    @Override
    public void run() {

        knjizara();

    }
}
