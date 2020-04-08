package com.vintile.bingo.ui.main.game;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
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
    private MutableLiveData<Integer> bingoCount = new MutableLiveData<>();

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


    public void playerChecked(Feed update) {
        Feed newFeed = new Feed(update.getBoxID(), update.getNumber(), false);
        Feed oppFeed = new Feed(map.get(update.getNumber()), update.getNumber(), false);
        repository.checkBox(newFeed, oppFeed);
    }

    public void updateFeed(List<Feed> updated) {
        feeds.clear();
        feeds = updated;
        feed.setValue(updated);
        setPlayerBingoCount();
    }


    public void setPlayerBingoCount(){
        int box[] = new int[25];
        int[] count = new int[12];
        for (Feed feed: feeds){
            if (!feed.isChecked()){
                box[feed.getBoxID() - 1] = 1;
            }else{
                box[feed.getBoxID() - 1] = 0;
            }
        }


        if (box[0] == 1 && box[1] == 1 && box[2] == 1 && box[3] == 1 && box[4] == 1){
            count[1] = 1;
        }

        if (box[5] == 1 && box[6] == 1 && box[7] == 1 && box[8] == 1 && box[9] == 1){
            count[2] = 1;
        }
        if (box[10] == 1 && box[11] == 1 && box[12] == 1 && box[13] == 1 && box[14] == 1){
            count[3] = 1;
        }
        if (box[15] == 1 && box[16] == 1 && box[17] == 1 && box[18] == 1 && box[19] == 1){
            count[4] = 1;
        }
        if (box[20] == 1 && box[21] == 1 && box[22] == 1 && box[23] == 1 && box[24] == 1){
            count[5] = 1;
        }
        if (box[0] == 1 && box[5] == 1 && box[10] == 1 && box[15] == 1 && box[20] == 1){
            count[6] = 1;
        }
        if (box[1] == 1 && box[6] == 1 && box[11] == 1 && box[16] == 1 && box[21] == 1){
            count[7] = 1;
        }
        if (box[2] == 1 && box[7] == 1 && box[12] == 1 && box[17] == 1 && box[22] == 1){
            count[8] = 1;
        }
        if (box[3] == 1 && box[8] == 1 && box[13] == 1 && box[18] == 1 && box[23] == 1){
            count[9] = 1;
        }
        if (box[4] == 1 && box[9] == 1 && box[14] == 1 && box[19] == 1 && box[24] == 1){
            count[10] = 1;
        }

        if (box[0] == 1 && box[6] == 1 && box[12] == 1 && box[18] == 1 && box[24] == 1){
            count[11] = 1;
        }
        if (box[4] == 1 && box[8] == 1 && box[12] == 1 && box[16] == 1 && box[20] == 1){
            count[0] = 1;
        }
        int sum = 0;
        for (int i = 0; i < 12; i++){
            sum +=count[i];
        }
        bingoCount.setValue(sum);
    }

    public LiveData<Integer> getPlayerBingoCount(){
        return bingoCount;
    }

}
