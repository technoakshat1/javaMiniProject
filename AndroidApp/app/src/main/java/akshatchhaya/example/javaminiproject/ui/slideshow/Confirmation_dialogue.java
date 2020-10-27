package akshatchhaya.example.javaminiproject.ui.slideshow;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import akshatchhaya.example.javaminiproject.R;

public class Confirmation_dialogue extends AppCompatDialogFragment {
    private EditText password_for_confirmation;
    private Button confirmButton;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.password_dialogue,null);
        builder.setView(view).setTitle("Confirm your password to change the password").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        password_for_confirmation= view.findViewById(R.id.confirm_password_for_changing);
        return  builder.create();
    }
}
