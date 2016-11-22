package hr.foi.air602.watchme;

import android.app.DialogFragment;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import hr.foi.air602.watchme.R;


public class O_programu extends AppCompatActivity {

// to je ta clasa? je i ova postavke

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MultiDex.install(O_programu.this);
        setContentView(R.layout.activity_o_programu);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
