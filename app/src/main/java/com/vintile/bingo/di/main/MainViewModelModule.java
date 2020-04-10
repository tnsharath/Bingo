package com.vintile.bingo.di.main;

import androidx.lifecycle.ViewModel;

import com.vintile.bingo.di.ViewModelKey;
import com.vintile.bingo.ui.main.feedNum.FeedViewModel;
import com.vintile.bingo.ui.main.game.GameViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by Sharath on 2020/02/12
 **/
@Module
public abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(FeedViewModel.class)
    public abstract ViewModel bindFeedViewModel(FeedViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(GameViewModel.class)
    public abstract ViewModel bindGameViewModel(GameViewModel viewModel);
}
