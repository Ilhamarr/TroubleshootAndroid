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

import com.applozic.mobicommons.commons.core.utils.Utils;
import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.SessionManager;
import com.mobcom.troubleshoot.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

import io.kommunicate.KmChatBuilder;
import io.kommunicate.Kommunicate;
import io.kommunicate.callbacks.KmCallback;

public class HomeFragment extends Fragment {
  private SessionManager sessionManager;
  private FragmentHomeBinding fragmentHomeBinding;
  private String firstName;
  private NavController navController;
  private Window window;

  public HomeFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);
    return fragmentHomeBinding.getRoot();
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    if (Build.VERSION.SDK_INT >= 21) {
      window = this.getActivity().getWindow();
      window.setStatusBarColor(this.getActivity().getResources().getColor(R.color.colortrb));
      window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
    }

    // setup sessionmanager
    sessionManager = new SessionManager(getActivity());
    firstName = sessionManager.getUserDetail().get(SessionManager.FIRST_NAME);
    fragmentHomeBinding.tvfirstNameHome.setText(firstName);

    // setup chatbot
    Kommunicate.init(getContext(), "34550bd3f13365904a4db45491ad8657f");


    // setup navcontroller
    navController = Navigation.findNavController(view);

    // button pesan sekarang
    fragmentHomeBinding.btnPesanSekarang.setOnClickListener(v -> navController.navigate(R.id.action_homeFragment_to_serviceFragment));

    // button konsultasi
    //fragmentHomeBinding.btnKonsul.setOnClickListener(v -> goToUrl("https://api.whatsapp.com/send/?phone=6285156811345&text&app_absent=0"));
    fragmentHomeBinding.btnKonsul.setOnClickListener(v -> launchChatBot());
  }

  private void goToUrl(String s) {
    Uri uri = Uri.parse(s);
    startActivity(new Intent(Intent.ACTION_VIEW, uri));
  }

  private void launchChatBot() {
    List<String> botList = new ArrayList();
    botList.add("trouble-bot-rorxl"); //enter your integrated bot Ids
    new KmChatBuilder(getContext()).setChatName("Support")
            .setBotIds(botList)
            .launchChat(new KmCallback() {
              @Override
              public void onSuccess(Object message) {
                Utils.printLog(getContext(), "ChatTest", "Success : " + message);
              }

              @Override
              public void onFailure(Object error) {
                Utils.printLog(getContext(), "ChatTest", "Failure : " + error);
              }
            });
  }

}
