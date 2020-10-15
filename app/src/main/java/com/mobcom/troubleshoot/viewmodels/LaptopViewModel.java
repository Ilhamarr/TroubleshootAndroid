package com.mobcom.troubleshoot.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mobcom.troubleshoot.models.LaptopModel;
import com.mobcom.troubleshoot.repositories.LaptopRepo;

import java.util.List;

public class LaptopViewModel extends ViewModel {

  LaptopRepo laptopRepo = new LaptopRepo();

  MutableLiveData<LaptopModel> mutableLaptop = new MutableLiveData<>();

  public LiveData<List<LaptopModel>> getLaptops() {
    return laptopRepo.getLaptop();
  }

  public void setLaptop(LaptopModel laptop){
    mutableLaptop.setValue(laptop);
  }

  public LiveData<LaptopModel> getLaptop(){
    return mutableLaptop;
  }

}
