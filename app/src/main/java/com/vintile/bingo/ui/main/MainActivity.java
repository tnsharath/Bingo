package com.vintile.bingo.ui.main;


import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import com.vintile.bingo.R;
import com.vintile.bingo.data.Repository;
import com.vintile.bingo.ui.main.menu.MenuFragment;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        signInAnonymously();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        MenuFragment bingoBox = new MenuFragment();
        ft.add(R.id.framelayout, bingoBox);
        ft.commit();

    }

    private void signInAnonymously() {
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInAnonymously:success");
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInAnonymously:failure", task.getException());
                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }

                });
    }

    private void signOut() {
        mAuth.signOut();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        signOut();
    }
}
