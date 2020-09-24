package com.mobcom.troubleshoot.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.mobcom.troubleshoot.R;

public class MainActivity extends AppCompatActivity {
  private Button btnPesan;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //remove status bar backgroud (biar transparant)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
      Window w = getWindow();
      w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
              WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
    //end remove status bar backgroud
    btnPesan = findViewById(R.id.btnPesanSekarang);
    btnPesan.setOnClickListener(new View.OnClickListener(){
      @Override
      public void onClick(View view) {
        startActivity(new Intent(MainActivity.this, KategoriPemesananActivity.class));
      }
    });
  }
}