package com.company.moviemania.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.company.moviemania.R;

public class SignInActivity extends AppCompatActivity {
    Button signIn;
    EditText editTextUsername, editTextPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        initView();
    }

    private void initView() {
        editTextUsername = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.editTextUserPassword);
        signIn = findViewById(R.id.sign_in);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextUsername.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty()) {
                    Toast.makeText(SignInActivity.this, "Please enter data", Toast.LENGTH_SHORT).show();
                } else if (editTextUsername.getText().toString().equals("admin") && editTextPassword.getText().toString().equals("admin")) {
                    startActivity(new Intent(SignInActivity.this, MainActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(SignInActivity.this, "Your username or password is incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}