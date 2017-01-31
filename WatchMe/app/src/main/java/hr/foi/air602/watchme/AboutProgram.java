package hr.foi.air602.watchme;

import android.support.multidex.MultiDex;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


public class AboutProgram extends AppCompatActivity {

//Prikazivanje osnovnih podataka o aplikaciji
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MultiDex.install(AboutProgram.this);
        setContentView(R.layout.activity_o_programu);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
