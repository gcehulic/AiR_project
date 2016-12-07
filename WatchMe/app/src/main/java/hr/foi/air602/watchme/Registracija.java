package hr.foi.air602.watchme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hr.foi.air602.watchme.database.UserAdapter;
import hr.foi.air602.watchme.database.entities.User;

public class Registracija extends AppCompatActivity {

    EditText editTextIme, editTextPrezime, editTextEmail, editTextKorIme, editTextLozinka;
    Button btnRegistrirajSe;

    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registracija);

        userAdapter = new UserAdapter(this);

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
                String korisnickoIme=editTextKorIme.getText().toString();
                String lozinka=editTextLozinka.getText().toString();

                boolean postoji = false;

                for(User user : userAdapter.getAllUsers()){
                    if(user.username.equals(korisnickoIme)) {
                        postoji = true;
                    }
                }

                if(ime.equals("")||prezime.equals("")||mail.equals("")||korisnickoIme.equals("")||lozinka.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Niste popunili sva polja!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (postoji==true)
                {
                    Toast.makeText(Registracija.this, "Korisničko ime već postoji u bazi!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    userAdapter.insertUser(new User(ime, prezime, mail, korisnickoIme, lozinka));
                    Toast.makeText(getApplicationContext(), "Račun je uspješno kreiran!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Registracija.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userAdapter.close();
    }

}
