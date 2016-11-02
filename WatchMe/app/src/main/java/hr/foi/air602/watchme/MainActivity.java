package hr.foi.air602.watchme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import butterknife.ButterKnife;
import hr.foi.air602.watchme.helper.MockData;

public class MainActivity extends AppCompatActivity {

    private Button mBottonNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        FlowManager.init(new FlowConfig.Builder(this).build());

        MockData.writeAll();

        mBottonNavigation = (Button)findViewById(R.id.bottomNavigationButton);
        mBottonNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, BottomNavigationActivity.class);
                startActivity(i);
            }
        });
    }
}
