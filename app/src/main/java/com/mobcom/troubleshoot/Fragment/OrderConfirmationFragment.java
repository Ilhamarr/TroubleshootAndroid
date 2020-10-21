package com.mobcom.troubleshoot.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mobcom.troubleshoot.API.APIRequestData;
import com.mobcom.troubleshoot.API.RetroServer;
import com.mobcom.troubleshoot.Activity.RegisterActivity;
import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.SessionManager;
import com.mobcom.troubleshoot.adapters.CartListConfirmAdapter;
import com.mobcom.troubleshoot.databinding.FragmentOrderConfirmationBinding;
import com.mobcom.troubleshoot.models.CartItem;
import com.mobcom.troubleshoot.models.ResponseHeaderOrder;
import com.mobcom.troubleshoot.models.ResponseOrder;
import com.mobcom.troubleshoot.viewmodels.ServiceViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderConfirmationFragment extends Fragment {
  private FragmentOrderConfirmationBinding fragmentOrderConfirmationBinding;
  private String account_id, laptop_Id, mereklaptop, tipeLaptop, seriLaptop, detail, tanggal, jam, tempat, nama, email, phone, totalHarga;
  private ServiceViewModel serviceViewModel;
  private int cartQuantity = 0;
  private NavController navController;
  private SessionManager sessionManager;
  private APIRequestData ardData;

  public OrderConfirmationFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    fragmentOrderConfirmationBinding = FragmentOrderConfirmationBinding.inflate(inflater, container, false);
    return fragmentOrderConfirmationBinding.getRoot();
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    // setup recyclerview
    CartListConfirmAdapter cartListConfirmAdaptertAdapter = new CartListConfirmAdapter();
    fragmentOrderConfirmationBinding.rvKonfirmasiPemesanan.setAdapter(cartListConfirmAdaptertAdapter);

    // setup sessionmanager
    sessionManager = new SessionManager(getActivity());
    account_id = sessionManager.getUserDetail().get(SessionManager.ACCOUNT_ID);

    // setup navcontroller
    navController = Navigation.findNavController(view);

    // setup view model
    serviceViewModel = new ViewModelProvider(requireActivity()).get(ServiceViewModel.class);


    // get data from previous fragment
    if (getArguments() != null){
      OrderConfirmationFragmentArgs args = OrderConfirmationFragmentArgs.fromBundle(getArguments());
      laptop_Id = args.getIdLaptop();
      mereklaptop = args.getMerkLaptop();
      tipeLaptop = args.getSeriLaptop();
      nama = args.getNama();
      email = args.getEmail();
      phone = args.getPhone();
      seriLaptop = args.getSeriLaptop();
      detail = args.getDetail();
      tanggal = args.getTanggal();
      jam = args.getJam();
      tempat = args.getAlamat();
    }

    // view data
    fragmentOrderConfirmationBinding.TxtTanggal.setText(tanggal);
    fragmentOrderConfirmationBinding.TxtJam.setText(jam);
    fragmentOrderConfirmationBinding.TxtNamaLengkap.setText(nama);
    fragmentOrderConfirmationBinding.TxtEmail.setText(email);
    fragmentOrderConfirmationBinding.TxtNomorTelepom.setText(phone);
    fragmentOrderConfirmationBinding.TxtAlamat.setText(tempat);

    // get banyaknya item di cart
    serviceViewModel.getCart().observe(getViewLifecycleOwner(), cartItems -> {
      cartListConfirmAdaptertAdapter.submitList(cartItems);
      int quantity = 0;
      for (CartItem cartItem : cartItems) {
        quantity += cartItem.getQuantity();
      }
      cartQuantity = quantity;
      fragmentOrderConfirmationBinding.TxtTotalProdukSeluruh.setText(String.valueOf(cartQuantity));
    });

    // get total harga di cart
    serviceViewModel.getTotalPrice().observe(getViewLifecycleOwner(), integer -> {
      totalHarga = integer.toString();
      fragmentOrderConfirmationBinding.orderTotalTextView.setText(integer.toString());
      fragmentOrderConfirmationBinding.tvorderTotalTextView.setText(integer.toString());
    });

    // button edit form order
    fragmentOrderConfirmationBinding.ubahDetailPesanan.setOnClickListener(v -> navController.navigate(R.id.action_orderConfirmationFragment_to_orderFragment));

    // button edit cart
    fragmentOrderConfirmationBinding.ubahLayanan.setOnClickListener(v -> navController.navigate(R.id.action_orderConfirmationFragment_to_serviceFragment));

    // button back
    fragmentOrderConfirmationBinding.backButton.setOnClickListener(v -> navController.popBackStack());

    // button submit
    fragmentOrderConfirmationBinding.buatPesanan.setOnClickListener(v -> {
      String trackKey = tracking_key(account_id);
      //headerOrder(account_id, laptop_Id, detail,phone,tanggal,jam,tempat,seriLaptop,totalHarga,trackKey);
      ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
      Call<ResponseHeaderOrder> headerOrderCall = ardData.HeaderOrderResponse(account_id, nama, email, laptop_Id, detail, phone, tanggal, jam, tempat, seriLaptop, totalHarga, trackKey);
      headerOrderCall.enqueue(new Callback<ResponseHeaderOrder>() {
        @Override
        public void onResponse(Call<ResponseHeaderOrder> call, Response<ResponseHeaderOrder> response) {
          if (response.body() != null && response.isSuccessful()) {
            serviceViewModel.getCart().observe(getViewLifecycleOwner(), cartItems -> {
              for (CartItem cartItem : cartItems) {
                String kerusakanId = cartItem.getService().getKerusakan_id();
                String jumlah = String.valueOf(cartItem.getQuantity());
                String harga = String.valueOf(cartItem.getService().getBiaya());
                String totalHarga = String.valueOf(cartItem.getQuantity() * cartItem.getService().getBiaya());
                order(account_id,trackKey,kerusakanId,harga,jumlah,totalHarga);
              }
            });
            serviceViewModel.resetCart();
            navController.navigate(R.id.action_orderConfirmationFragment_to_orderSuccessFragment);
          } else {
            navController.navigate(R.id.action_orderConfirmationFragment_to_orderFailedFragment);
          }
        }

        @Override
        public void onFailure(Call<ResponseHeaderOrder> call, Throwable t) {
          navController.navigate(R.id.action_orderConfirmationFragment_to_orderFailedFragment);
        }
      });
    });

  }

  public void order(String accountId, String trackingKey, String kerusakanId, String harga, String jumlah, String totalHarga){
    ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
    Call<ResponseOrder> orderCall = ardData.OrderResponse(accountId,trackingKey,kerusakanId,harga,jumlah,totalHarga);
    orderCall.enqueue(new Callback<ResponseOrder>() {
      @Override
      public void onResponse(Call<ResponseOrder> call, Response<ResponseOrder> response) {
      }

      @Override
      public void onFailure(Call<ResponseOrder> call, Throwable t) {
      }
    });

  }

  public String tracking_key(String accountId){
    String randStr = getRandomString();
    // accountid + date dmY + merk + random
    SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy");
    Date date = new Date();
    String dmY = formatter.format(date);
    return accountId + dmY + randStr;
  }

  public String getRandomString(){
    int leftLimit = 97; // letter 'a'
    int rightLimit = 122; // letter 'z'
    int targetStringLength = 5;
    Random random = new Random();
    StringBuilder buffer = new StringBuilder(targetStringLength);
    for (int i = 0; i < targetStringLength; i++) {
      int randomLimitedInt = leftLimit + (int)
              (random.nextFloat() * (rightLimit - leftLimit + 1));
      buffer.append((char) randomLimitedInt);
    }
    String generatedString = buffer.toString();
    return generatedString.toUpperCase();
  }
}