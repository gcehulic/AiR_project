package hr.foi.air602.watchme;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import hr.foi.air602.database.entities.LoginDataBaseAdapter;
import hr.foi.air602.watchme.fragments.o_programu;

public class MainActivity extends AppCompatActivity{

   private Button btnPrijava, btnRegistracija;
    LoginDataBaseAdapter loginDataBaseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        FlowManager.init(new FlowConfig.Builder(this).build());

        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

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





    public void signIn(View V) {
        final EditText editTextUserName = (EditText) findViewById(R.id.kor_ime);
        final EditText editTextPassword = (EditText) findViewById(R.id.password);

        String userName = editTextUserName.getText().toString();
        String password = editTextPassword.getText().toString();
        String storedPassword = loginDataBaseAdapter.getSinlgeEntry(userName);
        if (password.equals(storedPassword)) {
            Toast.makeText(MainActivity.this, "Prijava je uspješno izvršena!", Toast.LENGTH_LONG).show();
            Intent i = new Intent(MainActivity.this, BottomNavigationActivity.class);
            startActivity(i);
        } else {
            Toast.makeText(MainActivity.this, "Pogrešno korisničko ime ili lozinka!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginDataBaseAdapter.close();
    }

}
