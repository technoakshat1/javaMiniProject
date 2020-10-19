package akshatchhaya.example.javaminiproject.models;


import androidx.annotation.NonNull;

import org.json.JSONArray;

public class Model {
    private String flavor;
    private String description;
    private String imageUrl;
    private JSONArray mRawVariants;
    //private Variants[] mProccesedVariants;

    public Model(String flavor,String description,String imageUrl,JSONArray rawVariants){
        this.flavor=flavor;
        this.description=description;
        this.imageUrl=imageUrl;
        this.mRawVariants=rawVariants;
    }

    public String getFlavor() {
        return flavor;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Variants[] getVariants(){
        return Variants.getProcessedVariants(mRawVariants);
    }
    

    @NonNull
    @Override
    public String toString() {
        return "flavor: "+flavor+" Description: "+description+" ImageUrl: "+imageUrl;
    }
}
