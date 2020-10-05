package com.mobcom.troubleshoot.Repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mobcom.troubleshoot.API.APIRequestData;
import com.mobcom.troubleshoot.API.RetroServer;
import com.mobcom.troubleshoot.models.ResponseServiceModel;
import com.mobcom.troubleshoot.models.ServiceModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    //serviceList.add(new ServiceModel(UUID.randomUUID().toString(), 1299, "hardware", "iMac 21"));
    //serviceList.add(new ServiceModel(UUID.randomUUID().toString(), 799, "hardware", "iPad Air"));

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
