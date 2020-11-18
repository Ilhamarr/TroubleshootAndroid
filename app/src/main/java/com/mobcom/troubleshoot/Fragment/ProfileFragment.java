package com.mobcom.troubleshoot.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mobcom.troubleshoot.Activity.LoginActivity;
import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.SessionManager;
import com.mobcom.troubleshoot.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
  private static final String TAG = "ProfileFragment";
  private SessionManager sessionManager;
  private FragmentProfileBinding fragmentProfileBinding;
  private String firstName, lastName, telepon, email, alamat, fullName, picture, provider;
  private NavController navController;
  private Window window;
  private GoogleSignInClient mGoogleSignInClient;
  private GoogleSignInOptions gso;

  public ProfileFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    fragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false);
    return fragmentProfileBinding.getRoot();
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    if (Build.VERSION.SDK_INT >= 21){
      window = this.getActivity().getWindow();
      window.setStatusBarColor(this.getActivity().getResources().getColor(R.color.colorLightGrey));
      window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    sessionManager = new SessionManager(getActivity());
    firstName = sessionManager.getUserDetail().get(SessionManager.FIRST_NAME);
    lastName = sessionManager.getUserDetail().get(SessionManager.LAST_NAME);
    email = sessionManager.getUserDetail().get(SessionManager.EMAIL);
    alamat = sessionManager.getUserDetail().get(SessionManager.ALAMAT);
    telepon = sessionManager.getUserDetail().get(SessionManager.NOMOR_HP);
    picture = sessionManager.getUserDetail().get(SessionManager.PICTURE);
    provider = sessionManager.getUserDetail().get(SessionManager.PROVIDER);
    Log.d(TAG, "onViewCreated: provider : "+ provider);

    fullName = firstName + " " +lastName;
    fragmentProfileBinding.namaUser.setText(fullName);
    fragmentProfileBinding.emailUser.setText(email);
    fragmentProfileBinding.alamatUser.setText(alamat);
    fragmentProfileBinding.teleponUser.setText(telepon);
    
    // set foto profile
    if (picture != null){
      try {
        Glide.with(getContext()).load(picture).into(fragmentProfileBinding.fotoProfile);
      } catch (NullPointerException e){
        Toast.makeText(getContext(), "image not found", Toast.LENGTH_SHORT).show();
      }
    }


    // setup navcontroller
    navController = Navigation.findNavController(view);

    // if account google
     gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build();

    mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);

    fragmentProfileBinding.editProfil.setOnClickListener(v -> navController.navigate(R.id.action_profileFragment_to_editProfileFragment));

    fragmentProfileBinding.orderhistory.setOnClickListener(v -> navController.navigate(R.id.action_profileFragment_to_orderHistoryFragment));

    fragmentProfileBinding.tvLogout.setOnClickListener(v -> {
      sessionManager.logoutSession();
      if (provider.equals("google")){
        signOutGoogle();
      }

      moveToLogin();
      Toast.makeText(getContext(), "Anda berhasil keluar", Toast.LENGTH_SHORT).show();
    });

    fragmentProfileBinding.feedback.setOnClickListener(v -> goToUrl("https://g.page/troubleshootid/review?gm"));

    fragmentProfileBinding.buttonTC.setOnClickListener(v -> navController.navigate(R.id.action_profileFragment_to_termConditionFragment));
  }

  private void moveToLogin() {
    Intent intent = new Intent(getActivity(), LoginActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
    startActivity(intent);
    getActivity().finish();
  }

  private void goToUrl(String s) {
    Uri uri = Uri.parse(s);
    startActivity(new Intent(Intent.ACTION_VIEW,uri));
  }

  private void signOutGoogle() {
    mGoogleSignInClient.signOut()
            .addOnCompleteListener(getActivity(), task -> {
              Log.d(TAG, "onComplete: " + "google account has been sign out");
            });
  }

  private void revokeAccessGoogle() {
    mGoogleSignInClient.revokeAccess()
            .addOnCompleteListener(getActivity(), task -> {
              // ...
            });
  }

}