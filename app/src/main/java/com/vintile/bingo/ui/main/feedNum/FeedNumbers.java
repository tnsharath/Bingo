package com.vintile.bingo.ui.main.feedNum;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.vintile.bingo.ui.main.game.GameArenaFragment;
import com.vintile.bingo.util.Constants;
import com.vintile.bingo.util.FeedAdapterInterface;
import com.vintile.bingo.util.VerticalSpacingItemDecoration;
import com.vintile.bingo.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * Created by Sharath on 2020/04/05
 **/
public class FeedNumbers extends DaggerFragment implements FeedAdapterInterface {

    private static final String TAG = "PostsFragment";

    private FeedViewModel viewModel;
    private RecyclerView recyclerView;
    private Button allCrear;
    private ImageButton btnUndo;
    private Button btnConfirm;

    private TextView tvRoomIDDisplay;
    private TextView tvPlayer1;
    private TextView tvPlayer2;

    @Inject
    ViewModelProviderFactory providerFactory;

    FeedAdapter adapter;

    @Inject
    GridLayoutManager gridLayoutManager;

    @Inject
    SharedPref sharedPref;

    private DatabaseReference mFirebaseDatabaseReference;
    Match match = new Match();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed_numbers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_view);

        tvPlayer1 = view.findViewById(R.id.tvPlayer1);
        tvPlayer2 = view.findViewById(R.id.tvPlayer2);

        tvRoomIDDisplay = view.findViewById(R.id.tvRoomIDDisplay);

        allCrear = view.findViewById(R.id.btnCLearAll);
        allCrear.setOnClickListener(v -> viewModel.allClear());

        tvRoomIDDisplay.setText(sharedPref.getRoomId());

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.MATCH_CHILD).child(sharedPref.getRoomId());

        btnUndo = view.findViewById(R.id.btnUndo);
        btnUndo.setOnClickListener(v -> {
            //TODO undo
        });


        btnConfirm = view.findViewById(R.id.btnConfirm);
        btnConfirm.setEnabled(false);
        btnConfirm.setOnClickListener(v -> {
            viewModel.saveBox();
            if (viewModel.isFilled()) {
                startMatch();
            }

        });

        adapter = new FeedAdapter(this);
        viewModel = new ViewModelProvider(this, providerFactory).get(FeedViewModel.class);
        initRecyclerView();
        subscribeObserver();
        mFirebaseDatabaseReference.child(sharedPref.getRoomId());

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    String data = dataSnapshot.getKey();
                    Log.d(TAG, "onDataChange: data" + data);
                    if (!data.equals(sharedPref.getRoomId())) {
                        Toast.makeText(getContext(), " Room Does not exist.",
                                Toast.LENGTH_SHORT).show();

                        Log.d(TAG, "onDataChange: data error");
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.remove(new FeedNumbers());
                        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                        transaction.commit();
                    } else {
                        // for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        //   match = snapshot.getValue(Match.class);

                        match = dataSnapshot.getValue(Match.class);
                        //TODO Freeze button while also there is no player joined without listner
                        btnConfirm.setEnabled(true);
                        if (sharedPref.getKeyIsHost()) {
                            tvPlayer1.setText(match.getPlayer1());
                            tvPlayer2.setText(match.getPlayer2());
                            sharedPref.setKeyOppID(match.getPlayer2());
                        } else {
                            tvPlayer2.setText(match.getPlayer1());
                            tvPlayer1.setText(match.getPlayer2());

                            Log.d(TAG, "onDataChange: Toss" + match.toss);
                            sharedPref.setKeyIsMyTurn(!match.getToss());
                            Log.d(TAG, "onDataChange: Toss" + sharedPref.isMyTurn());
                            sharedPref.setKeyOppID(match.getPlayer1());
                            if (match.getPlayer2().equals("null")) {
                                match.setPlayer2(sharedPref.getKeyPlayerId());
                                mFirebaseDatabaseReference.setValue(match);
                            }


                        }

                        //   break;
                        // }
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(getContext(), "No room.Please try again.",
                        Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.remove(new FeedNumbers());
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        };
        mFirebaseDatabaseReference.addValueEventListener(postListener);
    }

    private void startMatch() {

        GameArenaFragment someFragment = new GameArenaFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayout, someFragment); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();

    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(gridLayoutManager);
        VerticalSpacingItemDecoration itemDecoration = new VerticalSpacingItemDecoration(0);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapter);

    }

    private void subscribeObserver() {
        viewModel.getFeed().observe(getViewLifecycleOwner(), feed -> {
            adapter.setFeeds(feed);
        });

        viewModel.getBox().observe(getViewLifecycleOwner(), feeds ->
                Log.d(TAG, "subscribeObserver: size" + feeds.size()));
    }

    @Override
    public void updateFeed(Feed feed) {
        if (viewModel != null) {
            viewModel.updateFeed(feed);
        }
    }
}
