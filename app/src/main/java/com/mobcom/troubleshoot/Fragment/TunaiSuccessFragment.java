package com.mobcom.troubleshoot.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.databinding.FragmentTunaiSuccessBinding;

public class TunaiSuccessFragment extends Fragment {

  private FragmentTunaiSuccessBinding fragmentTunaiSuccessBinding;
  private NavController navController;

  public TunaiSuccessFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    fragmentTunaiSuccessBinding = FragmentTunaiSuccessBinding.inflate(inflater, container, false);
    return fragmentTunaiSuccessBinding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    navController = Navigation.findNavController(view);

    fragmentTunaiSuccessBinding.backHistory.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        navController.navigate(R.id.action_tunaiSuccessFragment_to_orderHistoryFragment);
      }
    });
  }
}