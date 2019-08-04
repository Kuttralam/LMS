package com.example.kuttr.lms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;


public class LoginActivity extends AppCompatActivity implements OnClickListener {
    EditText username, password;
    Button signin;
    CheckBox checkbox;
    String n="";
    String p="";
    TextView link_signup;
    private static final int REQUEST_SIGNUP = 0;
    private static final String TAG = "LoginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkbox=findViewById(R.id.cb);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);



        link_signup=findViewById(R.id.link_signup);

        signin = findViewById(R.id.signin_button);
        signin.setOnClickListener(this);

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }});

        link_signup.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // Start the Signup activity
                        Intent intent = new Intent(getApplicationContext(), Sign_in.class);
                        startActivityForResult(intent, REQUEST_SIGNUP);
                    }
                });

        Fabric.with(this, new Crashlytics());

    }

    private void dismissProgressDialog(){
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void onLoginSuccess() {
        signin.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        signin.setEnabled(true);
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        signin.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();


        Log.d("username",username.getText().toString());
        Toast.makeText(this, username.getText().toString(), Toast.LENGTH_SHORT).show();

        Log.d("password",password.getText().toString());
        Toast.makeText(this, password.getText().toString(), Toast.LENGTH_SHORT).show();

        Intent i = new Intent
                (LoginActivity.this,
                        Employee.class);
        i.putExtra("name", username.getText().toString());
        i.putExtra("password", password.getText().toString());

        startActivityForResult(i,4);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        dismissProgressDialog();

                    }
                }, 3000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }


    public boolean validate() {
        boolean valid = true;

        if (TextUtils.isEmpty(username.getText())) {
            username.setError("You must enter a valid username");
            valid=false;
        }
        if (TextUtils.isEmpty(password.getText()) || password.getText().toString().length() < 8) {

            password.setError("You must have 8 characters in your password");
            valid=false;
        }

        return valid;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == signin.getId()) {
            login();
        }
    }




}


