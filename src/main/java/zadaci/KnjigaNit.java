package zadaci;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import model.Knjiga;
import model.Oblast;

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
        Connection c = null;
        try {
            //Inicjalizujemo drajver za SQLite
            Class.forName("org.sqlite.JDBC");
            //Upostavljamo konekciju sa bazom
            c = DriverManager.getConnection("jdbc:sqlite:knjigaOblast.db");

            knjigaDao = DaoManager.createDao(connectionSource, Knjiga.class);
            oblastDao = DaoManager.createDao(connectionSource, Oblast.class);


            List<Knjiga> knjige = knjigaDao.queryForAll();
            List<KnjigaNit> knjige2 = new ArrayList<>();

            for (Knjiga k : knjige){
                KnjigaNit knjigaNit = new KnjigaNit(k, "dddfd");
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
        System.out.println("Uspesno ucitanja baza podataka");
    }

    private void knjizara(){

        do{
            try {
                System.out.println("Pozajmljuje knjigu od 0 do 5 sec");


                this.sleep(500);

                    synchronized (knjiga){

                        System.out.println("Knjiga je zauzeta");
                    }

                knjiga.setPrisutna(true);
                System.out.println("Biblioteka se zatvara");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while(knjiga.isPrisutna() != true);



    }



    @Override
    public void run() {

        knjizara();

    }
}
