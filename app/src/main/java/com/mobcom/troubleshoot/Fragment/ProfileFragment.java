package com.mobcom.troubleshoot.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobcom.troubleshoot.Activity.LoginActivity;
import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.SessionManager;
import com.mobcom.troubleshoot.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
  private SessionManager sessionManager;
  FragmentProfileBinding fragmentProfileBinding;
  private String firstName, lastName, telepon, email, alamat, fullName;

  public ProfileFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    fragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false);
    return fragmentProfileBinding.getRoot();
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    sessionManager = new SessionManager(getActivity());
    firstName = sessionManager.getUserDetail().get(SessionManager.FIRST_NAME);
    lastName = sessionManager.getUserDetail().get(SessionManager.LAST_NAME);
    email = sessionManager.getUserDetail().get(SessionManager.EMAIL);
    alamat = sessionManager.getUserDetail().get(SessionManager.ALAMAT);
    telepon = sessionManager.getUserDetail().get(SessionManager.NOMOR_HP);

    fullName = firstName + " " +lastName;
    fragmentProfileBinding.namaUser.setText(fullName);
    fragmentProfileBinding.emailUser.setText(email);
    fragmentProfileBinding.alamatUser.setText(alamat);
    fragmentProfileBinding.teleponUser.setText(telepon);

    fragmentProfileBinding.tvLogout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        sessionManager.logoutSession();
        moveToLogin();
      }
    });
  }

  private void moveToLogin() {
    Intent intent = new Intent(getActivity(), LoginActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
    startActivity(intent);
    getActivity().finish();
  }
}