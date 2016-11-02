package hr.foi.air602.database.entities;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

/**
 * Created by Goran on 2.11.2016..
 */

@Table(database = MainDatabase.class)
public class Korisnik extends BaseModel{

    @PrimaryKey(autoincrement = true)
    @Column int id_korisnika;
    @Column String ime;
    @Column String prezime;
    @Column Date datum_rodenja;
    @Column String mail;
    @Column String korisnicko_ime;
    @Column String lozinka;

    public Korisnik() {
    }

    public Korisnik(int id_korisnika, String ime, String prezime, Date datum_rodenja, String mail, String korisnicko_ime, String lozinka) {
        this.id_korisnika = id_korisnika;
        this.ime = ime;
        this.prezime = prezime;
        this.datum_rodenja = datum_rodenja;
        this.mail = mail;
        this.korisnicko_ime = korisnicko_ime;
        this.lozinka = lozinka;
    }

    public int getId_korisnika() {
        return id_korisnika;
    }

    public void setId_korisnika(int id_korisnika) {
        this.id_korisnika = id_korisnika;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public Date getDatum_rodenja() {
        return datum_rodenja;
    }

    public void setDatum_rodenja(Date datum_rodenja) {
        this.datum_rodenja = datum_rodenja;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getKorisnicko_ime() {
        return korisnicko_ime;
    }

    public void setKorisnicko_ime(String korisnicko_ime) {
        this.korisnicko_ime = korisnicko_ime;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }
}
