package com.example.yumi.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        findViewById(R.id.toHomeButton).setOnClickListener(this);//画面遷移

//        Intent intent = getIntent();
//        String message = intent.getExtras().getString("message");
//        TextView birthday = (TextView) findViewById(R.id.textView2);
//        birthday.setText(message);


    }

    public void onClick(View view) {

        finish();


    }




}
