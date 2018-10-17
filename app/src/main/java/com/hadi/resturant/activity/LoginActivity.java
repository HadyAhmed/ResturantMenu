package com.hadi.resturant.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.hadi.resturant.R;

public class LoginActivity extends AppCompatActivity {
    public static final String EMAIL_KEY = "email_key";
    private TextInputLayout usernameLayout, passwordLayout;
    private EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Get the view references
        MaterialButton loginBtn = findViewById(R.id.login_btn);
        MaterialButton cancel = findViewById(R.id.cancel_btn);
        usernameLayout = findViewById(R.id.username_layout);
        passwordLayout = findViewById(R.id.password_layout);
        username = findViewById(R.id.username_text);
        password = findViewById(R.id.password_text);

        SharedPreferences prefs = getSharedPreferences(MainActivity.USER_DATA, MODE_PRIVATE);
        if (prefs != null) {
            String email = prefs.getString(EMAIL_KEY, null);
            username.setText(prefs.getString(EMAIL_KEY, null));
        }


        username.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (isEmailValid(username.getText())) {
                    usernameLayout.setError(null);
                }
                return false;
            }
        });
        password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (isPasswordValid(password.getText())) {
                    passwordLayout.setError(null);
                }
                return false;
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (attemptLogin()) {
                    getIntent().putExtra(EMAIL_KEY, username.getText().toString());
                    setResult(RESULT_OK, getIntent());
                    finish();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private boolean attemptLogin() {
        if (!isEmailValid(username.getText())) {
            usernameLayout.setError(getString(R.string.valid_email_hint));
            return false;
        } else if (!isPasswordValid(password.getText())) {
            passwordLayout.setError(getString(R.string.valid_pass_hint));
            return false;
        } else {
            return true;
        }
    }

    private boolean isEmailValid(Editable email) {
        return email != null && email.toString().endsWith("@gmail.com");
    }

    private boolean isPasswordValid(Editable pass) {
        return pass != null && pass.length() >= 4;
    }

}
