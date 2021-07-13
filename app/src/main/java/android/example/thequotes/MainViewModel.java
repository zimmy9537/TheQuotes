package android.example.thequotes;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainViewModel extends ViewModel {
    public String LOG_TAG=MainViewModel.class.getSimpleName();
    private static final String TYPE_FIT_API="https://type.fit/api/quotes";
    private Context context=null;
    private ArrayList<Quote> quoteArrayList=null;
    private int index = 0;

    public MainViewModel(Context context) throws IOException {
        this.context = context;
        quoteArrayList = loadQuoteFromJson(context);
    }

    private ArrayList<Quote> loadQuoteFromJson(Context context) throws IOException {
        /*
        ExecutorService service= Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                // same as the doInBackGround method of the async Task.
                try {
                    quoteArrayList=(ArrayList<Quote>) QueryUtils.fetchQuotesData(mUrl);
                } catch (IOException e) {
                    Log.e(LOG_TAG, "error fetching the data ",e);
                }
            }
        });
        */
        InputStream inputStream=context.getAssets().open("quotes.json");
        int size=inputStream.available();
        byte[] buffer=new byte[size];
        inputStream.read(buffer);
        inputStream.close();
        String json=new String(buffer, StandardCharsets.UTF_8);
        Gson gson=new Gson();
        Type typeToken=new TypeToken<ArrayList<Quote>>(){}.getType();
        quoteArrayList=gson.fromJson(json, typeToken);
        return quoteArrayList;
    }
    public Quote getQuote()
    {
        return quoteArrayList.get(index);
    }
    public Quote nextQuote()
    {
        return quoteArrayList.get(++index);
    }
    public Quote previousQuote()
    {
        return quoteArrayList.get(--index);
    }
}

