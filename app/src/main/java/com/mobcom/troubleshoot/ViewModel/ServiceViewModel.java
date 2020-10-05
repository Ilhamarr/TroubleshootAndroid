package com.mobcom.troubleshoot.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mobcom.troubleshoot.models.ServiceModel;
import com.mobcom.troubleshoot.Repositories.ServiceRepo;

import java.util.List;

public class ServiceViewModel extends ViewModel {

  ServiceRepo serviceRepo = new ServiceRepo();

  public LiveData<List<ServiceModel>> getService(){
    return serviceRepo.getService();
  }
}
