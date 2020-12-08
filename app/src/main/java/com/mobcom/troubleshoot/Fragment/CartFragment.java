package com.mobcom.troubleshoot.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mobcom.troubleshoot.Helper;
import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.adapters.CartListAdapter;
import com.mobcom.troubleshoot.databinding.FragmentCartBinding;
import com.mobcom.troubleshoot.models.CartItem;
import com.mobcom.troubleshoot.viewmodels.ServiceViewModel;

public class CartFragment extends Fragment implements CartListAdapter.CartInterface {

  private static final String TAG = "CartFragment";
  private ServiceViewModel serviceViewModel;
  private FragmentCartBinding fragmentCartBinding;
  private int cartQuantity = 0;
  private NavController navController;
  private int kondisi = 1;

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

    // setup navcontroller
    navController = Navigation.findNavController(view);

    // setup recyclerview
    CartListAdapter cartListAdapter = new CartListAdapter(this);
    fragmentCartBinding.cartRecyclerView.setAdapter(cartListAdapter);

    // setup view model
    serviceViewModel = new ViewModelProvider(requireActivity()).get(ServiceViewModel.class);

    // setup helper
    Helper helper = new Helper();

    if (getArguments() != null){
      CartFragmentArgs args = CartFragmentArgs.fromBundle(getArguments());
      kondisi = args.getKondisi();
    }

    if (kondisi == 2) {
      fragmentCartBinding.CheckoutButton.setText("Kembali");
    }

    // submit cart item dan get jumlah produk di cart
    serviceViewModel.getCart().observe(getViewLifecycleOwner(), cartItems -> {

      if (cartItems.isEmpty()){
        fragmentCartBinding.cartEmpty.setVisibility(View.VISIBLE);
        fragmentCartBinding.cartRecyclerView.setVisibility(View.INVISIBLE);
      }
      else{
        cartListAdapter.submitList(cartItems);
        cartListAdapter.notifyDataSetChanged();
        fragmentCartBinding.cartEmpty.setVisibility(View.INVISIBLE);
        fragmentCartBinding.cartRecyclerView.setVisibility(View.VISIBLE);
      }

      fragmentCartBinding.CheckoutButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (cartItems.isEmpty()){
            Toast.makeText(getContext(), "Masukkan item layanan terlebih dahulu", Toast.LENGTH_SHORT).show();
          }
          else{
            if (kondisi == 1) {
              navController.navigate(R.id.action_cartFragment_to_orderFragment);
            }
            else{
              navController.popBackStack();
            }
          }
        }
      });


      //fragmentCartBinding.CheckoutButton.setEnabled(cartItems.size() > 0);
      int quantity = 0;
      for (CartItem cartItem: cartItems) {
        quantity += cartItem.getQuantity();
      }
      cartQuantity = quantity;

      fragmentCartBinding.TxtTotalProdukSeluruh.setText(String.valueOf(cartQuantity));
    });

    // get harga total di cart
    serviceViewModel.getTotalPrice().observe(getViewLifecycleOwner(), integer -> fragmentCartBinding.orderTotalTextView.setText(helper.formatRp(integer)));

    // back button
    fragmentCartBinding.backButton.setOnClickListener(v -> navController.popBackStack());

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