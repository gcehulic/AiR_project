package hr.foi.air602.watchme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import hr.foi.air602.database.entities.LoginDataBaseAdapter;

public class Login extends AppCompatActivity {

    private Button btnPrijava, btnRegistracija;
    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        btnPrijava = (Button) findViewById(R.id.btnLogin);
        btnRegistracija = (Button) findViewById(R.id.btnReg);

        btnRegistracija.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Registracija.class);
                startActivity(i);
            }
        });

        btnPrijava.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                signIn(v);
            }
        });

    }


    public void signIn(View V) {
        final EditText editTextUserName = (EditText) findViewById(R.id.kor_ime);
        final EditText editTextPassword = (EditText) findViewById(R.id.password);

        Button btnSignIn = (Button) findViewById(R.id.btnLogin);

        btnSignIn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String userName = editTextUserName.getText().toString();
                String password = editTextPassword.getText().toString();
                String storedPassword = loginDataBaseAdapter.getSinlgeEntry(userName);
                if (password.equals(storedPassword)) {
                    Toast.makeText(Login.this, "Prijava je uspješno izvršena!", Toast.LENGTH_LONG).show();
                    Intent main = new Intent(Login.this, BottomNavigationActivity.class);
                    startActivity(main);
                } else {
                    Toast.makeText(Login.this, "Pogrešno korisničko ime ili lozinka!", Toast.LENGTH_LONG).show();
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
