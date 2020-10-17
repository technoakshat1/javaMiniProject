package akshatchhaya.example.javaminiproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.material.navigation.NavigationView;

import akshatchhaya.example.javaminiproject.api.LoginAPI;
import akshatchhaya.example.javaminiproject.api.OnResponseListener;


public class LoginHome extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //
        return inflater.inflate(R.layout.fragment_login_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final ProgressBar progressBar=view.findViewById(R.id.login_home_progressBar);
        progressBar.setVisibility(View.VISIBLE);
        LoginAPI api=new LoginAPI(getActivity(), new OnResponseListener() {
            @Override
            public void onSuccess() {
                Intent intent=getActivity().getIntent();
                intent.setClass(getActivity(),MainActivity.class);
                startActivity(intent);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(int statusCode) {
                NavController navController= Navigation.findNavController(getActivity(),R.id.login_nav_host);
                navController.navigate(R.id.action_loginHome_to_loginFragment);
            }
        });

        api.isLoggedIn();
    }
}