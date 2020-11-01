package com.mobcom.troubleshoot.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mobcom.troubleshoot.API.APIRequestData;
import com.mobcom.troubleshoot.API.RetroServer;
import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.adapters.DetailListAdapter;
import com.mobcom.troubleshoot.adapters.HistoryListAdapter;
import com.mobcom.troubleshoot.databinding.FragmentOrderDetailBinding;
import com.mobcom.troubleshoot.models.ItemOrderModel;
import com.mobcom.troubleshoot.models.ResponseKonfirmasiBayar;
import com.mobcom.troubleshoot.viewmodels.HistoryViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailFragment extends Fragment {
  private static final String TAG = "OrderDetailFragment";
  private FragmentOrderDetailBinding fragmentOrderDetailBinding;
  private DetailListAdapter detailListAdapter;
  private HistoryViewModel historyViewModel;
  private String trackingKey;
  private int statusPayment;
  private NavController navController;
  private APIRequestData ardData;

  public OrderDetailFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    fragmentOrderDetailBinding = FragmentOrderDetailBinding.inflate(inflater, container, false);
    return fragmentOrderDetailBinding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    // setup navcontroller
    navController = Navigation.findNavController(view);

    // setup view model
    historyViewModel = new ViewModelProvider(requireActivity()).get(HistoryViewModel.class);
    fragmentOrderDetailBinding.setHistoryViewModel(historyViewModel);
    statusPayment = historyViewModel.getHistory().getValue().getStatusPayment();


    // setup recyclerview
    detailListAdapter = new DetailListAdapter();
    fragmentOrderDetailBinding.rvItemOrderDetail.setAdapter(detailListAdapter);
    trackingKey = historyViewModel.getHistory().getValue().getTrackingKey();

    // get list order berdasarkan trackingkey
    historyViewModel.getItemOrders(trackingKey).observe(getViewLifecycleOwner(), items -> detailListAdapter.submitList(items));

    // button back
    fragmentOrderDetailBinding.backButton.setOnClickListener(v -> navController.popBackStack());

    // button bayar sekaang (to payment method)
    if(statusPayment >= 2 ) {
      fragmentOrderDetailBinding.btnBayarsekarang.setVisibility(View.GONE);
      fragmentOrderDetailBinding.btnBatalkanpesanan.setVisibility(View.GONE);
    }

//    if(statusPayment == 4){
//      fragmentOrderDetailBinding.btnBayarsekarang.setVisibility(View.GONE);
//    }
    if(statusPayment == 1){
      fragmentOrderDetailBinding.btnBayarsekarang.setVisibility(View.VISIBLE);
    }

    // button menuju metode pembayaran
    fragmentOrderDetailBinding.btnBayarsekarang.setOnClickListener(v -> navController.navigate(R.id.action_orderDetailFragment_to_paymentMethodFragment));

    // button batal
    //fragmentOrderDetailBinding.btnBatalkanpesanan.setOnClickListener(v -> batalPesanan());
    fragmentOrderDetailBinding.btnBatalkanpesanan.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // create alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Yakin ingin membatalkan pesanan ini?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            batalPesanan();
          }
        });
        builder.setNegativeButton("Cancel", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
      }
    });

  }

  public void batalPesanan(){
    ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
    Call<ResponseKonfirmasiBayar> batal = ardData.batalpemesanan(trackingKey);
    batal.enqueue(new Callback<ResponseKonfirmasiBayar>() {
      @Override
      public void onResponse(Call<ResponseKonfirmasiBayar> call, Response<ResponseKonfirmasiBayar> response) {
        if (response.body() != null && response.isSuccessful()) {
          navController.navigate(R.id.action_orderDetailFragment_to_orderHistoryFragment);
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