package com.vintile.bingo.data;


import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.vintile.bingo.db.SharedPref;
import com.vintile.bingo.model.Feed;
import com.vintile.bingo.util.Constants;


import java.util.List;

public class Repository {

    private DatabaseReference mFirebaseDatabaseReference;

    SharedPref sharedPref;

    public Repository(Context context) {
        sharedPref = new SharedPref(context);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.MATCH_BOX);
    }


    public void insertBox(List<Feed> feeds) {
        mFirebaseDatabaseReference.child(sharedPref.getKeyPlayerId()).setValue(feeds);
    }


    public void checkBox(Feed update, Feed oppFeed) {
        mFirebaseDatabaseReference.child(sharedPref.getKeyPlayerId()).child(String.valueOf(update.getBoxID() - 1)).setValue(update);
        mFirebaseDatabaseReference.child(sharedPref.getKeyOppId()).child(String.valueOf(oppFeed.getBoxID() - 1)).setValue(oppFeed);
    }

}
