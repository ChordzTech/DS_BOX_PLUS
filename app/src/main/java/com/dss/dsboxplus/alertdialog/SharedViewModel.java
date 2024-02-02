package com.dss.dsboxplus.alertdialog;

import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private int remainingDays = 0;

    public int getRemainingDays() {
        return remainingDays;
    }

    public void setRemainingDays(int remainingDays) {
        this.remainingDays = remainingDays;
    }
}
