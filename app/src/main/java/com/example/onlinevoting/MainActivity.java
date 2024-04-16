package com.example.onlinevoting;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText username, password;

    boolean isAllFieldsChecked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView imageView = findViewById(R.id.giphy);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        Button btn1 = findViewById(R.id.login);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAllFieldsChecked = CheckAllFields();

                if(isAllFieldsChecked) {
                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    startActivity(intent);
                }
            }
        });

        Button btn2 = findViewById(R.id.signup);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.resultpage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
    private boolean CheckAllFields(){
        if(username.length() == 0){
            username.setError("This field is required!");
            return false;
        }
        if(password.length() == 0){
            password.setError("This field is required!");
            return false;
        }
        if(password.length() < 8){
            password.setError("Password must contain 8 characters!");
            return false;
        }
        return true;
    }
}