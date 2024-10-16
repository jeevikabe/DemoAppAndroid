package com.example.demoapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<String> selectedData = new MutableLiveData<>();

    public void select(String data) {
        selectedData.setValue(data);
    }

    public LiveData<String> getSelected() {
        return selectedData;
    }
}

