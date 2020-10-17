package com.mobcom.troubleshoot.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mobcom.troubleshoot.Activity.KategoriPemesananActivity;
import com.mobcom.troubleshoot.Activity.LoginActivity;
import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.SessionManager;
import com.mobcom.troubleshoot.databinding.FragmentHomeBinding;
import com.mobcom.troubleshoot.databinding.FragmentServiceBinding;

public class HomeFragment extends Fragment {
  private SessionManager sessionManager;
  FragmentHomeBinding fragmentHomeBinding;
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
    sessionManager = new SessionManager(getActivity());
    firstName = sessionManager.getUserDetail().get(SessionManager.FIRST_NAME);
    fragmentHomeBinding.tvfirstNameHome.setText(firstName);

    navController = Navigation.findNavController(view);
    fragmentHomeBinding.btnPesanSekarang.setOnClickListener((v) -> {
      navController.navigate(R.id.action_homeFragment_to_serviceFragment);
    });
  }

}
