package com.mobcom.troubleshoot.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.databinding.FragmentOrderFailedBinding;

public class OrderFailedFragment extends Fragment {

  FragmentOrderFailedBinding fragmentOrderFailedBinding;

  public OrderFailedFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    fragmentOrderFailedBinding = FragmentOrderFailedBinding.inflate(inflater, container, false);
    return fragmentOrderFailedBinding.getRoot();
  }
}