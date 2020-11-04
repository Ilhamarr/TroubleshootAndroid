package com.mobcom.troubleshoot.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.databinding.FragmentOrderFailedBinding;
import com.mobcom.troubleshoot.databinding.FragmentTermConditionBinding;


public class TermConditionFragment extends Fragment {

    private FragmentTermConditionBinding fragmentTermConditionBinding;
    private NavController navController;

    public TermConditionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentTermConditionBinding = FragmentTermConditionBinding.inflate(inflater, container, false);
        return fragmentTermConditionBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // setup navcontroller
        navController = Navigation.findNavController(view);

        // tombol back
        fragmentTermConditionBinding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.popBackStack();
            }
        });

    }
}