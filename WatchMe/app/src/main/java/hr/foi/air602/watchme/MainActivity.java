package hr.foi.air602.watchme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hr.foi.air602.watchme.database.UserAdapter;
import hr.foi.air602.watchme.database.entities.User;

public class MainActivity extends AppCompatActivity{

    private Button btnPrijava, btnRegistracija;
    UserAdapter userAdapter = new UserAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPrijava = (Button)findViewById(R.id.btnLogin);
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


    public void signIn(View V){
        final EditText editTextUserName = (EditText) findViewById(R.id.kor_ime);
        final EditText editTextPassword = (EditText) findViewById(R.id.password);

        String userName = editTextUserName.getText().toString();
        String password = editTextPassword.getText().toString();

        boolean postoji = false;

        for(User user : userAdapter.getAllUsers()){
            if(user.username.equals(userName) && user.password.equals(password))
                postoji=true;
        }

        if (postoji==true) {
            //Toast.makeText(MainActivity.this, "Prijava je uspješno izvršena!", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(MainActivity.this, BottomNavigationActivity.class);
            startActivity(i);
        } else {
            Toast.makeText(MainActivity.this, "Pogrešno korisničko ime ili lozinka!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userAdapter.close();
    }

}
