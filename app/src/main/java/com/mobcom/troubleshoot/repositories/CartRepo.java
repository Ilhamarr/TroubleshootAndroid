package com.mobcom.troubleshoot.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mobcom.troubleshoot.models.CartItem;
import com.mobcom.troubleshoot.models.ServiceModel;

import java.util.ArrayList;
import java.util.List;

public class CartRepo {

  private MutableLiveData<List<CartItem>> mutableCart = new MutableLiveData<>();

  public LiveData<List<CartItem>> getCart() {
    if (mutableCart.getValue() == null) {
      initCart();
    }
    return mutableCart;
  }

  public void initCart() {
    mutableCart.setValue(new ArrayList<CartItem>());
  }

  public boolean addItemToCart(ServiceModel service) {
    if (mutableCart.getValue() == null) {
      initCart();
    }

    List<CartItem> cartItemList = new ArrayList<>(mutableCart.getValue());

    CartItem cartItem = new CartItem(service, 1);
    cartItemList.add(cartItem);
    mutableCart.setValue(cartItemList);
    return true;
  }
}
