package hr.foi.air602.watchme;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import hr.foi.air602.notification.configuration.Config;
import hr.foi.air602.notification.essentials.NotificationListener;
import hr.foi.air602.notification.util.NotificationUtils;
import hr.foi.air602.watchme.strategies.ScheduledNotificationStrategy;

/**
 * Created by markopc on 11/22/2016.
 */

public class UserSettings extends AppCompatActivity {

    private Button btnSaveSettings = null;
    private EditText editTextMinutes = null;
    private RadioButton radioSound, radioVibration;
    private NotificationListener notificationListener = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postavke);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        this.notificationListener = NotificationUtils.getInstance(this).getListener();

        this.radioSound = (RadioButton) findViewById(R.id.radioSound);
        this.radioVibration = (RadioButton) findViewById(R.id.radioVibration);

        this.btnSaveSettings = (Button) findViewById(R.id.btnSaveSettings);
        this.editTextMinutes = (EditText) findViewById(R.id.editTextMinutes);
        SharedPreferences sp = getSharedPreferences(Config.SHARED_PREF_OPTIONS, Context.MODE_PRIVATE);
        this.editTextMinutes.setText(sp.getInt("minutes", 20)+"");
        int flag = sp.getInt("radioSettings",1);
        this.radioSound.setChecked(flag == 1);
        this.radioVibration.setChecked(flag == 2);
        final UserSettings uSettings = this;

        this.btnSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sp = getSharedPreferences(Config.SHARED_PREF_OPTIONS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("minutes",Integer.parseInt(editTextMinutes.getText().toString()));
                editor.putInt("radioSettings",radioSound.isChecked() ? 1 : 2);
                editor.apply();
                ScheduledNotificationStrategy.getInstance(getApplicationContext()).setMinutes(Integer.parseInt(editTextMinutes.getText().toString()));

                if(radioSound.isChecked()) notificationListener.onNotificationSettingsChange(1);
                else notificationListener.onNotificationSettingsChange(2);

                uSettings.finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}