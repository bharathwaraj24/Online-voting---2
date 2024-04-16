package com.example.onlinevoting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Register extends AppCompatActivity {

    EditText uname, email, dob, pass, confirmpass;
    boolean isAllFieldsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.resultpage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        uname = findViewById(R.id.uname);
        email = findViewById(R.id.email);
        dob = findViewById(R.id.dob);
        pass = findViewById(R.id.pass);
        confirmpass = findViewById(R.id.confirmpass);

        Button btn3 = findViewById(R.id.register);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAllFieldsChecked = CheckAllFields();

                if(isAllFieldsChecked) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    private boolean CheckAllFields() {
        if (uname.length() == 0) {
            uname.setError("This field is required!");
            return false;
        }
        if (pass.length() == 0) {
            pass.setError("This field is required!");
            return false;
        }
        if (pass.length() < 8) {
            pass.setError("Password must contain 8 characters!");
            return false;
        }
        if (confirmpass.length() == 0) {
            confirmpass.setError("This field is required!");
            return false;
        }
        if (confirmpass.length() < 8) {
            confirmpass.setError("Password must contain 8 characters!");
            return false;
        }
        if (confirmpass != pass) {
            confirmpass.setError("Password must same as above!");
            return false;
        }
        if(email.length() == 0){
            email.setError("This field is required!");
            return false;
        }
        if(dob.length() == 0){
            dob.setError("This field is required!");
            return false;
        }
        return true;

    }
}