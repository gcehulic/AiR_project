package hr.foi.air602.watchme.helper;

/**
 * Created by Goran on 2.11.2016..
 */

public class MockData {

    public static void writeAll(){
        Korisnik korisnikTest=new Korisnik();
        korisnikTest.setId_korisnika(1);
        korisnikTest.setIme("Ivo");
        korisnikTest.setPrezime("Ivic");
        korisnikTest.save();
    }
}
