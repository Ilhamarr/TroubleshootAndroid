package com.mobcom.troubleshoot.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.SessionManager;

public class ProfileFragment extends Fragment {
  private SessionManager sessionManager;
  private TextView tvNamaUser, tvTeleponUser, tvEmailUser, tvAlamatUser;
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
    firstName = sessionManager.getUserDetail().get(SessionManager.FIRST_NAME);
    lastName = sessionManager.getUserDetail().get(SessionManager.LAST_NAME);
    email = sessionManager.getUserDetail().get(SessionManager.EMAIL);
    alamat = sessionManager.getUserDetail().get(SessionManager.ALAMAT);
    fullName = firstName + " " +lastName;

    tvNamaUser.setText(fullName);
    tvEmailUser.setText(email);
    tvAlamatUser.setText(alamat);
    Toast.makeText(getActivity(), email, Toast.LENGTH_SHORT).show();
  }
}