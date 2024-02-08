package com.dss.dsboxplus.alertdialog;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SubscriptionViewModel extends ViewModel {
    private MutableLiveData<Integer> remainingDays;

    public MutableLiveData<Integer> getRemainingDays() {
        if (remainingDays == null) {
            remainingDays = new MutableLiveData<>();
        }
        return remainingDays;
    }

    public void setRemainingDays(int remainingDays) {
        if (this.remainingDays != null) {
            this.remainingDays.setValue(remainingDays);
        }
    }
}
