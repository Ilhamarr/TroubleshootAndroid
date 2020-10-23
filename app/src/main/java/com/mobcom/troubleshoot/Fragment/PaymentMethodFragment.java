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
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.databinding.FragmentPaymentMethodBinding;
import com.mobcom.troubleshoot.viewmodels.HistoryViewModel;

public class PaymentMethodFragment extends Fragment {
  private FragmentPaymentMethodBinding fragmentPaymentMethodBinding;
  private HistoryViewModel historyViewModel;
  private NavController navController;

  public PaymentMethodFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    fragmentPaymentMethodBinding = FragmentPaymentMethodBinding.inflate(inflater, container, false);
    return fragmentPaymentMethodBinding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    // setup navcontroller
    navController = Navigation.findNavController(view);

    // setup view model
    historyViewModel = new ViewModelProvider(requireActivity()).get(HistoryViewModel.class);
    fragmentPaymentMethodBinding.setHistoryViewModel(historyViewModel);

    // back button
    fragmentPaymentMethodBinding.backButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        navController.popBackStack();
      }
    });

    // button bayar
    fragmentPaymentMethodBinding.bayar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        int selectedId = fragmentPaymentMethodBinding.rgPaymentMethod.getCheckedRadioButtonId();
        if (selectedId == fragmentPaymentMethodBinding.tunai.getId()) {

        } else if (selectedId == fragmentPaymentMethodBinding.bank.getId()) {
          navController.navigate(R.id.action_paymentMethodFragment_to_bankFragment);

        } else {
          navController.navigate(R.id.action_paymentMethodFragment_to_danaFragment);

        }

      }
    });

  }
}