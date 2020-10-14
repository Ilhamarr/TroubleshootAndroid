package com.mobcom.troubleshoot.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.mobcom.troubleshoot.Activity.KategoriPemesananActivity;
import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.adapters.CartListAdapter;
import com.mobcom.troubleshoot.databinding.FragmentCartBinding;
import com.mobcom.troubleshoot.models.CartItem;
import com.mobcom.troubleshoot.models.LaptopModel;
import com.mobcom.troubleshoot.models.ResponseLaptopModel;
import com.mobcom.troubleshoot.viewmodels.ServiceViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment implements CartListAdapter.CartInterface {

  private static final String TAG = "CartFragment";
  ServiceViewModel serviceViewModel;
  FragmentCartBinding fragmentCartBinding;
  private int cartQuantity = 0;
  private NavController navController;

  public CartFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    fragmentCartBinding = FragmentCartBinding.inflate(inflater, container, false);
    return fragmentCartBinding.getRoot();
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    CartListAdapter cartListAdapter = new CartListAdapter(this);
    fragmentCartBinding.cartRecyclerView.setAdapter(cartListAdapter);
    navController = Navigation.findNavController(view);
    serviceViewModel = new ViewModelProvider(requireActivity()).get(ServiceViewModel.class);
    serviceViewModel.getCart().observe(getViewLifecycleOwner(), cartItems -> {
      cartListAdapter.submitList(cartItems);
      fragmentCartBinding.CheckoutButton.setEnabled(cartItems.size() > 0);
      int quantity = 0;
      for (CartItem cartItem: cartItems) {
        quantity += cartItem.getQuantity();
      }
      cartQuantity = quantity;

      fragmentCartBinding.TxtTotalProdukSeluruh.setText(String.valueOf(cartQuantity));
    });

    serviceViewModel.getTotalPrice().observe(getViewLifecycleOwner(), integer -> fragmentCartBinding.orderTotalTextView.setText(integer.toString()));

    fragmentCartBinding.CheckoutButton.setOnClickListener(v -> navController.navigate(R.id.action_cartFragment_to_orderFragment));

  }

  @Override
  public void deleteItem(CartItem cartItem) {
    serviceViewModel.removeItemFromCart(cartItem);
  }

  @Override
  public void changeQuantity(CartItem cartItem, int quantity) {
    serviceViewModel.changeQuantity(cartItem, quantity);
  }

  @Override
  public void addQuantity(CartItem cartItem) {
    serviceViewModel.addQuantity(cartItem);
  }

  @Override
  public void decreaseQuantity(CartItem cartItem) {
    serviceViewModel.decreaseQuantity(cartItem);
  }

}