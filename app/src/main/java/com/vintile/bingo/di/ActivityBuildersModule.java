package com.vintile.bingo.di;



import com.vintile.bingo.ui.main.MainActivity;
import com.vintile.bingo.di.main.MainActivityBuildersModule;
import com.vintile.bingo.di.main.MainModule;
import com.vintile.bingo.di.main.MainScope;
import com.vintile.bingo.di.main.MainViewModelModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Sharath on 2020/02/10
 **/

@Module
public abstract class ActivityBuildersModule  {


    @MainScope
    @ContributesAndroidInjector(
            modules = {
                    MainActivityBuildersModule.class,
                    MainViewModelModule.class,
                    MainModule.class,
            }
    )
    abstract MainActivity contributeMainActivity();
}
