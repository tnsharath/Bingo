package com.vintile.bingo.ui.main.feedNum;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.vintile.bingo.data.Repository;
import com.vintile.bingo.model.Feed;


import java.util.ArrayList;
import java.util.List;


import javax.inject.Inject;

/**
 * Created by Sharath on 2020/04/05
 **/
public class FeedViewModel extends ViewModel {

    private static final String TAG = "FeedViewModel";


    private MediatorLiveData<List<Feed>> feed = new MediatorLiveData<>();
    private  List<Feed> feeds = new ArrayList<>();
    private int fillCount = 0;

    private final Repository repository;

    @Inject
    public FeedViewModel(Repository repository) {
        Log.d(TAG, "PostsViewModel: viewModel is working...");
        this.repository = repository;
        initBox();
    }

    private void initBox() {
        Feed feed1 = new Feed(1, 0, false);
        feeds.add(feed1);

        Feed feed2 = new Feed(2, 0, false);
        feeds.add(feed2);
        Feed feed3 = new Feed(3, 0, false);
        feeds.add(feed3);
        Feed feed4 = new Feed(4, 0, false);
        feeds.add(feed4);
        Feed feed5 = new Feed(5, 0, false);
        feeds.add(feed5);
        Feed feed6 = new Feed(6, 0, false);
        feeds.add(feed6);
        Feed feed7 = new Feed(7, 0, false);
        feeds.add(feed7);
        Feed feed8 = new Feed(8, 0, false);
        feeds.add(feed8);
        Feed feed9 = new Feed(9, 0, false);
        feeds.add(feed9);
        Feed feed10 = new Feed(10, 0, false);
        feeds.add(feed10);
        Feed feed11 = new Feed(11, 0, false);
        feeds.add(feed11);
        Feed feed12 = new Feed(12, 0, false);
        feeds.add(feed12);
        Feed feed13 = new Feed(13, 0, false);
        feeds.add(feed13);
        Feed feed14 = new Feed(14, 0, false);
        feeds.add(feed14);
        Feed feed15 = new Feed(15, 0, false);
        feeds.add(feed15);
        Feed feed16 = new Feed(16, 0, false);
        feeds.add(feed16);
        Feed feed17 = new Feed(17, 0, false);
        feeds.add(feed17);
        Feed feed18 = new Feed(18, 0, false);
        feeds.add(feed18);
        Feed feed19 = new Feed(19, 0, false);
        feeds.add(feed19);
        Feed feed20 = new Feed(20, 0, false);
        feeds.add(feed20);
        Feed feed21 = new Feed(21, 0, false);
        feeds.add(feed21);
        Feed feed22= new Feed(22, 0, false);
        feeds.add(feed22);
        Feed feed23 = new Feed(23, 0, false);
        feeds.add(feed23);
        Feed feed24 = new Feed(24, 0, false);
        feeds.add(feed24);
        Feed feed25 = new Feed(25, 0, false);
        feeds.add(feed25);
        feed.setValue(feeds);
    }

    LiveData<List<Feed>> getFeed(){
        return feed;
    }

    public void updateFeed(Feed update) {

        Feed newFeed = new Feed(update.getBoxID(), ++fillCount, true);
        feeds.set(update.getBoxID() - 1, newFeed);
        feed.setValue(feeds);
    }

    public void testMethod() {
        Log.d(TAG, "testMethod: this is test");
    }

    public void allClear(){
        feeds.clear();
        fillCount = 0;
        initBox();
    }

    public void saveBox() {
        repository.insertBox(feeds);
    }

    public LiveData<List<Feed>>  getBox(){
        return repository.getBoxContent();
    }

    public boolean isFilled(){
        return fillCount >= 25;
    }
}
