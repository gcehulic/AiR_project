package hr.foi.air602.watchme;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import hr.foi.air602.notification.configuration.Config;
import hr.foi.air602.notification.essentials.NotificationOptions;
import hr.foi.air602.notification.util.NotificationUtils;
import hr.foi.air602.notification.notification_options.SilentNotification;
import hr.foi.air602.notification.notification_options.SoundNotification;
import hr.foi.air602.notification.notification_options.SoundVibrationNotification;
import hr.foi.air602.notification.notification_options.VibrationNotification;
import hr.foi.air602.watchme.strategies.ScheduledNotificationStrategy;

/**
 * Created by markopc on 11/22/2016.
 */

// Korisnikove postavke o zvuku, vibraciji i koliko prije dolazi obavijest
public class UserSettings extends AppCompatActivity {

    private Button btnSaveSettings = null;
    private CheckBox checkBoxSound = null;
    private CheckBox checkBoxVibration = null;
    private EditText editTextMinutes = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postavke);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        this.btnSaveSettings = (Button) findViewById(R.id.btnSaveSettings);
        this.checkBoxSound = (CheckBox) findViewById(R.id.checkBoxSound);
        this.checkBoxVibration = (CheckBox) findViewById(R.id.checkBoxVibration);
        this.editTextMinutes = (EditText) findViewById(R.id.editTextMinutes);
        SharedPreferences sp = getSharedPreferences(Config.SHARED_PREF_OPTIONS, Context.MODE_PRIVATE);
        this.checkBoxVibration.setChecked(sp.getBoolean("vibration", false));
        this.checkBoxSound.setChecked(sp.getBoolean("sound", true));
        this.editTextMinutes.setText(sp.getInt("minutes", 20)+"");
        final UserSettings uSettings = this;

        this.btnSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sp = getSharedPreferences(Config.SHARED_PREF_OPTIONS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("sound",checkBoxSound.isChecked());
                editor.putBoolean("vibration",checkBoxVibration.isChecked());
                editor.putInt("minutes",Integer.parseInt(editTextMinutes.getText().toString()));
                editor.apply();
                ScheduledNotificationStrategy.getInstance(getApplicationContext()).setMinutes(Integer.parseInt(editTextMinutes.getText().toString()));
                uSettings.finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}