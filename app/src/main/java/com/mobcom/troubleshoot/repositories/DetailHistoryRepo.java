package com.mobcom.troubleshoot.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mobcom.troubleshoot.API.APIRequestData;
import com.mobcom.troubleshoot.API.RetroServer;
import com.mobcom.troubleshoot.models.ItemOrderModel;
import com.mobcom.troubleshoot.models.ResponseItemOrderDetail;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailHistoryRepo {

  private static final String TAG = "detailRepo";

  private MutableLiveData<List<ItemOrderModel>> mutableItemOrderList;
  List<ItemOrderModel> itemOrderList = new ArrayList<>();

  public LiveData<List<ItemOrderModel>> getItemOrders(String tracking_key) {
    mutableItemOrderList = new MutableLiveData<>();
    loadItemOrder(tracking_key);
    return mutableItemOrderList;
  }

  private void loadItemOrder(String tracking_key) {
    APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
    Call<ResponseItemOrderDetail> tampilItemOrder = ardData.getListOrderLayanan(tracking_key);
    tampilItemOrder.enqueue(new Callback<ResponseItemOrderDetail>() {
      @Override
      public void onResponse(Call<ResponseItemOrderDetail> call, Response<ResponseItemOrderDetail> response) {
        if (response.isSuccessful() && response.body().isStatus()) {
          itemOrderList = response.body().getData();
          mutableItemOrderList.setValue(itemOrderList);
          Log.d(TAG, "onResponse: " + response.body().isStatus());
        }
      }

      @Override
      public void onFailure(Call<ResponseItemOrderDetail> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
      }
    });

  }
}
