package com.vintile.bingo.di.main;

import android.app.Application;

import androidx.recyclerview.widget.GridLayoutManager;

import com.vintile.bingo.data.Repository;
import com.vintile.bingo.db.AppDatabase;
import com.vintile.bingo.ui.main.MainActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Sharath on 2020/02/14
 **/
@Module
public class MainModule {


    @Provides
    static GridLayoutManager provideGridLayoutManager(MainActivity mainActivity) {
        return new GridLayoutManager(mainActivity, 5);
    }


    @Provides
    static Repository providesRepository(Application application, AppDatabase appDatabase){
        return new Repository(application, appDatabase);
    }

    @Provides
    static AppDatabase providesDatabaseInstance(Application application){
        return AppDatabase.getInstance(application);
    }
}
