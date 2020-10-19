package akshatchhaya.example.javaminiproject.api;

import akshatchhaya.example.javaminiproject.models.Model;

public interface OnDataResponse {
    void onDataAvailable(Model[] data);
    void onFailure();
}
