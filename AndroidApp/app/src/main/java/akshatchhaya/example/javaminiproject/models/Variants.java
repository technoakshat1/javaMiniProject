package akshatchhaya.example.javaminiproject.models;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Variants{
    private String prize;
    private String size;

    public Variants(){}


    private Variants(String prize,String size){
        this.prize=prize;
        this.size=size;
    }

     static Variants[] getProcessedVariants(JSONArray rawVariants){
        int length=rawVariants.length();
        Variants[] processedVariants=new Variants[length];
        try{
            for(int i=0;i<length;i++){
                JSONObject obj=rawVariants.getJSONObject(i);
               String prize=obj.getString("prize");
               String size=obj.getString("size");
               Variants variant=new Variants(prize,size);
               processedVariants[i]=variant;
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return processedVariants;
    }

    public String getPrize() {
        return prize;
    }

    public String getSize() {
        return size;
    }

    @NonNull
    @Override
    public String toString() {
        return "Prize: "+prize+"Size: "+size;
    }
}
