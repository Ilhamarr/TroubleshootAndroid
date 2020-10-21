package com.mobcom.troubleshoot.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
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
  private SessionManager sessionManager;
  private BottomNavigationView bottomNavigationView;
  private ServiceViewModel serviceViewModel;
  private NavController navController;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // cek apakah user login apa engga
    // klo belom login, bakal di lempar ke login activity
    sessionManager = new SessionManager(MainActivity.this);
    if (!sessionManager.isLoggedin()) {
      moveToLogin();
    }

    // cek cart item
    serviceViewModel = new ViewModelProvider(this).get(ServiceViewModel.class);
    serviceViewModel.getCart().observe(this, new Observer<List<CartItem>>() {
      @Override
      public void onChanged(List<CartItem> cartItems) {
        Log.d(TAG, "onChanged: " + cartItems.size());
      }
    });

    // bottom navigasi
    bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
    navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    NavigationUI.setupWithNavController(bottomNavigationView, navController);
  }

  private void moveToLogin() {
    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
    startActivity(intent);
    finish();
  }

  public void updateStatusBarColor(String color){// Color must be in hexadecimal fromat
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      Window window = getWindow();
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.setStatusBarColor(Color.parseColor(color));
    }
  }

}