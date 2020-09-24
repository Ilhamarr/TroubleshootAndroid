package com.mobcom.troubleshoot.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.mobcom.troubleshoot.R;

public class SplashActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    //remove status bar backgroud (biar transparant)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
      Window w = getWindow();
      w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
              WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
    //end remove status bar backgroud

    // add handler start
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
      }
    }, 3000);

    // end add handler start
  }
}