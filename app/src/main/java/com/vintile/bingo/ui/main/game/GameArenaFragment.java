package com.vintile.bingo.ui.main.game;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentTransaction;
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
import com.vintile.bingo.model.Match;
import com.vintile.bingo.ui.main.MainActivity;
import com.vintile.bingo.ui.main.feedNum.FeedNumbers;
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

    TextView tvTurn;

    TextView tvB;
    TextView tvI;
    TextView tvN;
    TextView tvG;
    TextView tvO;
    TextView tvoppB;
    TextView tvoppI;
    TextView tvoppN;
    TextView tvoppG;
    TextView tvoppO;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.viewGroup = container;
        return inflater.inflate(R.layout.fragment_game_arena, container, false);
    }

    ViewGroup viewGroup;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rv_player = view.findViewById(R.id.recycler_view);
        rv_opp = view.findViewById(R.id.rv_opp_box);
        tvTurn = view.findViewById(R.id.tvTurn);

        tvB = view.findViewById(R.id.tvB);
        tvI = view.findViewById(R.id.tvI);
        tvN = view.findViewById(R.id.tvN);
        tvG = view.findViewById(R.id.tvG);
        tvO = view.findViewById(R.id.tvO);
        tvoppB = view.findViewById(R.id.tvoppB);
        tvoppI = view.findViewById(R.id.tvoppI);
        tvoppN = view.findViewById(R.id.tvoppN);
        tvoppG = view.findViewById(R.id.tvoppG);
        tvoppO = view.findViewById(R.id.tvoppO);

        playerAdapter = new PlayerAdapter(this);
        oppAdapter = new OppAdapter(this);

        TextView tvOppName = view.findViewById(R.id.tvOppName);
        TextView tvPlayerName = view.findViewById(R.id.tvPlayerName);

        tvOppName.setText(sharedPref.getKeyOppId());
        tvPlayerName.setText(sharedPref.getKeyPlayerId());

        if (sharedPref.isMyTurn()) {
            tvTurn.setText("Your turn!");
        } else {
            tvTurn.setText("Opponent turn!");
        }

        viewModel = new ViewModelProvider(this, providerFactory).get(GameViewModel.class);
        initRecyclerView();
        subscribeObserver();
        listenToChanges();
        updateOpponent();
        listenToMatch();
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
            sharedPref.setKeyIsMyTurn(!sharedPref.isMyTurn());
            if (sharedPref.isMyTurn()) {
                tvTurn.setText("Your turn!");
            } else {
                tvTurn.setText("Opponent turn!");
            }

        });

        viewModel.getOppFeed().observe(getViewLifecycleOwner(), feeds -> {
            oppAdapter.setFeeds(feeds);
        });


        viewModel.getPlayerBingoCount().observe(getViewLifecycleOwner(), count -> {
            mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.MATCH_CHILD).child(sharedPref.getRoomId());
            mFirebaseDatabaseReference.child(sharedPref.getRoomId());
            switch (count) {
                case 1:
                    tvB.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    tvI.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    tvN.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    tvG.setVisibility(View.VISIBLE);
                    break;
                case 5:
                    tvO.setVisibility(View.VISIBLE);
                    Match match = new Match(sharedPref.getKeyPlayerId(),
                            sharedPref.getKeyOppId(),
                            sharedPref.getKeyPlayerId(),
                            sharedPref.getKeyOppId(),
                            sharedPref.isMyTurn());

                    mFirebaseDatabaseReference.setValue(match);
                    break;
            }
        });
    }

    @Override
    public void clickedBox(Feed feed) {
        Log.d(TAG, "clickedBox: My turn " + sharedPref.isMyTurn());
        if (sharedPref.isMyTurn()) {
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
    private boolean gotResult = false;

    private void listenToMatch() {
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.MATCH_CHILD).child(sharedPref.getRoomId());

        mFirebaseDatabaseReference.child(sharedPref.getRoomId());

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    Match match = dataSnapshot.getValue(Match.class);

                    if (!match.getWinner().equals("null") && !gotResult) {
                        showResult(match);
                        gotResult = true;
                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());

            }
        };
        mFirebaseDatabaseReference.addValueEventListener(postListener);
    }

    private void showResult(Match match) {

        String result = "";

        if (sharedPref.getKeyPlayerId().equals(match.winner)) {
            result = "congratulations!! You won";
        } else {
            result = "You lost. Better luck next time!!";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.result, viewGroup, false);
        TextView tvResult = dialogView.findViewById(R.id.tvResult);
        tvResult.setText(result);
        builder.setView(dialogView);
        builder.setPositiveButton("Ok",
                (arg0, arg1) -> {
                    sharedPref.clear();
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    getActivity().finish();
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

    }
}