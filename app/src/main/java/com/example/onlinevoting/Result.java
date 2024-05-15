package com.example.onlinevoting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Result extends AppCompatActivity {
    private TextView votecount1;
    private TextView votecount2;
    private DatabaseReference voteReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.resultpage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        votecount1 = findViewById(R.id.votecount1);
        votecount2 = findViewById(R.id.votecount2);
        Button calculate = findViewById(R.id.calculation);

        // Initialize Firebase reference
        voteReference = FirebaseDatabase.getInstance().getReference("votes");

        // Retrieve and display the vote counts
        displayVoteCounts();

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Vote_Calculation.class);
                startActivity(intent);
            }
        });
    }

    private void displayVoteCounts() {
        voteReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Integer voteCount1 = snapshot.child("candidate1").getValue(Integer.class);
                    Integer voteCount2 = snapshot.child("candidate2").getValue(Integer.class);

                    if (voteCount1 != null) {
                        votecount1.setText("Total Votes for Candidate 1: " + voteCount1);
                    } else {
                        votecount1.setText("Total Votes for Candidate 1: 0");
                    }

                    if (voteCount2 != null) {
                        votecount2.setText("Total Votes for Candidate 2: " + voteCount2);
                    } else {
                        votecount2.setText("Total Votes for Candidate 2: 0");
                    }
                } else {
                    votecount1.setText("Total Votes for Candidate 1: 0");
                    votecount2.setText("Total Votes for Candidate 2: 0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors.
            }
        });
    }
}
