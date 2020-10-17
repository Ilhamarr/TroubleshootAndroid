package com.mobcom.troubleshoot.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mobcom.troubleshoot.API.APIRequestData;
import com.mobcom.troubleshoot.API.RetroServer;
import com.mobcom.troubleshoot.models.LaptopModel;
import com.mobcom.troubleshoot.models.ResponseLaptopModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaptopRepo {

  private MutableLiveData<List<LaptopModel>> mutableLaptopList;
  List<LaptopModel> laptopList = new ArrayList<>();

  public LiveData<List<LaptopModel>> getLaptop(){
    if (mutableLaptopList == null){
      mutableLaptopList = new MutableLiveData<>();
      loadLaptop();
    }
    return mutableLaptopList;
  }

  private void loadLaptop(){
    APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
    Call<ResponseLaptopModel> tampilDataLaptop = ardData.ambilListLaptop();
    tampilDataLaptop.enqueue(new Callback<ResponseLaptopModel>() {
      @Override
      public void onResponse(Call<ResponseLaptopModel> call, Response<ResponseLaptopModel> response) {
        if (response.isSuccessful()){
          laptopList = response.body().getListLaptop();
          mutableLaptopList.setValue(laptopList);
        }
      }

      @Override
      public void onFailure(Call<ResponseLaptopModel> call, Throwable t) {

      }
    });

  }
}
