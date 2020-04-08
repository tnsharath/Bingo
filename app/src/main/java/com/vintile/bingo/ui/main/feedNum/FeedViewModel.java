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
    private List<Feed> feeds = new ArrayList<>();
    private int fillCount = 0;

    private final Repository repository;

    @Inject
    public FeedViewModel(Repository repository) {
        Log.d(TAG, "PostsViewModel: viewModel is working...");
        this.repository = repository;
     //   initBox();
    }

    private void initBox() {
        for (int i = 1; i <= 25; i++) {
            Feed feed = new Feed(i, i, true);
            feeds.add(feed);
        }
        //TODO remove fill count
       // fillCount = 25;
        feed.setValue(feeds);
    }

    LiveData<List<Feed>> getFeed() {
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

    public void allClear() {
        feeds.clear();
        fillCount = 0;
        initBox();
    }

    public void saveBox() {
        repository.insertBox(feeds);
    }

    public LiveData<List<Feed>> getBox() {
        return repository.getBoxContent();
    }

    public boolean isFilled() {
        return fillCount >= 25;
    }
}
