package com.mobcom.troubleshoot.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mobcom.troubleshoot.models.CartItem;
import com.mobcom.troubleshoot.models.ServiceModel;
import com.mobcom.troubleshoot.repositories.CartRepo;
import com.mobcom.troubleshoot.repositories.ServiceRepo;

import java.util.List;

public class ServiceViewModel extends ViewModel {

  ServiceRepo serviceRepo = new ServiceRepo();
  CartRepo cartRepo = new CartRepo();

  MutableLiveData<ServiceModel> mutableService = new MutableLiveData<>();

  public LiveData<List<ServiceModel>> getServices() {
    return serviceRepo.getService();
  }

  public void setService(ServiceModel service) {
    mutableService.setValue(service);
  }

  public LiveData<ServiceModel> getService() {
    return mutableService;
  }

  public LiveData<List<CartItem>> getCart() {
    return cartRepo.getCart();
  }

  public boolean addItemToCart(ServiceModel service) {
    return cartRepo.addItemToCart(service);
  }

  public void removeItemFromCart(CartItem cartItem){
    cartRepo.removeItemFromCart(cartItem);
  }

  public void changeQuantity(CartItem cartItem, int quantity){
    cartRepo.changeQuantity(cartItem, quantity);
  }

  public LiveData<Integer> getTotalPrice() {
    return cartRepo.getTotalPrice();
  }
}
