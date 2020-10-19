package com.mobcom.troubleshoot.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mobcom.troubleshoot.models.OrderHistoryModel;
import com.mobcom.troubleshoot.repositories.HistoryRepo;

import java.util.List;

public class HistoryViewModel extends ViewModel {

  HistoryRepo historyRepo = new HistoryRepo();

  MutableLiveData<OrderHistoryModel> mutableHistory = new MutableLiveData<>();

  public LiveData<List<OrderHistoryModel>> getHistories(String account_id) {
    return historyRepo.getHistories(account_id);
  }

  public void setHistory(OrderHistoryModel history) {
    mutableHistory.setValue(history);
  }

  public LiveData<OrderHistoryModel> getHistory(){
    return mutableHistory;
  }
}
