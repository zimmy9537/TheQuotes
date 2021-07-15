package android.example.thequotes;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainViewModel extends ViewModel {
    public String LOG_TAG = MainViewModel.class.getSimpleName();
    private static final String TYPE_FIT_API = "https://type.fit/api/";
    private Context context = null;
    private ArrayList<Quote> quoteArrayList = null;
    private int index = 0;

    public MainViewModel(Context context) {
        this.context = context;
        loadQuoteFromJson(context);
    }

    private void loadQuoteFromJson(Context context) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TYPE_FIT_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        QuotesApi quotesApi = retrofit.create(QuotesApi.class);
        Call<ArrayList<Quote>> call = quotesApi.getQuotes();

        call.enqueue(new Callback<ArrayList<Quote>>() {
            @Override
            public void onResponse(Call<ArrayList<Quote>> call, Response<ArrayList<Quote>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "response is unsuccessful " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d(LOG_TAG, "hit this point");
                quoteArrayList = response.body();
                if (quoteArrayList == null) {
                    Log.d(LOG_TAG, "the quoteArrayList is empty in size *****");
                }
                Toast.makeText(context, "QUOTES :- " + quoteArrayList.size(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ArrayList<Quote>> call, Throwable t) {
                Log.d(LOG_TAG, "response Failure " + t.getMessage());
            }
        });
    }

    Quote quote = new Quote();

    public Quote getQuote() {
        if (quoteArrayList == null) {
            quote.setText("abc");
            quote.setAuthor("xyz");
            return quote;
        }
        return quoteArrayList.get(index);
    }

    public Quote nextQuote() {
//        if(quoteArrayList==null)
//        {
//            quote.setText("abc");
//            quote.setAuthor("xyz");
//            return quote;
//        }
        return quoteArrayList.get((++index) % quoteArrayList.size());
    }

    public Quote previousQuote() {
        return quoteArrayList.get((--index) % quoteArrayList.size());
    }

    public int getIndex() {
        return (index + 1);
    }
}

