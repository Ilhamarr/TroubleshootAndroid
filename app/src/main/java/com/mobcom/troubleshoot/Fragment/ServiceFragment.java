package com.mobcom.troubleshoot.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.mobcom.troubleshoot.adapters.ServiceListAdapter;
import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.models.CartItem;
import com.mobcom.troubleshoot.models.ServiceModel;
import com.mobcom.troubleshoot.viewmodels.ServiceViewModel;
import com.mobcom.troubleshoot.databinding.FragmentServiceBinding;

import java.util.List;

public class ServiceFragment extends Fragment implements ServiceListAdapter.ServiceInterface, View.OnClickListener {

  private static final String TAG = "ServiceFragment";
  FragmentServiceBinding fragmentServiceBinding;
  private ServiceListAdapter serviceListAdapter;
  private ServiceViewModel serviceViewModel;
  Button btnShowCart;
  private int cartQuantity = 0;

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
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    serviceListAdapter = new ServiceListAdapter(this);
    fragmentServiceBinding.serviceRecyclerView.setAdapter(serviceListAdapter);

    serviceViewModel = new ViewModelProvider(requireActivity()).get(ServiceViewModel.class);
    serviceViewModel.getServices().observe(getViewLifecycleOwner(), new Observer<List<ServiceModel>>() {
      @Override
      public void onChanged(List<ServiceModel> service) {
        serviceListAdapter.submitList(service);
      }
    });
    serviceViewModel.getCart().observe(getViewLifecycleOwner(), new Observer<List<CartItem>>() {
      @Override
      public void onChanged(List<CartItem> cartItems) {
        fragmentServiceBinding.cart.setEnabled(cartItems.size() > 0);
        int quantity = 0;
        for (CartItem cartItem: cartItems) {
          quantity += cartItem.getQuantity();
        }
        cartQuantity = quantity;

        fragmentServiceBinding.TxtJumlahProduk.setText(String.valueOf(cartQuantity));
      }
    });

    serviceViewModel.getTotalPrice().observe(getViewLifecycleOwner(), new Observer<Integer>() {
      @Override
      public void onChanged(Integer integer) {
        fragmentServiceBinding.TxtHarga.setText(integer.toString());
      }
    });
    btnShowCart = (Button) view.findViewById(R.id.cart);
    btnShowCart.setOnClickListener(this);
  }

  // add to cart ada disini
  @Override
  public void addItem(ServiceModel service) {
    //Log.d(TAG, "addItem: " + service.toString());
    boolean isAdded = serviceViewModel.addItemToCart(service);
  }

  @Override
  public void onItemClick(ServiceModel service) {

  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.cart:
        Fragment fragment = new CartFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        break;
    }
  }
}