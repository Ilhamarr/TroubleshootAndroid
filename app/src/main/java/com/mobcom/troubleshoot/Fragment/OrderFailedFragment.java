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
import com.mobcom.troubleshoot.databinding.FragmentOrderFailedBinding;

public class OrderFailedFragment extends Fragment {

  private FragmentOrderFailedBinding fragmentOrderFailedBinding;
  private NavController navController;

  public OrderFailedFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    fragmentOrderFailedBinding = FragmentOrderFailedBinding.inflate(inflater, container, false);
    return fragmentOrderFailedBinding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    navController = Navigation.findNavController(view);

    fragmentOrderFailedBinding.TxtSplashPemesananGagal.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        navController.navigate(R.id.action_orderFailedFragment_to_homeFragment);
      }
    });
  }
}