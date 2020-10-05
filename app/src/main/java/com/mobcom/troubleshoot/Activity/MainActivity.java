package com.mobcom.troubleshoot.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mobcom.troubleshoot.Fragment.HomeFragment;
import com.mobcom.troubleshoot.Fragment.OrderFragment;
import com.mobcom.troubleshoot.Fragment.OrderHistoryFragment;
import com.mobcom.troubleshoot.Fragment.ProfileFragment;
import com.mobcom.troubleshoot.Fragment.ServiceFragment;
import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.SessionManager;

public class MainActivity extends AppCompatActivity {

  SessionManager sessionManager;
  Intent intent;
  BottomNavigationView bottomNavigationView;
  FrameLayout frameLayout;
  NavController navController;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    //    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

    navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    //NavigationUI.setupActionBarWithNavController(this, navController);

    sessionManager = new SessionManager(MainActivity.this);
    if (!sessionManager.isLoggedin()) {
      moveToLogin();
    }

    bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
    bottomNavigationView.setOnNavigationItemSelectedListener(navigation);

    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HomeFragment()).commit();
  }

  private BottomNavigationView.OnNavigationItemSelectedListener navigation =
          new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

              Fragment selectedFragment = null;

              switch (item.getItemId()) {
                case R.id.menuHome:
                  selectedFragment = new HomeFragment();
                  break;
                case R.id.menuOrder:
                  selectedFragment = new ServiceFragment();
                  break;
                case R.id.menuOrderHistory:
                  selectedFragment = new OrderHistoryFragment();
                  break;
                case R.id.menuProfile:
                  selectedFragment = new ProfileFragment();
                  break;
              }
              getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFragment).commit();
              return true;
            }
          };


  private void moveToLogin() {
    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
    startActivity(intent);
    finish();
  }

}