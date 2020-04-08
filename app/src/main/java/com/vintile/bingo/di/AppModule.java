package com.vintile.bingo.di;


import android.app.Application;

import com.vintile.bingo.db.SharedPref;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Sharath on 2020/02/10
 **/
@Module
public abstract class AppModule {

    @Singleton
    @Provides
    static SharedPref providesSharedPref(Application application) {
        return new SharedPref(application);
    }
}
