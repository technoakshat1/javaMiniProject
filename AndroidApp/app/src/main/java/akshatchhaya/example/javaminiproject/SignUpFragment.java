package akshatchhaya.example.javaminiproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import akshatchhaya.example.javaminiproject.api.OnResponseListener;
import akshatchhaya.example.javaminiproject.api.SignUpApi;

public class SignUpFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       return inflater.inflate(R.layout.fragment_signup,container,false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        final TextView username=view.findViewById(R.id.signup_username);
        final TextView password=view.findViewById(R.id.signup_password);
        final TextView confirmPassword=view.findViewById(R.id.signup_confirm_password);
        final TextView passwordMatch=view.findViewById(R.id.password_match);
        final ProgressBar progressBar=view.findViewById(R.id.signup_progressbar);
        final Button confirm=view.findViewById(R.id.btn_confirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().trim().equals(confirmPassword.getText().toString().trim())){
                    progressBar.setVisibility(View.VISIBLE);
                    SignUpApi api=new SignUpApi(getActivity(), new OnResponseListener() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                            Intent intent=getActivity().getIntent();
                            if(intent!=null){
                                intent.setClass(getContext(),MainActivity.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(int statusCode) {
                            Snackbar.make(view,"Due to an internal issue there please try again later!",Snackbar.LENGTH_LONG).show();
                        }
                    });

                    api.signUp(username.getText().toString().trim(),password.getText().toString().trim());
                }else{
                    passwordMatch.setText("Passwords doesn't match please check");
                    passwordMatch.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
