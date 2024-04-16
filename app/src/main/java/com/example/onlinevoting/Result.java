package com.example.onlinevoting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Result extends AppCompatActivity {
    private TextView votecount1;
    private TextView votecount2;
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

        int voteCount1 = getIntent().getIntExtra("voteCount1",0);
        votecount1 = findViewById(R.id.votecount1);
        votecount1.setText("Total Votes: " + voteCount1);

        int voteCount2 = getIntent().getIntExtra("voteCount2",0);
        votecount2 = findViewById(R.id.votecount2);
        votecount2.setText("Total Votes: " + voteCount2);

        Button calculate = findViewById(R.id.calculation);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Vote_Calculation.class);
                startActivity(intent);
            }
        });
    }
}