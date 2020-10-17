package com.mobcom.troubleshoot.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.mobcom.troubleshoot.adapters.ServiceListAdapter;
import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.models.CartItem;
import com.mobcom.troubleshoot.models.ServiceModel;
import com.mobcom.troubleshoot.viewmodels.ServiceViewModel;
import com.mobcom.troubleshoot.databinding.FragmentServiceBinding;

import java.util.List;

public class ServiceFragment extends Fragment implements ServiceListAdapter.ServiceInterface {
  private static final String TAG = "ServiceFragment";
  FragmentServiceBinding fragmentServiceBinding;
  private ServiceListAdapter serviceListAdapter;
  private ServiceViewModel serviceViewModel;
  private NavController navController;
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
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    serviceListAdapter = new ServiceListAdapter(this);
    fragmentServiceBinding.serviceRecyclerView.setAdapter(serviceListAdapter);
    serviceViewModel = new ViewModelProvider(requireActivity()).get(ServiceViewModel.class);
    fragmentServiceBinding.pbDataLayanan.setVisibility(View.VISIBLE);

    fragmentServiceBinding.scrollView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        fragmentServiceBinding.scrollView.setRefreshing(true);
        retrieveData();
        fragmentServiceBinding.scrollView.setRefreshing(false);
      }
    });

    serviceViewModel.getCart().observe(getViewLifecycleOwner(), new Observer<List<CartItem>>() {
      @Override
      public void onChanged(List<CartItem> cartItems) {
        fragmentServiceBinding.checkoutButton.setEnabled(cartItems.size() > 0);
        int quantity = 0;
        for (CartItem cartItem: cartItems) {
          quantity += cartItem.getQuantity();
        }
        cartQuantity = quantity;

        fragmentServiceBinding.TxtJumlahProduk.setText(String.valueOf(cartQuantity));
      }
    });

    navController = Navigation.findNavController(view);

    serviceViewModel.getTotalPrice().observe(getViewLifecycleOwner(), new Observer<Integer>() {
      @Override
      public void onChanged(Integer integer) {
        fragmentServiceBinding.TxtHarga.setText(integer.toString());
      }
    });
    fragmentServiceBinding.cart.setOnClickListener((v) -> {
      navController.navigate(R.id.action_serviceFragment_to_cartFragment);
    });

    fragmentServiceBinding.checkoutButton.setOnClickListener(v -> navController.navigate(R.id.action_serviceFragment_to_orderFragment));
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
  }

  @Override
  public void onItemClick(ServiceModel service) {

  }
}