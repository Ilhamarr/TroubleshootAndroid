package com.mobcom.troubleshoot.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mobcom.troubleshoot.Activity.KategoriPemesananActivity;
import com.mobcom.troubleshoot.Activity.LoginActivity;
import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.SessionManager;

public class HomeFragment extends Fragment implements View.OnClickListener {
  private SessionManager sessionManager;
  private Button btnPesan;
  private TextView tvFirstName;
  private String firstName;
  private Intent intent;

  public HomeFragment() {
    // Required empty public constructor

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_home, container, false);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    sessionManager = new SessionManager(getActivity());
    btnPesan = (Button) view.findViewById(R.id.btnPesanSekarang);
    tvFirstName = (TextView) view.findViewById(R.id.tvfirstNameHome);
    firstName = sessionManager.getUserDetail().get(SessionManager.FIRST_NAME);
    tvFirstName.setText(firstName);
    btnPesan.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btnPesanSekarang:
        intent = new Intent(getActivity(), KategoriPemesananActivity.class);
        startActivity(intent);
        break;
    }
  }
}
