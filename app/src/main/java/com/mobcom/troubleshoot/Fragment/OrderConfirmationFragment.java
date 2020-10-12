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

import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.adapters.CartListConfirmAdapter;
import com.mobcom.troubleshoot.databinding.FragmentOrderConfirmationBinding;
import com.mobcom.troubleshoot.models.CartItem;
import com.mobcom.troubleshoot.viewmodels.ServiceViewModel;

import java.util.List;

public class OrderConfirmationFragment extends Fragment {
  FragmentOrderConfirmationBinding fragmentOrderConfirmationBinding;
  String laptop_Id, mereklaptop, tipeLaptop, seriLaptop, detail, tanggal, jam, tempat, nama, email, phone;
  ServiceViewModel serviceViewModel;
  private int cartQuantity = 0;
  private NavController navController;

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
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    CartListConfirmAdapter cartListConfirmAdaptertAdapter = new CartListConfirmAdapter();
    fragmentOrderConfirmationBinding.rvKonfirmasiPemesanan.setAdapter(cartListConfirmAdaptertAdapter);
    navController = Navigation.findNavController(view);
    serviceViewModel = new ViewModelProvider(requireActivity()).get(ServiceViewModel.class);

    //get data from previous fragment
    if (getArguments() != null){
      OrderConfirmationFragmentArgs args = OrderConfirmationFragmentArgs.fromBundle(getArguments());
      laptop_Id = args.getIdLaptop();
      mereklaptop = args.getMerkLaptop();
      tipeLaptop = args.getTipeLaptop();
      nama = args.getNama();
      email = args.getEmail();
      phone = args.getPhone();

      seriLaptop = args.getSeriLaptop();
      detail = args.getDetail();
      tanggal = args.getTanggal();
      jam = args.getJam();
      tempat = args.getAlamat();
    }
    //end get data

    //view data
    fragmentOrderConfirmationBinding.TxtTanggal.setText(tanggal);
    fragmentOrderConfirmationBinding.TxtJam.setText(jam);
    fragmentOrderConfirmationBinding.TxtNamaLengkap.setText(nama);
    fragmentOrderConfirmationBinding.TxtEmail.setText(email);
    fragmentOrderConfirmationBinding.TxtNomorTelepom.setText(phone);
    fragmentOrderConfirmationBinding.TxtAlamat.setText(tempat);
    //end view data

    //get banyaknya item di cart dan total harga
    serviceViewModel.getCart().observe(getViewLifecycleOwner(), cartItems -> {
      cartListConfirmAdaptertAdapter.submitList(cartItems);
      int quantity = 0;
      for (CartItem cartItem : cartItems) {
        quantity += cartItem.getQuantity();
      }
      cartQuantity = quantity;
      fragmentOrderConfirmationBinding.TxtTotalProdukSeluruh.setText(String.valueOf(cartQuantity));
    });
    serviceViewModel.getTotalPrice().observe(getViewLifecycleOwner(), new Observer<Integer>() {
      @Override
      public void onChanged(Integer integer) {
        fragmentOrderConfirmationBinding.orderTotalTextView.setText(integer.toString());
        fragmentOrderConfirmationBinding.tvorderTotalTextView.setText(integer.toString());
      }
    });
    //end

    fragmentOrderConfirmationBinding.ubahDetailPesanan.setOnClickListener(v -> navController.navigate(R.id.action_orderConfirmationFragment_to_orderFragment));

    fragmentOrderConfirmationBinding.ubahLayanan.setOnClickListener(v -> navController.navigate(R.id.action_orderConfirmationFragment_to_serviceFragment));

    fragmentOrderConfirmationBinding.buatPesanan.setOnClickListener(v -> {

    });


  }
}