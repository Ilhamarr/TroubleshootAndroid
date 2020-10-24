package com.mobcom.troubleshoot.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.SessionManager;
import com.mobcom.troubleshoot.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
  private SessionManager sessionManager;
  private FragmentHomeBinding fragmentHomeBinding;
  private String firstName;
  private NavController navController;

  public HomeFragment() {
    // Required empty public constructor

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);
    return fragmentHomeBinding.getRoot();
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    // setup sessionmanager
    sessionManager = new SessionManager(getActivity());
    firstName = sessionManager.getUserDetail().get(SessionManager.FIRST_NAME);
    fragmentHomeBinding.tvfirstNameHome.setText(firstName);

    // setup navcontroller
    navController = Navigation.findNavController(view);

    // button pesan sekarang
    fragmentHomeBinding.btnPesanSekarang.setOnClickListener((v) -> {
      navController.navigate(R.id.action_homeFragment_to_serviceFragment);
    });

    // button konsultasi
    fragmentHomeBinding.btnKonsul.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        goToUrl("https://www.instagram.com/troubleshoot.id/");
      }
    });
  }

  private void goToUrl(String s) {
    Uri uri = Uri.parse(s);
    startActivity(new Intent(Intent.ACTION_VIEW,uri));
  }

}
