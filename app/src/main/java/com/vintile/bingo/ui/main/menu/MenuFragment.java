package com.vintile.bingo.ui.main.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vintile.bingo.R;
import com.vintile.bingo.db.SharedPref;
import com.vintile.bingo.model.Match;
import com.vintile.bingo.ui.main.feedNum.FeedNumbers;
import com.vintile.bingo.util.Constants;

import java.util.Random;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * Created by Sharath on 2020/04/07
 **/
public class MenuFragment extends DaggerFragment {

    private RadioGroup radioGroup;
    private Button btnCreateJoin;
    private TextInputEditText etEnterRoomID;
    private TextInputEditText etEnterPlayerName;
    private TextInputLayout tiEnterRoomID;
    private DatabaseReference mFirebaseDatabaseReference;

    @Inject
    SharedPref sharedPref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        radioGroup = view.findViewById(R.id.radioGroup);
        RadioButton rbCreate = view.findViewById(R.id.rbCreate);
        RadioButton rbJoin = view.findViewById(R.id.rbJoin);
        btnCreateJoin = view.findViewById(R.id.btnCreateJoin);
        etEnterRoomID = view.findViewById(R.id.etEnterRoomID);
        etEnterPlayerName = view.findViewById(R.id.etEnterPlayerName);
        tiEnterRoomID = view.findViewById(R.id.tiEnterRoomID);

        if (!sharedPref.getPlayerName().equals("Me")) {
            etEnterPlayerName.setText(sharedPref.getPlayerName());
        }
        btnCreateJoin.setOnClickListener(v -> clickedCreateJoin());

        rbCreate.setOnClickListener(v -> {
            tiEnterRoomID.setVisibility(View.INVISIBLE);
            btnCreateJoin.setText("Create");
        });


        rbJoin.setOnClickListener(v -> {
            tiEnterRoomID.setVisibility(View.VISIBLE);
            btnCreateJoin.setText("Join");
        });

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.MATCH_CHILD);

    }

    private void clickedCreateJoin() {
        int roomId = generateRoomId();

        int id = radioGroup.getCheckedRadioButtonId();

        String name = etEnterPlayerName.getText().toString();
        if (name.length() > 0) {
            sharedPref.setKeyPlayerName(name);
            if (sharedPref.getKeyPlayerId().length() <= 0) {
                sharedPref.setKeyPlayerID(generateRoomId() + "_" + name);
            }
        } else {
            Toast.makeText(getContext(), "Enter Name!!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (id == R.id.rbCreate) {

            boolean toss = toss();
            Match match = new Match(sharedPref.getKeyPlayerId(),
                    "null",
                    "null",
                    "null",
                    toss);
            sharedPref.setKeyIsMyTurn(toss);
            sharedPref.setKeyRoomId(String.valueOf(roomId));
            mFirebaseDatabaseReference.child(String.valueOf(roomId)).setValue(match);
            startFeedNumFrag();
            sharedPref.setKeyIsHost(true);
        } else {
            String idString = etEnterRoomID.getText().toString().trim();
            if (idString.length() > 0) {
                try {
                    sharedPref.setKeyRoomId(idString);
                    startFeedNumFrag();
                    sharedPref.setKeyIsHost(false);
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Only numbers are allowed", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Enter Room ID!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private int generateRoomId() {
        return new Random().nextInt(10000);
    }

    private void startFeedNumFrag() {
        FeedNumbers someFragment = new FeedNumbers();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayout, someFragment); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }

    private boolean toss(){
        return new Random().nextBoolean();
    }
}
