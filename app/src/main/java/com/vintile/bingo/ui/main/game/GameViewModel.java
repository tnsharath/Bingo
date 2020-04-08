package com.vintile.bingo.ui.main.game;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;


import com.vintile.bingo.data.Repository;
import com.vintile.bingo.model.Feed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Sharath on 2020/04/06
 **/
public class GameViewModel extends ViewModel {

    private static final String TAG = "FeedViewModel";

    private MediatorLiveData<List<Feed>> feed = new MediatorLiveData<>();
    private MediatorLiveData<List<Feed>> oppBox = new MediatorLiveData<>();
    private List<Feed> feeds = new ArrayList<>();
    private List<Feed> oppBoxes = new ArrayList<>();
    private Map<Integer, Integer> map = new HashMap<>();

    private final Repository repository;


    @Inject
    public GameViewModel(Repository repository) {
        Log.d(TAG, "GameViewModel: viewModel is working...");
        this.repository = repository;
    }

    public void initOppBox(List<Feed> feeds) {

        for (Feed feed: feeds){
            map.put(feed.getNumber(), feed.getBoxID());
        }
        oppBoxes.clear();
        oppBoxes = feeds;
        oppBox.setValue(oppBoxes);
    }

    LiveData<List<Feed>> getFeed() {
        return feed;
    }

    LiveData<List<Feed>> getOppFeed() {
        return oppBox;
    }

    public LiveData<List<Feed>> getBox() {
        return repository.getBoxContent();
    }

    public void playerChecked(Feed update) {
        Feed newFeed = new Feed(update.getBoxID(), update.getNumber(), false);
        Feed oppFeed = new Feed(map.get(update.getNumber()), update.getNumber(), false);
        repository.checkBox(newFeed, oppFeed);
    }

    public void updateFeed(List<Feed> updated) {
        feeds.clear();
        feeds = updated;
        Log.e(TAG, "updateFeed: feed size "+ feeds.size());
        Log.e(TAG, "updateFeed: update size "+ updated.size());
        feed.setValue(updated);
    }
}
