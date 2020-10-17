package com.mobcom.troubleshoot.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mobcom.troubleshoot.models.OrderHistoryModel;
import com.mobcom.troubleshoot.repositories.HistoryRepo;

import java.util.List;

public class HistoryViewModel extends ViewModel {

  HistoryRepo historyRepo = new HistoryRepo();

  public LiveData<List<OrderHistoryModel>> getHistories(String account_id) {
    return historyRepo.getHistories(account_id);
  }
}
