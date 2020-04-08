package com.vintile.bingo.ui.main.game;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vintile.bingo.R;
import com.vintile.bingo.db.SharedPref;
import com.vintile.bingo.model.Feed;
import com.vintile.bingo.util.Constants;
import com.vintile.bingo.util.GameAdapterInterface;
import com.vintile.bingo.util.VerticalSpacingItemDecoration;
import com.vintile.bingo.viewmodels.ViewModelProviderFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * Created by Sharath on 2020/04/06
 **/
public class GameArenaFragment extends DaggerFragment implements GameAdapterInterface {

    private static final String TAG = "GameArenaFragment";

    private RecyclerView rv_player;
    private RecyclerView rv_opp;

    private GameViewModel viewModel;


    @Inject
    ViewModelProviderFactory providerFactory;

    private PlayerAdapter playerAdapter;
    private OppAdapter oppAdapter;

    @Inject
    GridLayoutManager gridLayoutManager;

    private DatabaseReference mFirebaseDatabaseReference;

    boolean firstTurn = true;

    @Inject
    SharedPref sharedPref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_arena, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rv_player = view.findViewById(R.id.recycler_view);
        rv_opp = view.findViewById(R.id.rv_opp_box);

        playerAdapter = new PlayerAdapter(this);
        oppAdapter = new OppAdapter(this);

        viewModel = new ViewModelProvider(this, providerFactory).get(GameViewModel.class);
        initRecyclerView();
        subscribeObserver();
        listenToChanges();
        updateOpponent();
    }

    private void initRecyclerView() {
        VerticalSpacingItemDecoration itemDecoration = new VerticalSpacingItemDecoration(0);

        rv_player.setLayoutManager(gridLayoutManager);
        rv_player.addItemDecoration(itemDecoration);
        rv_player.setAdapter(playerAdapter);


        rv_opp.setLayoutManager(new GridLayoutManager(getContext(), 5));
        rv_opp.addItemDecoration(itemDecoration);
        rv_opp.setAdapter(oppAdapter);

    }

    private void subscribeObserver() {
        viewModel.getFeed().observe(getViewLifecycleOwner(), feed -> {
            playerAdapter.setFeeds(feed);

        });


        viewModel.getOppFeed().observe(getViewLifecycleOwner(), feeds -> {
            oppAdapter.setFeeds(feeds);
            if (!firstTurn) {
                sharedPref.setKeyIsMyTurn(!sharedPref.isMyTurn());
            }else {
                firstTurn = false;
            }
        });
    }

    @Override
    public void clickedBox(Feed feed) {
        Log.d(TAG, "clickedBox: My turn " + sharedPref.isMyTurn());
        if (sharedPref.isMyTurn()){
            Log.d(TAG, "clickedBox: My turn " + sharedPref.isMyTurn());
            viewModel.playerChecked(feed);
        }
    }

    private void updateOpponent() {

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.MATCH_BOX).child(sharedPref.getKeyOppId());
        mFirebaseDatabaseReference.child(sharedPref.getKeyOppId());

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Feed> feedList = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Feed feed = snapshot.getValue(Feed.class);
                    feedList.add(feed);
                }
                viewModel.initOppBox(feedList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mFirebaseDatabaseReference.addValueEventListener(postListener);

    }


    List<Feed> feedList;

    private void listenToChanges() {

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.MATCH_BOX).child(sharedPref.getKeyPlayerId());
        mFirebaseDatabaseReference.child(sharedPref.getKeyPlayerId());

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                feedList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Feed feed = snapshot.getValue(Feed.class);
                    Log.d(TAG, "onDataChange: feedbox id " + feed.getBoxID() + " feed bool " + feed.isChecked());
                    feedList.add(feed);
                }
                viewModel.updateFeed(feedList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mFirebaseDatabaseReference.addValueEventListener(postListener);
    }
}