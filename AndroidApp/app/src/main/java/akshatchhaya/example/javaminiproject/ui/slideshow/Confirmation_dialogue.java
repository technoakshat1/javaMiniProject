package akshatchhaya.example.javaminiproject.ui.slideshow;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.snackbar.Snackbar;

import akshatchhaya.example.javaminiproject.MainActivity;
import akshatchhaya.example.javaminiproject.R;
import akshatchhaya.example.javaminiproject.api.LoginAPI;
import akshatchhaya.example.javaminiproject.api.OnResponseListener;

public class Confirmation_dialogue extends AppCompatDialogFragment {

    final String TAG="Confirm Password";
    private ExampleDialogListener listener;
    private String mPassword;

    public Confirmation_dialogue(ExampleDialogListener listener){
        this.listener=listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        final View view=inflater.inflate(R.layout.password_dialogue,null);
        final TextView textView=view.findViewById(R.id.status_password);
        textView.setVisibility(View.GONE);


        builder.setView(view).setTitle("Confirm your password to change the password").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                final EditText password=view.findViewById(R.id.confirm_password_for_changing);
                mPassword=password.getText().toString();
                LoginAPI api=new LoginAPI(getActivity(), new OnResponseListener() {
                    @Override
                    public void onSuccess() {
                        listener.onPasswordConfirm(mPassword);
                    }

                    @Override
                    public void onFailure(int statusCode) {
                      textView.setVisibility(View.VISIBLE);
                    }
                });

               api.confirmPassword(mPassword);
            }
        });
        return  builder.create();
    }

    public interface ExampleDialogListener {
        void onPasswordConfirm(String password);
    }
}
