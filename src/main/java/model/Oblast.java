package model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by android on 27.9.16..
 */
public class Oblast {

    public static final String POLJE_NAZIV="naziv";
    public static final String POLJE_POCETNA_STRANA="pocetna_strana";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = POLJE_NAZIV,canBeNull = false)
    private String naziv;

    @DatabaseField(columnName = POLJE_POCETNA_STRANA,canBeNull = false)
    private int pocetnaStrana;

    @DatabaseField(foreign = true,foreignAutoRefresh = true,canBeNull = false)
    private Knjiga knjiga;

    public Oblast(){

    }

    public Oblast(String naziv, int pocetnaStrana) {
        this.naziv = naziv;
        this.pocetnaStrana = pocetnaStrana;
    }

    public Oblast(String naziv, int pocetnaStrana,Knjiga knjiga) {
        this.naziv = naziv;
        this.pocetnaStrana = pocetnaStrana;
        this.knjiga = knjiga;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getPocetnaStrana() {
        return pocetnaStrana;
    }

    public void setPocetnaStrana(int pocetnaStrana) {
        this.pocetnaStrana = pocetnaStrana;
    }

    public Knjiga getKnjiga() {
        return knjiga;
    }

    public void setKnjiga(Knjiga knjiga) {
        this.knjiga = knjiga;
    }

    @Override
    public String toString() {
        return "Oblast{" +
                "id=" + id +
                ", naziv='" + naziv + '\'' +
                ", pocetna Strana.=" + pocetnaStrana +
                '}';
    }
}
