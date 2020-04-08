package com.vintile.bingo.di.main;


import com.vintile.bingo.ui.main.feedNum.FeedNumbers;
import com.vintile.bingo.ui.main.game.GameArenaFragment;
import com.vintile.bingo.ui.main.menu.MenuFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Sharath on 2020/02/11
 **/
@Module
public abstract class MainActivityBuildersModule {

    @ContributesAndroidInjector
    abstract FeedNumbers contributePostsFragment();

    @ContributesAndroidInjector
    abstract GameArenaFragment contributeGameArenaFragment();

    @ContributesAndroidInjector
    abstract MenuFragment contributeMenuFragment();
}
