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
import android.widget.ProgressBar;
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
    Button button;
    Button button1;
    EditText new_Password1;
    EditText old_password;
    EditText new_Password2;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
         button=root.findViewById(R.id.button_reset_password);
         button1=root.findViewById(R.id.button_reset_password_1);
         old_password=root.findViewById(R.id.old_password);
         new_Password1=root.findViewById(R.id.new_password);
         new_Password2=root.findViewById(R.id.confirm_password);
         final ProgressBar pb=root.findViewById(R.id.resetPasswordSuccess);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View root){
                openDialog();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v){
                pb.setVisibility(View.VISIBLE);
                final String s1=new_Password1.getText().toString();
                final String s2=new_Password2.getText().toString();
                if(s1.equals(s2)) {
                    LoginAPI api = new LoginAPI(getActivity(), new OnResponseListener() {
                        @Override
                        public void onSuccess() {
                           pb.setVisibility(View.GONE);
                           Snackbar.make(root,"Password change successful",Snackbar.LENGTH_LONG).show();
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
        Confirmation_dialogue d1=new Confirmation_dialogue(this);
        d1.show(getChildFragmentManager(),"Password Confirmation");
    }
    public void onPasswordConfirm(String password) {
       new_Password1.setVisibility(View.VISIBLE);
       old_password.setText(password);
       old_password.setFocusable(false);
       old_password.setClickable(false);
       new_Password2.setVisibility(View.VISIBLE);
       button.setVisibility(View.INVISIBLE);
       button1.setVisibility(View.VISIBLE);
    }
}