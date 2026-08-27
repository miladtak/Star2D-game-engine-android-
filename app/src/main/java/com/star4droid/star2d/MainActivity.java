package com.star4droid.star2d;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import com.star4droid.star2d.Helpers.EngineSettings;
import com.star4droid.star2d.editor.TestApp;
import com.star4droid.star2d.evo.R;

public class MainActivity extends AppCompatActivity {
    private final Handler handler = new Handler(Looper.getMainLooper());
    private boolean opened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setLanguage(this);
        setContentView(R.layout.activity_main);
        EngineSettings.init(this);
        
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            checkPerms(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        
        handler.postDelayed(this::open, 1200);
    }
    
    private synchronized void open(){
        if (opened || isFinishing() || isDestroyed()) {
            return;
        }
        opened = true;
        
        Intent i = new Intent(MainActivity.this, com.star4droid.star2d.EditorActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
        finish();
    }
    
    public boolean checkPerms(final String... perms) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for(String p : perms) {
                if (ContextCompat.checkSelfPermission(this, p) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(MainActivity.this, perms, 1000);
                    return false;
                }
            }
        }
        return true;
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}