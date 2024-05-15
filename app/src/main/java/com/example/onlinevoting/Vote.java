package com.example.onlinevoting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Vote extends AppCompatActivity {
    private int votecount1 = 0;
    private int votecount2 = 0;
    private DatabaseReference voteReference;
    private DatabaseReference userReference;
    private String username;
    private LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vote);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.resultpage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get the username from the intent
        username = getIntent().getStringExtra("username");

        Button vote1 = findViewById(R.id.vote1);
        Button vote2 = findViewById(R.id.vote2);
        animationView = findViewById(R.id.voted);

        // Initialize Firebase references
        voteReference = FirebaseDatabase.getInstance().getReference("votes");
        userReference = FirebaseDatabase.getInstance().getReference("users").child(username);

        vote1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndVote(1);
            }
        });

        vote2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndVote(2);
            }
        });
    }

    private void checkAndVote(int candidate) {
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean hasVoted = snapshot.child("hasVoted").getValue(Boolean.class);
                if (hasVoted != null && hasVoted) {
                    Toast.makeText(Vote.this, "You have already voted!", Toast.LENGTH_SHORT).show();
                } else {
                    castVote(candidate);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Vote.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void castVote(int candidate) {
        userReference.child("hasVoted").setValue(true);

        voteReference.child("candidate" + candidate).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int currentVotes = snapshot.getValue(Integer.class) != null ? snapshot.getValue(Integer.class) : 0;
                voteReference.child("candidate" + candidate).setValue(currentVotes + 1);

                Button vote1 = findViewById(R.id.vote1);
                Button vote2 = findViewById(R.id.vote2);
                vote1.setEnabled(false);
                vote2.setEnabled(false);
                animationView.setVisibility(View.VISIBLE);

                animationView.setAnimation(R.raw.voted);
                animationView.playAnimation();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Vote.this, Result.class);
                        startActivity(intent);
                        finish();
                    }
                }, 2000);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Vote.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
