package com.vintile.bingo.data;


import android.content.Context;
import android.util.Log;


import androidx.annotation.NonNull;

import androidx.lifecycle.LiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.vintile.bingo.db.AppDatabase;
import com.vintile.bingo.db.SharedPref;
import com.vintile.bingo.model.Feed;
import com.vintile.bingo.util.Constants;


import java.util.List;
import java.util.concurrent.Executor;

public class Repository {

    private static final String TAG = "Repository";
    private final Context context;
    private final TaskExecutor taskExecutor;

    private final AppDatabase db;
    private DatabaseReference mFirebaseDatabaseReference;

    SharedPref sharedPref;

    public Repository(Context context, AppDatabase db) {
        this.context = context;
        this.db = db;
        taskExecutor = new TaskExecutor();
        sharedPref = new SharedPref(context);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.MATCH_BOX);
    }


    public LiveData<List<Feed>> getBoxContent() {
        return db.bingoDao().getAll();
    }

    public void insertBox(List<Feed> feeds) {
        mFirebaseDatabaseReference.child(sharedPref.getKeyPlayerId()).setValue(feeds);
        taskExecutor.execute(new RoomUpdateTask(feeds));
    }

    public void deleteAll() {
        db.bingoDao().deleteAll();
    }

    public void checkBox(Feed update, Feed oppFeed) {
        mFirebaseDatabaseReference.child(sharedPref.getKeyPlayerId()).child(String.valueOf(update.getBoxID() - 1)).setValue(update);
        mFirebaseDatabaseReference.child(sharedPref.getKeyOppId()).child(String.valueOf(oppFeed.getBoxID() - 1)).setValue(oppFeed);
    }

    private class TaskExecutor implements Executor {
        @Override
        public void execute(@NonNull Runnable runnable) {
            Thread t = new Thread(runnable);
            t.start();
        }
    }

    private class RoomUpdateTask implements Runnable {
        private final List<Feed> restuarantMenus;

        RoomUpdateTask(List<Feed> restuarantMenus) {
            this.restuarantMenus = restuarantMenus;
        }

        @Override
        public void run() {
            insertLatestCouponsIntoLocalDb(restuarantMenus);
        }
    }

    private void insertLatestCouponsIntoLocalDb(List<Feed> bonusProductsModels) {
        try {
            db.bingoDao().insertAll(bonusProductsModels);
        } catch (Exception e) {
            Log.d(TAG, "run: unsucess" + e);
        }
    }
}
