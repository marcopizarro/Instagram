package com.codepath.instagram;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("BJZuTUWLYwQ5EUY9ocvrSdGHyeoSxUIz7V9zbjHk")
                .clientKey("NDPlZnE5O78FLYTAWkxzKWWLqAf3IiwT5SYXxfsj")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
