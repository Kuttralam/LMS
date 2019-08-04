
package com.example.kuttr.lms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Roles extends AppCompatActivity {

    String letterList[] = {"Employee", "Calculator", "History", "Settings", "About"};

    int letterIcon[] = {R.drawable.employee,};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roles);
    }
}
