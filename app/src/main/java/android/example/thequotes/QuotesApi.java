package android.example.thequotes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QuotesApi {
    @GET("quotes")
    Call<ArrayList<Quote>> getQuotes();
}
