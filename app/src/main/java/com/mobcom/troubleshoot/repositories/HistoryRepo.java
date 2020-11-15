package com.mobcom.troubleshoot.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mobcom.troubleshoot.API.APIRequestData;
import com.mobcom.troubleshoot.API.RetroServer;
import com.mobcom.troubleshoot.models.OrderHistoryModel;
import com.mobcom.troubleshoot.models.ResponseOrderHistory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryRepo {

  private static final String TAG = "HistoryRepo";

  private MutableLiveData<List<OrderHistoryModel>> mutableHistoryList;
  List<OrderHistoryModel> historyList = new ArrayList<>();

  public LiveData<List<OrderHistoryModel>> getHistories(String account_id) {
    mutableHistoryList = new MutableLiveData<>();
    loadHistory(account_id);
    return mutableHistoryList;
  }

  private void loadHistory(String account_id) {
    APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
    Call<ResponseOrderHistory> tampilOrderHistory = ardData.getListOrderHistory(account_id);
    tampilOrderHistory.enqueue(new Callback<ResponseOrderHistory>() {
      @Override
      public void onResponse(Call<ResponseOrderHistory> call, Response<ResponseOrderHistory> response) {
        if (response.isSuccessful() && response.body().isStatus()) {
          historyList = response.body().getData();
          mutableHistoryList.setValue(historyList);
          Log.d(TAG, "onResponse: " + response.body().isStatus());
        } else {
          mutableHistoryList.setValue(null);
          Log.d(TAG, "onResponse: " + response.body().getMessage());
        }
      }

      @Override
      public void onFailure(Call<ResponseOrderHistory> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
      }
    });

  }
}
