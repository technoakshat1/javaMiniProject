package akshatchhaya.example.javaminiproject.ui.flavoursRecyclerViewAPI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.Arrays;

import akshatchhaya.example.javaminiproject.R;
import akshatchhaya.example.javaminiproject.models.Model;

public class FlavorsRecyclerViewAdapter extends RecyclerView.Adapter<FlavorsRecyclerViewAdapter.FlavoursRecyclerViewHolder> {
    private Model[] data=null;
    private static final String TAG = "FlavorsRecyclerViewAdap";
    //private final Context mContext;

//    public FlavorsRecyclerViewAdapter(Context context){
//        mContext=context;
//    }


    @NonNull
    @Override
    public FlavoursRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.flavour_item,parent,false);
        return new FlavoursRecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FlavoursRecyclerViewHolder holder, int position) {
        Model flavorItem=data[position];
        String title=flavorItem.getFlavor();
        holder.flavorTitle.setText(title);
        Picasso.get()
                .load(flavorItem.getImageUrl())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.flavorImage);
    }

    @Override
    public int getItemCount() {
        return (data==null?0:data.length);
    }

    public void loadNewData(Model[] data){
        Log.d(TAG, "loadNewData: dataset changed to "+ Arrays.toString(data));
        this.data=data.clone();
        notifyDataSetChanged();
    }

    public static class FlavoursRecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView flavorTitle;
        ImageView flavorImage;

        FlavoursRecyclerViewHolder(View item){
            super(item);
            flavorTitle=item.findViewById(R.id.flavour_item_title);
            flavorImage=item.findViewById(R.id.flavour_item_image);
        }
    }
}


