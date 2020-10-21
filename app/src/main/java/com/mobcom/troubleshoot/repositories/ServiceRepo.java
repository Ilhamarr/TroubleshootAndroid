package com.mobcom.troubleshoot.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mobcom.troubleshoot.API.APIRequestData;
import com.mobcom.troubleshoot.API.RetroServer;
import com.mobcom.troubleshoot.models.ResponseServiceModel;
import com.mobcom.troubleshoot.models.ServiceModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceRepo {

  private MutableLiveData<List<ServiceModel>> mutableServiceList;
  List<ServiceModel> serviceList = new ArrayList<>();

  public LiveData<List<ServiceModel>> getService(){
    if (mutableServiceList == null){
      mutableServiceList = new MutableLiveData<>();
      loadService();
    }
    return mutableServiceList;
  }

  private void loadService(){
    APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
    Call<ResponseServiceModel> tampilDataLayanan = ardData.ambillistLayanan();
    tampilDataLayanan.enqueue(new Callback<ResponseServiceModel>() {
      @Override
      public void onResponse(Call<ResponseServiceModel> call, Response<ResponseServiceModel> response) {
        if (response.isSuccessful()){
          serviceList = response.body().getListService();
          mutableServiceList.setValue(serviceList);
        }
      }

      @Override
      public void onFailure(Call<ResponseServiceModel> call, Throwable t) {

      }
    });
  }
}
