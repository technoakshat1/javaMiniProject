package akshatchhaya.example.javaminiproject.api;

public interface OnResponseListener {
    void onSuccess();
    void onFailure(int statusCode);
}
