package com.mobcom.troubleshoot.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.databinding.FragmentBankBinding;
import com.mobcom.troubleshoot.viewmodels.HistoryViewModel;

public class BankFragment extends Fragment {
  private FragmentBankBinding fragmentBankBinding;
  private HistoryViewModel historyViewModel;
  private NavController navController;

  public BankFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    fragmentBankBinding = FragmentBankBinding.inflate(inflater, container, false);
    return fragmentBankBinding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    // setup navcontroller
    navController = Navigation.findNavController(view);

    // setup view model
    historyViewModel = new ViewModelProvider(requireActivity()).get(HistoryViewModel.class);
    fragmentBankBinding.setHistoryViewModel(historyViewModel);

    // tombol back
    fragmentBankBinding.backButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        navController.popBackStack();
      }
    });
  }
}