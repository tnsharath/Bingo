package com.vintile.bingo.ui.main;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.vintile.bingo.FriendlyMessage;
import com.vintile.bingo.R;
import com.firebase.ui.database.SnapshotParser;
import com.vintile.bingo.data.Repository;
import com.vintile.bingo.ui.main.game.GameArenaFragment;
import com.vintile.bingo.ui.main.menu.MenuFragment;
import com.vintile.bingo.util.Constants;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private DatabaseReference mFirebaseDatabaseReference;

    private ValueEventListener mPostListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        signInAnonymously();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        MenuFragment bingoBox = new MenuFragment();
        ft.add(R.id.framelayout, bingoBox);
        ft.commit();


        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.MESSAGES_CHILD);
        Log.d(TAG, "onCreate: ref" + mFirebaseDatabaseReference.toString());

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String friendlyMessage = dataSnapshot.child(Constants.MESSAGES_CHILD).toString();
              //  String key= dataSnapshot.child(MESSAGES_CHILD).getValue(FriendlyMessage.class);

             //   Log.d(TAG, "onDataChange: data is present " + key);
                //  FriendlyMessage friendlyMessage = dataSnapshot.getValue(FriendlyMessage.class);
          //       Log.d(TAG, "onDataChange: friendlyMessage ID " + friendlyMessage.getId());
                 Log.d(TAG, "onDataChange: friendlyMessage Name " + friendlyMessage);
                // Log.d(TAG, "onDataChange: friendlyMessage Text " + friendlyMessage.getText());4
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(MainActivity.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
        mFirebaseDatabaseReference.addValueEventListener(postListener);


    }


  /*  @Override
    public void onStart() {
        super.onStart();
        // New child entries
        FriendlyMessage friendlyMessage = new
                FriendlyMessage(
                "001",
                "Testing",
                "Sharath",
                null,
                null *//* no image *//*);
        mFirebaseDatabaseReference.child(MESSAGES_CHILD)
                .push().setValue(friendlyMessage);
    }*/


    private void signInAnonymously() {
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInAnonymously:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        // [END signin_anonymously]
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }


    private void updateUI(FirebaseUser user) {

        boolean isSignedIn = (user != null);

        // Status text
        if (isSignedIn) {

            Log.d(TAG, "updateUI: userId" + user.getUid());
            Log.d(TAG, "updateUI: user email" + user.getEmail());
        } else {

        }
    }

    @Inject
    Repository repository;

    @Override
    protected void onDestroy() {
        super.onDestroy();
repository.deleteAll();    }
}
