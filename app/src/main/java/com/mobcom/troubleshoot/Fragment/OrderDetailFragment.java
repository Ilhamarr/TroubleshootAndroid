package com.mobcom.troubleshoot.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mobcom.troubleshoot.API.APIRequestData;
import com.mobcom.troubleshoot.API.RetroServer;
import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.SessionManager;
import com.mobcom.troubleshoot.adapters.DetailListAdapter;
import com.mobcom.troubleshoot.databinding.FragmentOrderDetailBinding;
import com.mobcom.troubleshoot.models.ResponseKonfirmasiBayar;
import com.mobcom.troubleshoot.viewmodels.HistoryViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailFragment extends Fragment {
  private static final String TAG = "OrderDetailFragment";
  private FragmentOrderDetailBinding fragmentOrderDetailBinding;
  private DetailListAdapter detailListAdapter;
  private HistoryViewModel historyViewModel;
  private String trackingKey, email;
  private SessionManager sessionManager;
  private int statusPayment, tracking_id;
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

    sessionManager = new SessionManager(getActivity());
    email = sessionManager.getUserDetail().get(SessionManager.EMAIL);

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
    tracking_id = historyViewModel.getHistory().getValue().getTrackingId();

    // get list order berdasarkan trackingkey
    historyViewModel.getItemOrders(trackingKey).observe(getViewLifecycleOwner(), items -> detailListAdapter.submitList(items));

    // button back
    fragmentOrderDetailBinding.backButton.setOnClickListener(v -> navController.popBackStack());

    if (statusPayment == 5){
      fragmentOrderDetailBinding.btnFalse.setVisibility(View.GONE);
      fragmentOrderDetailBinding.btnTrue.setVisibility(View.GONE);
    }

    else if (statusPayment == 3 && tracking_id == 4){
      // review pelayanan
      fragmentOrderDetailBinding.btnTrue.setText(R.string.review_pelayanan);
      fragmentOrderDetailBinding.btnTrue.setBackgroundColor(getResources().getColor(R.color.green));
      fragmentOrderDetailBinding.btnTrue.setOnClickListener(v -> goToUrl("https://g.page/troubleshootid/review?gm"));

      //klaim garansi
      fragmentOrderDetailBinding.btnFalse.setText(getString(R.string.klaim_garansi));
    }

    else if (statusPayment == 4 || statusPayment == 2){
      fragmentOrderDetailBinding.btnTrue.setVisibility(View.GONE);

      // batal pesanan
      fragmentOrderDetailBinding.btnFalse.setOnClickListener(v -> {
        // create alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Yakin ingin membatalkan pesanan ini?");
        builder.setPositiveButton("OK", (dialog, which) -> batalPesanan());
        builder.setNegativeButton("Cancel", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
      });
    }

    else {
      // metode pembayaran
      fragmentOrderDetailBinding.btnTrue.setOnClickListener(v -> navController.navigate(R.id.action_orderDetailFragment_to_paymentMethodFragment));

      // batal pesanan
      fragmentOrderDetailBinding.btnFalse.setOnClickListener(v -> {
        // create alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Yakin ingin membatalkan pesanan ini?");
        builder.setPositiveButton("OK", (dialog, which) -> batalPesanan());
        builder.setNegativeButton("Cancel", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
      });
    }
  }

  public void batalPesanan() {
    ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
    Call<ResponseKonfirmasiBayar> batal = ardData.batalpemesanan(trackingKey, email);
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

  private void goToUrl(String s) {
    Uri uri = Uri.parse(s);
    startActivity(new Intent(Intent.ACTION_VIEW, uri));
  }
}