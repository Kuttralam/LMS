package com.example.kuttr.lms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Sign_in extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    EditText e1,e2,e3;
    TextView t1;
    Button b1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        e1=findViewById(R.id.input_name);
        e2=findViewById(R.id.input_email);
        e3=findViewById(R.id.input_password);
        b1=findViewById(R.id.btn_signup);

        t1=findViewById(R.id.link_login) ;

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        b1.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(Sign_in.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = e1.getText().toString();
        String email = e2.getText().toString();
        String password = e3.getText().toString();

        Intent i = new Intent
                (Sign_in.this,
                        LoginActivity.class);
        i.putExtra("name", name);
        i.putExtra("password", password);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        b1.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        b1.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = e1.getText().toString();
        String email = e2.getText().toString();
        String password = e3.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            e1.setError("at least 3 characters");
            valid = false;
        } else {
            e1.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            e2.setError("enter a valid email address");
            valid = false;
        } else {
            e2.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            e3.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            e3.setError(null);
        }

        return valid;
    }
}