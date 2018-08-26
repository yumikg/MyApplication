package com.example.yumi.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;


//なぜabstractになるのか
public abstract class EditProfileActivity extends Activity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);


    }
}
