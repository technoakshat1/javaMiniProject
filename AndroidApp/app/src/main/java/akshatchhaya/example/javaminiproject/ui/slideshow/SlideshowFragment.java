package akshatchhaya.example.javaminiproject.ui.slideshow;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.google.android.material.snackbar.Snackbar;

import akshatchhaya.example.javaminiproject.MainActivity;
import akshatchhaya.example.javaminiproject.R;
import akshatchhaya.example.javaminiproject.api.LoginAPI;
import akshatchhaya.example.javaminiproject.api.OnResponseListener;

public class SlideshowFragment extends Fragment implements Confirmation_dialogue.ExampleDialogListener {
    private SlideshowViewModel slideshowViewModel;
    final String TAG="Reset Password";
    public String password_from_dialogue;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        final Button button=(Button)root.findViewById(R.id.button_reset_password);
        final Button button1=(Button)root.findViewById(R.id.button_reset_password_1);
        final EditText new_Password1=root.findViewById(R.id.new_password);
        final EditText new_Password2=root.findViewById(R.id.confirm_password);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View root){
                openDialog();
                LoginAPI api = new LoginAPI(getActivity(), new OnResponseListener() {
                    @Override
                    public void onSuccess() {
                        button.setVisibility(View.INVISIBLE);
                        button1.setVisibility(View.VISIBLE);
                        new_Password1.setVisibility(View.VISIBLE);
                        new_Password1.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void onFailure(int statusCode) {
                        openDialog();
                    }
                });
                api.confirmPassword(password_from_dialogue);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View root){
                final String s1=new_Password1.getText().toString();
                final String s2=new_Password2.getText().toString();
                if(s1==s2) {
                    LoginAPI api = new LoginAPI(getActivity(), new OnResponseListener() {
                        @Override
                        public void onSuccess() {

                        }


                        @Override
                        public void onFailure(int statusCode) {
                            Snackbar.make(root, "Looks like login failed please retry or re check your credentials and try again!", Snackbar.LENGTH_INDEFINITE).show();
                            Log.e(TAG, "onFailure: error" + statusCode);
                        }
                    });
                    api.resetPassword(new_Password1.getText().toString());
                }
            }
        });
        return root;
    }

    public void  openDialog(){
        Confirmation_dialogue d1=new Confirmation_dialogue();
        d1.show(getChildFragmentManager(),"Password Confirmation");
    }
    public void applyTexts(String password) {
        password_from_dialogue=password;
    }
}