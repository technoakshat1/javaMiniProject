package akshatchhaya.example.javaminiproject.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;

import akshatchhaya.example.javaminiproject.R;
import akshatchhaya.example.javaminiproject.api.DataRetrievalAPI;
import akshatchhaya.example.javaminiproject.api.OnDataResponse;
import akshatchhaya.example.javaminiproject.models.Model;
import akshatchhaya.example.javaminiproject.ui.flavoursRecyclerViewAPI.FlavorsRecyclerViewAdapter;
import akshatchhaya.example.javaminiproject.ui.home.HomeViewModel;
public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FlavorsRecyclerViewAdapter mAdapter;
    private Model[] mFlavours;
    private static final String TAG = "GalleryFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter=new FlavorsRecyclerViewAdapter();
        DataRetrievalAPI api=new DataRetrievalAPI(getActivity(), new OnDataResponse() {
            @Override
            public void onDataAvailable(Model[] data) {
                mFlavours=data.clone();
                Log.d(TAG, "onDataAvailable: data available"+ Arrays.toString(mFlavours));
                mAdapter.loadNewData(data);
            }

            @Override
            public void onFailure() {
                Log.d(TAG, "onFailure: loading failed");
            }
        });

        api.getFlavours();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        RecyclerView recyclerView=root.findViewById(R.id.flavor_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);
        return root;
    }
}