package com.mobcom.troubleshoot.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.adapters.CartListAdapter;
import com.mobcom.troubleshoot.databinding.FragmentCartBinding;
import com.mobcom.troubleshoot.models.CartItem;
import com.mobcom.troubleshoot.viewmodels.ServiceViewModel;

import java.util.List;

public class CartFragment extends Fragment implements CartListAdapter.CartInterface {

  private static final String TAG = "CartFragment";
  ServiceViewModel serviceViewModel;
  FragmentCartBinding fragmentCartBinding;

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
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    CartListAdapter cartListAdapter = new CartListAdapter(this);
    fragmentCartBinding.cartRecyclerView.setAdapter(cartListAdapter);
    fragmentCartBinding.cartRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

    serviceViewModel = new ViewModelProvider(requireActivity()).get(ServiceViewModel.class);
    serviceViewModel.getCart().observe(getViewLifecycleOwner(), new Observer<List<CartItem>>() {
      @Override
      public void onChanged(List<CartItem> cartItems) {
        cartListAdapter.submitList(cartItems);
      }
    });

    serviceViewModel.getTotalPrice().observe(getViewLifecycleOwner(), new Observer<Integer>() {
      @Override
      public void onChanged(Integer integer) {
        fragmentCartBinding.orderTotalTextView.setText(integer.toString());
      }
    });

  }

  @Override
  public void deleteItem(CartItem cartItem) {
    serviceViewModel.removeItemFromCart(cartItem);
  }

  @Override
  public void changeQuantity(CartItem cartItem, int quantity) {
    serviceViewModel.changeQuantity(cartItem, quantity);
  }
}