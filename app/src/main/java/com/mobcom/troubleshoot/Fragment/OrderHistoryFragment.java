package com.mobcom.troubleshoot.Fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.SessionManager;
import com.mobcom.troubleshoot.adapters.HistoryListAdapter;
import com.mobcom.troubleshoot.databinding.FragmentOrderHistoryBinding;
import com.mobcom.troubleshoot.models.OrderHistoryModel;
import com.mobcom.troubleshoot.viewmodels.HistoryViewModel;

import java.util.List;

public class OrderHistoryFragment extends Fragment implements HistoryListAdapter.HistoryInterface {
  private FragmentOrderHistoryBinding fragmentOrderHistoryBinding;
  private HistoryListAdapter historyListAdapter;
  private HistoryViewModel historyViewModel;
  private SessionManager sessionManager;
  private String account_id;
  private static final String TAG = "OrderHistoryFragment";
  private NavController navController;
  private Window window;

  public OrderHistoryFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    fragmentOrderHistoryBinding = FragmentOrderHistoryBinding.inflate(inflater, container, false);
    return fragmentOrderHistoryBinding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    if (Build.VERSION.SDK_INT >= 21){
      window = this.getActivity().getWindow();
      window.setStatusBarColor(this.getActivity().getResources().getColor(R.color.colorLightGrey));
      window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    // setup navcontroller
    navController = Navigation.findNavController(view);

    // setup sessionmanager
    sessionManager = new SessionManager(getActivity());
    account_id = sessionManager.getUserDetail().get(SessionManager.ACCOUNT_ID);

    // setup recyclerview
    historyListAdapter = new HistoryListAdapter(this);
    fragmentOrderHistoryBinding.rvDataOrderHistory.setAdapter(historyListAdapter);

    // setup view model
    historyViewModel = new ViewModelProvider(requireActivity()).get(HistoryViewModel.class);

    // untuk first load bakal ada loading (progressbar)
    fragmentOrderHistoryBinding.pbDataLayanan.setVisibility(View.VISIBLE);


    // refresh listener
    fragmentOrderHistoryBinding.scrollView.setOnRefreshListener(() -> {
      fragmentOrderHistoryBinding.scrollView.setRefreshing(true);
      retrieveData();
      fragmentOrderHistoryBinding.scrollView.setRefreshing(false);
    });

  }

  @Override
  public void onResume() {
    super.onResume();
    retrieveData();
  }

  public void retrieveData(){
    historyViewModel.getHistories(account_id).observe(getViewLifecycleOwner(), new Observer<List<OrderHistoryModel>>() {
      @Override
      public void onChanged(List<OrderHistoryModel> orderHistory) {
        if (orderHistory == null){
          fragmentOrderHistoryBinding.orderEmpty.setVisibility(View.VISIBLE);
          fragmentOrderHistoryBinding.pbDataLayanan.setVisibility(View.INVISIBLE);
          fragmentOrderHistoryBinding.rvDataOrderHistory.setVisibility(View.INVISIBLE);
        }
        else{
          historyListAdapter.submitList(orderHistory);
          historyListAdapter.notifyDataSetChanged();
          fragmentOrderHistoryBinding.orderEmpty.setVisibility(View.INVISIBLE);
          fragmentOrderHistoryBinding.pbDataLayanan.setVisibility(View.INVISIBLE);
          fragmentOrderHistoryBinding.rvDataOrderHistory.setVisibility(View.VISIBLE);
        }

      }
    });
  }

  @Override
  public void onItemClick(OrderHistoryModel history) {
    historyViewModel.setHistory(history);
    navController.navigate(R.id.action_orderHistoryFragment_to_orderDetailFragment);
  }
}