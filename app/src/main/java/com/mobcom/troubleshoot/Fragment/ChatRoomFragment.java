package com.mobcom.troubleshoot.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.SessionManager;
import com.mobcom.troubleshoot.databinding.FragmentChatRoomBinding;
import com.mobcom.troubleshoot.databinding.FragmentHomeBinding;

public class ChatRoomFragment extends Fragment {

    private SessionManager sessionManager;
    private FragmentChatRoomBinding fragmentChatRoomBinding;
    private String firstName;
    private NavController navController;
    private Window window;

    public ChatRoomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentChatRoomBinding = FragmentChatRoomBinding.inflate(inflater, container, false);
        return fragmentChatRoomBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21){
            window = this.getActivity().getWindow();
            window.setStatusBarColor(this.getActivity().getResources().getColor(R.color.colortrb));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }

        // setup sessionmanager
        sessionManager = new SessionManager(getActivity());
        firstName = sessionManager.getUserDetail().get(SessionManager.FIRST_NAME);
        fragmentChatRoomBinding.tvfirstNameHome.setText(firstName);

        // setup navcontroller
        navController = Navigation.findNavController(view);
    }

    private void goToUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}