package hr.foi.air602.watchme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import butterknife.ButterKnife;
import hr.foi.air602.watchme.helper.MockData;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        FlowManager.init(new FlowConfig.Builder(this).build());

        MockData.writeAll();
    }
}
