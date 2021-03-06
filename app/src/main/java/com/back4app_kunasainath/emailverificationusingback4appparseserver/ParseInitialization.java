package com.back4app_kunasainath.emailverificationusingback4appparseserver;

import android.app.Application;

import com.parse.Parse;

public class ParseInitialization extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
        .applicationId(getString(R.string.parse_application_id))
        .clientKey(getString(R.string.parse_client_key))
        .server(getString(R.string.parse_url))
        .build());

    }
}
