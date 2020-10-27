package akshatchhaya.example.javaminiproject.ui.slideshow;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import akshatchhaya.example.javaminiproject.R;

public class SlideshowFragment extends Fragment {
    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        Button button=(Button)root.findViewById(R.id.button_reset_password);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View root){
                openDialog();
            }
        });
        return root;
    }

    public void  openDialog(){
        Confirmation_dialogue d1=new Confirmation_dialogue();
        d1.show(getChildFragmentManager(),"Password Confirmation");
    }
}