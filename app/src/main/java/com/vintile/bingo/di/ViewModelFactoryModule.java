package com.vintile.bingo.di;

import androidx.lifecycle.ViewModelProvider;


import com.vintile.bingo.viewmodels.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Sharath on 2020/02/10
 **/
@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelProviderFactory);
}
