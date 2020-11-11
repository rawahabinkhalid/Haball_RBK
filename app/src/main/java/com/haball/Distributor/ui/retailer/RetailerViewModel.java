package com.haball.Distributor.ui.retailer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RetailerViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RetailerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is share fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}