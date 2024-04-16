package com.example.onlinevoting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;

public class Vote extends AppCompatActivity {
    int votecount1 = 0;
    int votecount2 = 0;

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


        Button vote2 = findViewById(R.id.vote2);
        Button vote1 = findViewById(R.id.vote1);
        LottieAnimationView animationView = findViewById(R.id.voted);
    vote1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            votecount1++;

            vote1.setEnabled(false);
            vote2.setEnabled(false);
            animationView.setVisibility(View.VISIBLE);

            // Load and play the Lottie animation
            animationView.setAnimation(R.raw.voted);
            animationView.playAnimation();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Vote.this, Result.class);
                    intent.putExtra("voteCount1", votecount1);
                    startActivity(intent);
                }
            }, 2000);

        }
    });

    vote2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            votecount2++;

            vote1.setEnabled(false);
            vote2.setEnabled(false);
            animationView.setVisibility(View.VISIBLE);

            // Load and play the Lottie animation
            animationView.setAnimation(R.raw.voted);
            animationView.playAnimation();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Vote.this, Result.class);
                    intent.putExtra("voteCount2", votecount2);
                    startActivity(intent);
                }
            }, 2000);
        }
    });
    }
}