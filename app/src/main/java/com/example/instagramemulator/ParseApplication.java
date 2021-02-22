package com.example.instagramemulator;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Setting up the Parse backend for this app

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("TplPj1oNSQsB3jpWiHvZzXR6wZKFUH49N4S5e3kS")
                .clientKey("CMlhN2tu3FMMY8qHZjeMD7nwve7fidarDtZougMB")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
