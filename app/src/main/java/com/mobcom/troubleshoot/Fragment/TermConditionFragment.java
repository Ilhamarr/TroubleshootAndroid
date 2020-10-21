package com.mobcom.troubleshoot.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.databinding.FragmentOrderFailedBinding;
import com.mobcom.troubleshoot.databinding.FragmentTermConditionBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TermConditionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TermConditionFragment extends Fragment {

    FragmentTermConditionBinding fragmentTermConditionBinding;
    private NavController navController;

    public TermConditionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param //param1 Parameter 1.
     * @param //param2 Parameter 2.
     * @return A new instance of fragment TermConditionFragment.
     */
    // TODO: Rename and change types and number of parameters
//    public static TermConditionFragment newInstance(String param1, String param2) {
//        TermConditionFragment fragment = new TermConditionFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_term_condition, container, false);
    }
}