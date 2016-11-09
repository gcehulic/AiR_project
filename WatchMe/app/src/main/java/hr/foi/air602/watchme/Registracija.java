package hr.foi.air602.watchme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hr.foi.air602.database.entities.LoginDataBaseAdapter;

public class Registracija extends AppCompatActivity {

    EditText editTextIme, editTextPrezime, editTextEmail, editTextKorIme, editTextLozinka;
    Button btnRegistrirajSe;
    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registracija);

        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        editTextIme=(EditText)findViewById(R.id.editTextIme);
        editTextPrezime=(EditText)findViewById(R.id.editTextPrezime);
        editTextEmail=(EditText)findViewById(R.id.editTextEmail);
        editTextKorIme=(EditText)findViewById(R.id.editTextKorIme);
        editTextLozinka=(EditText)findViewById(R.id.editTextLozinka);
        btnRegistrirajSe=(Button)findViewById(R.id.btnRegistrirajSe);

        btnRegistrirajSe.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String ime=editTextIme.getText().toString();
                String prezime=editTextPrezime.getText().toString();
                String mail=editTextEmail.getText().toString();
                String korisnicko_ime=editTextKorIme.getText().toString();
                String lozinka=editTextLozinka.getText().toString();

                String spremljeno_kor_ime = loginDataBaseAdapter.getSinlgeEntry(korisnicko_ime);

                if(ime.equals("")||prezime.equals("")||mail.equals("")||korisnicko_ime.equals("")||lozinka.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Niste popunili sva polja!", Toast.LENGTH_LONG).show();
                    return;
                }
                else if (korisnicko_ime.equals(spremljeno_kor_ime))
                {
                    Toast.makeText(Registracija.this, "Korisničko ime već postoji u bazi!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    loginDataBaseAdapter.insertEntry(ime, prezime, mail, korisnicko_ime, lozinka);
                    Toast.makeText(getApplicationContext(), "Račun je uspješno kreiran!", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Registracija.this, Login.class);
                    startActivity(i);
                    finish();
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        loginDataBaseAdapter.close();
    }

}
