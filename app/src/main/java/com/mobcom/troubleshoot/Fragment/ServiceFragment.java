package com.mobcom.troubleshoot.Fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.mobcom.troubleshoot.Activity.MainActivity;
import com.mobcom.troubleshoot.Helper;
import com.mobcom.troubleshoot.adapters.ServiceListAdapter;
import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.models.CartItem;
import com.mobcom.troubleshoot.models.ServiceModel;
import com.mobcom.troubleshoot.viewmodels.ServiceViewModel;
import com.mobcom.troubleshoot.databinding.FragmentServiceBinding;

import java.util.List;

public class ServiceFragment extends Fragment implements ServiceListAdapter.ServiceInterface {
  private static final String TAG = "ServiceFragment";
  private FragmentServiceBinding fragmentServiceBinding;
  private ServiceListAdapter serviceListAdapter;
  private ServiceViewModel serviceViewModel;
  private NavController navController;
  private int cartQuantity = 0;
  private Window window;
  private int kondisi = 1;

  public ServiceFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    fragmentServiceBinding = FragmentServiceBinding.inflate(inflater, container, false);
    return fragmentServiceBinding.getRoot();
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    if (Build.VERSION.SDK_INT >= 21){
      window = this.getActivity().getWindow();
      window.setStatusBarColor(this.getActivity().getResources().getColor(R.color.colorLightGrey));
      window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    // setup navcontroller
    navController = Navigation.findNavController(view);

    // setup recyclerview
    serviceListAdapter = new ServiceListAdapter(this);
    fragmentServiceBinding.serviceRecyclerView.setAdapter(serviceListAdapter);

    // setup view model
    serviceViewModel = new ViewModelProvider(requireActivity()).get(ServiceViewModel.class);

    // first time load fragment bakal muncul loading (progress bar)
    fragmentServiceBinding.pbDataLayanan.setVisibility(View.VISIBLE);

    // setup helper
    Helper helper = new Helper();

    // load list layanan
    retrieveData();

    if (getArguments() != null){
      ServiceFragmentArgs args = ServiceFragmentArgs.fromBundle(getArguments());
      kondisi = args.getKondisi();
    }

    if (kondisi == 2) {
      fragmentServiceBinding.checkoutButton.setText("Pesan Sekarang");
    }

    // get jumlah produk (didalem cart)
    serviceViewModel.getCart().observe(getViewLifecycleOwner(), new Observer<List<CartItem>>() {
      @Override
      public void onChanged(List<CartItem> cartItems) {
        //fragmentServiceBinding.checkoutButton.setEnabled(cartItems.size() > 0);

        fragmentServiceBinding.checkoutButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            if (cartItems.isEmpty()) {
              Toast.makeText(getContext(), "Masukkan item layanan terlebih dahulu", Toast.LENGTH_SHORT).show();
            }
            else {
              if (kondisi == 1) {
                navController.navigate(R.id.action_serviceFragment_to_orderFragment);
              }
              else{
                navController.popBackStack();
              }

            }
          }
        });

        int quantity = 0;
        for (CartItem cartItem: cartItems) {
          quantity += cartItem.getQuantity();
        }
        cartQuantity = quantity;

        fragmentServiceBinding.TxtJumlahProduk.setText(String.valueOf(cartQuantity));
      }
    });

    // get total price (didalem cart)
    serviceViewModel.getTotalPrice().observe(getViewLifecycleOwner(), new Observer<Integer>() {
      @Override
      public void onChanged(Integer integer) {

        fragmentServiceBinding.TxtHarga.setText(helper.formatRp(integer));
      }
    });

    // cart button
    fragmentServiceBinding.cart.setOnClickListener((v) -> {
      //navController.navigate(R.id.action_serviceFragment_to_cartFragment);
      //OrderConfirmationFragmentDirections.ActionOrderConfirmationFragmentToServiceFragment action = OrderConfirmationFragmentDirections.actionOrderConfirmationFragmentToServiceFragment();
      //ServiceFragmentDirections.ActionServiceFragmentToCartFragment action = ServiceFragmentDirections.actionServiceFragmentToCartFragment();
      ServiceFragmentDirections.ActionServiceFragmentToCartFragment action = ServiceFragmentDirections.actionServiceFragmentToCartFragment();
      action.setKondisi(kondisi);
      navController.navigate(action);
    });

    // checkout button (pesan sekarang)
    //fragmentServiceBinding.checkoutButton.setOnClickListener(v -> navController.navigate(R.id.action_serviceFragment_to_orderFragment));
  }

  @Override
  public void onResume() {
    super.onResume();
    retrieveData();
  }

  public void retrieveData(){
    serviceViewModel.getServices().observe(getViewLifecycleOwner(), new Observer<List<ServiceModel>>() {
      @Override
      public void onChanged(List<ServiceModel> service) {
        serviceListAdapter.submitList(service);
        serviceListAdapter.notifyDataSetChanged();
        fragmentServiceBinding.pbDataLayanan.setVisibility(View.INVISIBLE);
      }
    });
  }

  // add to cart ada disini
  @Override
  public void addItem(ServiceModel service) {
    //Log.d(TAG, "addItem: " + service.toString());
    boolean isAdded = serviceViewModel.addItemToCart(service);
    if (isAdded) {
      Snackbar.make(requireView(), "'" +service.getNama_kerusakan()+"'" + " ditambahkan ke keranjang.", Snackbar.LENGTH_LONG)
              .setAction("keranjang", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  navController.navigate(R.id.action_serviceFragment_to_cartFragment);
                }
              })
              .show();
    } else {
      Snackbar.make(requireView(), "Sudah ada di keranjang.", Snackbar.LENGTH_LONG)
              .show();
    }
  }
}