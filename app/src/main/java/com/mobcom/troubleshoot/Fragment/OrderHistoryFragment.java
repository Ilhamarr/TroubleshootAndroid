package com.mobcom.troubleshoot.Fragment;

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

import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.SessionManager;
import com.mobcom.troubleshoot.adapters.HistoryListAdapter;
import com.mobcom.troubleshoot.databinding.FragmentOrderHistoryBinding;
import com.mobcom.troubleshoot.models.OrderHistoryModel;
import com.mobcom.troubleshoot.viewmodels.HistoryViewModel;

import java.util.List;

public class OrderHistoryFragment extends Fragment implements HistoryListAdapter.HistoryInterface {
  FragmentOrderHistoryBinding fragmentOrderHistoryBinding;
  private HistoryListAdapter historyListAdapter;
  private HistoryViewModel historyViewModel;
  private SessionManager sessionManager;
  private String account_id;
  private static final String TAG = "OrderHistoryFragment";
  private NavController navController;

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

    sessionManager = new SessionManager(getActivity());
    account_id = sessionManager.getUserDetail().get(SessionManager.ACCOUNT_ID);
    historyListAdapter = new HistoryListAdapter(this);
    fragmentOrderHistoryBinding.rvDataOrderHistory.setAdapter(historyListAdapter);
    historyViewModel = new ViewModelProvider(requireActivity()).get(HistoryViewModel.class);
    fragmentOrderHistoryBinding.pbDataLayanan.setVisibility(View.VISIBLE);

    fragmentOrderHistoryBinding.scrollView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        fragmentOrderHistoryBinding.scrollView.setRefreshing(true);
        retrieveData();
        fragmentOrderHistoryBinding.scrollView.setRefreshing(false);
      }
    });

    navController = Navigation.findNavController(view);

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
        historyListAdapter.submitList(orderHistory);
        historyListAdapter.notifyDataSetChanged();
        fragmentOrderHistoryBinding.pbDataLayanan.setVisibility(View.INVISIBLE);
      }
    });
  }

  @Override
  public void onItemClick(OrderHistoryModel history) {
    Log.d(TAG, "onItemClick: " + history.toString());
    historyViewModel.setHistory(history);
    navController.navigate(R.id.action_orderHistoryFragment_to_orderDetailFragment);
  }
}