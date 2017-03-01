package com.nyro.crimewatch.Application;

import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.nyro.crimewatch.Models.User;

/**
 * Created by smsgh on 10/01/2017.
 */

public class Application extends android.app.Application {

    private static Context context;




    @Override
    public void onCreate() {
        super.onCreate();

        context = getAppContext();


        initDataBase();


    }


    public static Context getAppContext() {
        return context;
    }


    //Setup DataBase
    protected void initDataBase() {
        Configuration.Builder configurationBuilder = new Configuration.Builder(this);
        configurationBuilder.addModelClasses(User.class);
         ActiveAndroid.initialize(configurationBuilder.create());
    }

}
