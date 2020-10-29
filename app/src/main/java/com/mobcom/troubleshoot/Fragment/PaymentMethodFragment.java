package com.mobcom.troubleshoot.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mobcom.troubleshoot.API.APIRequestData;
import com.mobcom.troubleshoot.API.RetroServer;
import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.databinding.FragmentPaymentMethodBinding;
import com.mobcom.troubleshoot.models.ResponseKonfirmasiBayar;
import com.mobcom.troubleshoot.viewmodels.HistoryViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentMethodFragment extends Fragment {

  private static final String TAG = "PaymentMethodFragment";
  private FragmentPaymentMethodBinding fragmentPaymentMethodBinding;
  private HistoryViewModel historyViewModel;
  private NavController navController;
  private String trackingKey;
  private APIRequestData ardData;

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
    trackingKey = historyViewModel.getHistory().getValue().getTrackingKey();

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
          // masuk database
          konfirmasiCod();

        } else if (selectedId == fragmentPaymentMethodBinding.bank.getId()) {
          navController.navigate(R.id.action_paymentMethodFragment_to_bankFragment);

        } else {
          navController.navigate(R.id.action_paymentMethodFragment_to_danaFragment);

        }

      }
    });

  }

  public void konfirmasiCod(){
    ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
    Call<ResponseKonfirmasiBayar> konfirmasiCod = ardData.konfirmasiCod(trackingKey);
    konfirmasiCod.enqueue(new Callback<ResponseKonfirmasiBayar>() {
      @Override
      public void onResponse(Call<ResponseKonfirmasiBayar> call, Response<ResponseKonfirmasiBayar> response) {
        if (response.body() != null && response.isSuccessful()) {
          navController.navigate(R.id.action_paymentMethodFragment_to_tunaiSuccessFragment);
          Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onFailure(Call<ResponseKonfirmasiBayar> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
      }
    });
  }
}