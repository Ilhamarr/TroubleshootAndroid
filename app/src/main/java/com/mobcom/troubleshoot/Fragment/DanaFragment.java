package com.mobcom.troubleshoot.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.databinding.FragmentDanaBinding;
import com.mobcom.troubleshoot.viewmodels.HistoryViewModel;

public class DanaFragment extends Fragment {

  private FragmentDanaBinding fragmentDanaBinding;
  private HistoryViewModel historyViewModel;
  private NavController navController;

  public DanaFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    fragmentDanaBinding = FragmentDanaBinding.inflate(inflater, container, false);
    return fragmentDanaBinding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    // setup view model
    historyViewModel = new ViewModelProvider(requireActivity()).get(HistoryViewModel.class);
    fragmentDanaBinding.setHistoryViewModel(historyViewModel);
  }
}