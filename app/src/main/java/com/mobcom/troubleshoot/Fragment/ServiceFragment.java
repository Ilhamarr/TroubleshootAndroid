package com.mobcom.troubleshoot.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobcom.troubleshoot.Adapter.ServiceListAdapter;
import com.mobcom.troubleshoot.models.ServiceModel;
import com.mobcom.troubleshoot.ViewModel.ServiceViewModel;
import com.mobcom.troubleshoot.databinding.FragmentServiceBinding;

import java.util.List;

public class ServiceFragment extends Fragment implements ServiceListAdapter.ServiceInterface {

  FragmentServiceBinding fragmentServiceBinding;
  private ServiceListAdapter serviceListAdapter;
  private ServiceViewModel serviceViewModel;

  public ServiceFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    fragmentServiceBinding = FragmentServiceBinding.inflate(inflater, container, false);
    return fragmentServiceBinding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    serviceListAdapter = new ServiceListAdapter();
    fragmentServiceBinding.serviceRecyclerView.setAdapter(serviceListAdapter);
    //fragmentServiceBinding.serviceRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(),DividerItemDecoration.HORIZONTAL));

    serviceViewModel = new ViewModelProvider(requireActivity()).get(ServiceViewModel.class);
    serviceViewModel.getService().observe(getViewLifecycleOwner(), new Observer<List<ServiceModel>>() {
      @Override
      public void onChanged(List<ServiceModel> service) {
        serviceListAdapter.submitList(service);
      }
    });
  }

  @Override
  public void addItem(ServiceModel service) {

  }

  @Override
  public void onItemClick(ServiceModel service) {

  }
}