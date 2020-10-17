package com.mobcom.troubleshoot.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mobcom.troubleshoot.Activity.KategoriPemesananActivity;
import com.mobcom.troubleshoot.Activity.LoginActivity;
import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.SessionManager;

public class ProfileFragment extends Fragment implements View.OnClickListener{
  private SessionManager sessionManager;
  private TextView tvNamaUser, tvTeleponUser, tvEmailUser, tvAlamatUser, tvLogout;
  private String firstName, lastName, telepon, email, alamat, fullName;

  public ProfileFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_profile, container, false);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    sessionManager = new SessionManager(getActivity());
    tvNamaUser = (TextView) view.findViewById(R.id.nama_user);
    tvTeleponUser = (TextView) view.findViewById(R.id.telepon_user);
    tvEmailUser = (TextView) view.findViewById(R.id.email_user);
    tvAlamatUser = (TextView) view.findViewById(R.id.alamat_user);
    tvLogout = (TextView) view.findViewById(R.id.tv_logout);
    firstName = sessionManager.getUserDetail().get(SessionManager.FIRST_NAME);
    lastName = sessionManager.getUserDetail().get(SessionManager.LAST_NAME);
    email = sessionManager.getUserDetail().get(SessionManager.EMAIL);
    alamat = sessionManager.getUserDetail().get(SessionManager.ALAMAT);
    telepon = sessionManager.getUserDetail().get(SessionManager.NOMOR_HP);

    fullName = firstName + " " +lastName;
    tvNamaUser.setText(fullName);
    tvEmailUser.setText(email);
    tvAlamatUser.setText(alamat);
    tvTeleponUser.setText(telepon);

    tvLogout.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.tv_logout:
        sessionManager.logoutSession();
        moveToLogin();
        break;
    }

  }

  private void moveToLogin() {
    Intent intent = new Intent(getActivity(), LoginActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
    startActivity(intent);
    getActivity().finish();
  }
}