package com.example.qr.models;

import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<String> firstName = new MutableLiveData<>();
    private final MutableLiveData<String> lastName = new MutableLiveData<>();
    private final MutableLiveData<String> emailAddress = new MutableLiveData<>();
    private final MutableLiveData<String> phoneNumber = new MutableLiveData<>();

    public void setFirstName(String name) {
        firstName.setValue(name);
    }
    public LiveData<String> getFirstName() {
        return firstName;
    }
    public void setLastName(String name) {
        lastName.setValue(name);
    }
    public LiveData<String> getLastName() {
        return lastName;
    }

    public void setEmailAddress(String email){
        emailAddress.setValue(email);
    }
    public LiveData<String> getEmailAddress() {
        return emailAddress;
    }
    public void setPhoneNumber(String number) {
        phoneNumber.setValue(number);
    }
    public LiveData<String> getPhoneNumber() {
        return phoneNumber;
    }
}
