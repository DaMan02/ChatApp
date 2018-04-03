package com.dayal.talkative.activities;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dayal.talkative.model.Message;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;

public class ChatApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this);
        ParseObject.registerSubclass(Message.class);

        ParseInstallation.getCurrentInstallation().saveInBackground();   //save curr installation to back4app
    }
}
