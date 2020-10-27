package akshatchhaya.example.javaminiproject;
import android.content.Intent;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import akshatchhaya.example.javaminiproject.api.LoginAPI;
import akshatchhaya.example.javaminiproject.api.OnResponseListener;

public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";
    private Button mLoginBtn;
    private TextView mUsername;
    private TextView mPassword;
    private ProgressBar mProgressBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_login, container, false);
        mLoginBtn=view.findViewById(R.id.button_reset_password);
        mUsername=view.findViewById(R.id.username);
        mPassword=view.findViewById(R.id.password);
        mProgressBar=view.findViewById(R.id.progressBar);

        Button signup=view.findViewById(R.id.btn_signup);

        final NavController navController= Navigation.findNavController(getActivity(),R.id.login_nav_host);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_loginFragment_to_signUpFragment);
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                LoginAPI api=new LoginAPI(getActivity(), new OnResponseListener() {
                    @Override
                    public void onSuccess() {
                        mProgressBar.setVisibility(View.GONE);
                        Intent intent=getActivity().getIntent();
                        if(intent!=null){
                            intent.setClass(getContext(),MainActivity.class);
                            startActivity(intent);
                        }
                    }


                    @Override
                    public void onFailure(int statusCode) {
                        Snackbar.make(view,"Looks like login failed please retry or re check your credentials and try again!",Snackbar.LENGTH_INDEFINITE).show();
                        mProgressBar.setVisibility(View.GONE);
                        Log.e(TAG, "onFailure: error"+statusCode);
                    }
                });

                api.login(mUsername.getText().toString().trim(),mPassword.getText().toString().trim());
            }
        });
        return  view;
    }




}