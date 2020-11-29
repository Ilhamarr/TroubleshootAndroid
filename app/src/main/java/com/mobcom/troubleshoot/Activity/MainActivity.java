package com.mobcom.troubleshoot.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
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

    bottomNavigationView.setOnNavigationItemReselectedListener(Item -> {
      bottomNavigationView.findViewById(Item.getItemId()).setEnabled(false);
    });

  }

  @Override
  public void onBackPressed() {
    bottomNavigationView.getMenu().getItem(0).setChecked(true);
    super.onBackPressed();
  }

  private void moveToLogin() {
    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
    startActivity(intent);
    finish();
  }

}