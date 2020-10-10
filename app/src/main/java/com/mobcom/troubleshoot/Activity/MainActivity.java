package com.mobcom.troubleshoot.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mobcom.troubleshoot.Fragment.HomeFragment;
import com.mobcom.troubleshoot.Fragment.OrderHistoryFragment;
import com.mobcom.troubleshoot.Fragment.ProfileFragment;
import com.mobcom.troubleshoot.Fragment.ServiceFragment;
import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.SessionManager;
import com.mobcom.troubleshoot.models.CartItem;
import com.mobcom.troubleshoot.viewmodels.ServiceViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "MainActivity";
  SessionManager sessionManager;
  Intent intent;
  BottomNavigationView bottomNavigationView;
  FrameLayout frameLayout;
  ServiceViewModel serviceViewModel;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    //    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

    sessionManager = new SessionManager(MainActivity.this);
    if (!sessionManager.isLoggedin()) {
      moveToLogin();
    }

    serviceViewModel = new ViewModelProvider(this).get(ServiceViewModel.class);
    serviceViewModel.getCart().observe(this, new Observer<List<CartItem>>() {
      @Override
      public void onChanged(List<CartItem> cartItems) {
        Log.d(TAG, "onChanged: " + cartItems.size());
      }
    });

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