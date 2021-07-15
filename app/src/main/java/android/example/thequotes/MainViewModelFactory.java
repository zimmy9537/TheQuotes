package android.example.thequotes;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.io.IOException;

public class MainViewModelFactory implements ViewModelProvider.Factory {
    public Context context;
    public MainViewModelFactory(Context context)
    {
        this.context=context;
    }
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        MainViewModel mainViewModel= null;
        mainViewModel = new MainViewModel(context);
        return (T)mainViewModel;
    }
}
