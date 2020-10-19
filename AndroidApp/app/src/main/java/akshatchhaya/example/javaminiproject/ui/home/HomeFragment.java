package akshatchhaya.example.javaminiproject.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;

import akshatchhaya.example.javaminiproject.R;
import akshatchhaya.example.javaminiproject.api.DataRetrievalAPI;
import akshatchhaya.example.javaminiproject.api.OnDataResponse;
import akshatchhaya.example.javaminiproject.models.Model;
import akshatchhaya.example.javaminiproject.ui.flavoursRecyclerViewAPI.FlavorsRecyclerViewAdapter;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FlavorsRecyclerViewAdapter mAdapter;
    private Model [] mHotFlavours;
    private static final String TAG = "HomeFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter=new FlavorsRecyclerViewAdapter();
        DataRetrievalAPI api=new DataRetrievalAPI(getActivity(), new OnDataResponse() {
            @Override
            public void onDataAvailable(Model[] data) {
                mHotFlavours=data.clone();
                Log.d(TAG, "onDataAvailable: data available"+ Arrays.toString(mHotFlavours));
                mAdapter.loadNewData(data);
            }

            @Override
            public void onFailure() {
                Log.d(TAG, "onFailure: loading failed");
            }
        });

        api.getHotFlavours();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recyclerView=root.findViewById(R.id.flavor_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);
        return root;
    }
}