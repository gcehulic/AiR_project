package hr.foi.air602.watchme;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import hr.foi.air602.notification.configuration.Config;
import hr.foi.air602.notification.essentials.NotificationOptions;
import hr.foi.air602.notification.service.MyFirebaseMessagingService;
import hr.foi.air602.notification.util.NotificationUtils;
import hr.foi.air602.watchme.notification_options.SoundNotification;
import hr.foi.air602.watchme.notification_options.SoundVibrationNotification;
import hr.foi.air602.watchme.notification_options.VibrationNotification;
import hr.foi.air602.watchme.strategies.ScheduledNotificationStrategy;

/**
 * Created by markopc on 11/22/2016.
 */

public class UserSettings extends AppCompatActivity {

    private Button btnSaveSettings = null;
    private CheckBox checkBoxSound = null;
    private CheckBox checkBoxVibration = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postavke);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        this.btnSaveSettings = (Button) findViewById(R.id.btnSaveSettings);
        this.checkBoxSound = (CheckBox) findViewById(R.id.checkBoxSound);
        this.checkBoxVibration = (CheckBox) findViewById(R.id.checkBoxVibration);

        SharedPreferences sp = getSharedPreferences(Config.SHARED_PREF_OPTIONS, Context.MODE_PRIVATE);

        //this.scheduledNotificationStrategy = ScheduledNotificationStrategy.getInstance(getApplicationContext());
        MyFirebaseMessagingService myFirebaseMessagingService = MyFirebaseMessagingService.getInstance();

        this.checkBoxVibration.setChecked(sp.getBoolean("vibration", false));
        this.checkBoxSound.setChecked(sp.getBoolean("sound", true));

        final UserSettings uSettings = this;

        this.btnSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*NotificationSettings notificationSettings = new NotificationSettings();
                notificationSettings.setSound(checkBoxZvuk.isChecked());
                notificationSettings.setVibration(checkBoxVibration.isChecked());*/
                SharedPreferences sp = getSharedPreferences(Config.SHARED_PREF_OPTIONS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("sound",checkBoxSound.isChecked());
                editor.putBoolean("vibration",checkBoxVibration.isChecked());

                editor.apply();

                NotificationOptions notificationOptions = null;
                if(checkBoxSound.isChecked() && checkBoxVibration.isChecked()) notificationOptions = new SoundVibrationNotification();
                else if(checkBoxSound.isChecked()) notificationOptions = new SoundNotification();
                else if(checkBoxVibration.isChecked()) notificationOptions = new VibrationNotification();

                NotificationUtils.applyNotificationSettings(notificationOptions);

                MyFirebaseMessagingService.getInstance().schedulingNotifs(ScheduledNotificationStrategy.getInstance(getApplicationContext()));
                uSettings.finish();

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}