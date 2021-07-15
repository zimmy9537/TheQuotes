package android.example.thequotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    private TextView quoteText;
    private TextView authorText;
    private MainViewModel mainViewModel = null;
    private TextView index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quoteText = findViewById(R.id.tv_quotes_text);
        authorText = findViewById(R.id.tv_author);
        index=findViewById(R.id.index);
        mainViewModel = new ViewModelProvider(this, new MainViewModelFactory(getApplicationContext())).get(MainViewModel.class);
        setQuote(mainViewModel.getQuote());
    }

    private void setIndex(int indexNumber)
    {
        index.setText(String.valueOf(indexNumber));
    }

    private void setQuote(Quote quote) {
        quoteText.setText(quote.getText());
        authorText.setText(quote.getAuthor());
        setIndex(mainViewModel.getIndex());
    }

    public void onPrevious(View view) {
        setQuote(mainViewModel.previousQuote());
    }

    public void onNext(View view) {
        setQuote(mainViewModel.nextQuote());
    }

    public void onShare(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, mainViewModel.getQuote().getText()+"\n\n  -"+mainViewModel.getQuote().getAuthor());
        intent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(intent, null);
        startActivity(shareIntent);
    }
}